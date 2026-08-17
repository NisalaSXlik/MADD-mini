import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

admin.initializeApp();
const db = admin.database();

/**
 * Scheduled function running every 1 minute to check:
 * 1. IRON_SLOT safety cutoff: if status is ON and elapsed time exceeds maxOnDurationMinutes, turn OFF and create an alert.
 * 2. LIGHT_SCHEDULE auto on/off: checks if current HH:mm falls within scheduleStart and scheduleEnd.
 */
export const smartHomeAutomation = functions.pubsub.schedule("every 1 minutes").onRun(async (_context) => {
  const now = Date.now();
  const currentDate = new Date();
  const currentHours = String(currentDate.getHours()).padStart(2, "0");
  const currentMinutes = String(currentDate.getMinutes()).padStart(2, "0");
  const currentTimeStr = `${currentHours}:${currentMinutes}`;

  const devicesSnap = await db.ref("devices").once("value");
  if (!devicesSnap.exists()) {
    return null;
  }

  const updates: { [key: string]: any } = {};
  const alertsRef = db.ref("alerts");

  devicesSnap.forEach((childSnap) => {
    const deviceId = childSnap.key;
    const device = childSnap.val();
    if (!device) return;

    const deviceType = device.type;
    const status = device.status;
    const deviceName = device.name || "Unknown Device";

    // 1. IRON_SLOT Safety Cutoff Logic
    if (deviceType === "IRON_SLOT" && status === "ON") {
      const turnedOnAt = device.turnedOnAt || now;
      const maxOnDurationMinutes = device.maxOnDurationMinutes || 30; // default 30 mins
      const elapsedMs = now - turnedOnAt;
      const maxDurationMs = maxOnDurationMinutes * 60 * 1000;

      if (elapsedMs > maxDurationMs) {
        updates[`devices/${deviceId}/status`] = "OFF";
        updates[`devices/${deviceId}/turnedOnAt`] = null;

        // Push alert
        const newAlertRef = alertsRef.push();
        updates[`alerts/${newAlertRef.key}`] = {
          id: newAlertRef.key,
          deviceId: deviceId,
          message: `Safety cutoff: ${deviceName} exceeded max on-duration`,
          timestamp: now,
          acknowledged: false,
        };
      }
    }

    // 2. LIGHT_SCHEDULE Auto On/Off Logic
    if (deviceType === "LIGHT_SCHEDULE") {
      const scheduleStart = device.scheduleStart; // Expected format "HH:mm" e.g., "18:00"
      const scheduleEnd = device.scheduleEnd;     // Expected format "HH:mm" e.g., "06:00"

      if (scheduleStart && scheduleEnd) {
        let shouldBeOn = false;

        if (scheduleStart <= scheduleEnd) {
          // e.g. 08:00 to 17:00
          shouldBeOn = currentTimeStr >= scheduleStart && currentTimeStr < scheduleEnd;
        } else {
          // Overnight span e.g. 18:00 to 06:00
          shouldBeOn = currentTimeStr >= scheduleStart || currentTimeStr < scheduleEnd;
        }

        const targetStatus = shouldBeOn ? "ON" : "OFF";
        if (status !== targetStatus) {
          updates[`devices/${deviceId}/status`] = targetStatus;
        }
      }
    }
  });

  if (Object.keys(updates).length > 0) {
    await db.ref().update(updates);
  }

  return null;
});

export const deviceStatusAlert = functions.database
  .ref("/devices/{deviceId}/status")
  .onWrite(async (change, context) => {
    if (!change.after.exists()) {
      return null;
    }

    const beforeStatus = change.before.val();
    const afterStatus = change.after.val();
    if (beforeStatus === afterStatus) {
      return null;
    }

    if (afterStatus !== "ERROR" && afterStatus !== "DISCONNECTED") {
      return null;
    }

    const deviceId = context.params.deviceId;
    const deviceSnap = await db.ref(`devices/${deviceId}`).once("value");
    const device = deviceSnap.val() || {};
    if (device.lastAlertedStatus === afterStatus) {
      return null;
    }

    const deviceName = device.name || "Unknown Device";
    const message =
      afterStatus === "ERROR"
        ? `Device error: ${deviceName} reported an error`
        : `Device disconnected: ${deviceName} is disconnected`;

    const alertRef = db.ref("alerts").push();
    await db.ref().update({
      [`devices/${deviceId}/lastAlertedStatus`]: afterStatus,
      [`alerts/${alertRef.key}`]: {
        id: alertRef.key,
        deviceId,
        message,
        timestamp: Date.now(),
        acknowledged: false,
      },
    });

    return null;
  });
