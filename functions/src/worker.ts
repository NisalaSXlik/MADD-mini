import * as admin from "firebase-admin";
import * as path from "path";
import * as fs from "fs";

// Initialize Firebase Admin SDK.
// GitHub Actions should provide FIREBASE_SERVICE_ACCOUNT_KEY as a JSON secret.
// Local runs can still use functions/serviceAccountKey.json.
const serviceAccountPath = path.join(__dirname, "../serviceAccountKey.json");
const databaseURL = process.env.FIREBASE_DATABASE_URL || "https://madd-mini-project-default-rtdb.firebaseio.com";

if (process.env.FIREBASE_SERVICE_ACCOUNT_KEY) {
  const serviceAccount = JSON.parse(process.env.FIREBASE_SERVICE_ACCOUNT_KEY);
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL
  });
} else if (fs.existsSync(serviceAccountPath)) {
  const serviceAccount = JSON.parse(fs.readFileSync(serviceAccountPath, "utf8"));
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL
  });
} else {
  admin.initializeApp({ databaseURL });
}

const db = admin.database();

async function runAutomationWorker() {
  console.log(`[Worker] Running automation check at ${new Date().toISOString()}`);
  try {
    const now = Date.now();
    const currentDate = new Date();
    const currentHours = String(currentDate.getHours()).padStart(2, "0");
    const currentMinutes = String(currentDate.getMinutes()).padStart(2, "0");
    const currentTimeStr = `${currentHours}:${currentMinutes}`;

    const devicesSnap = await db.ref("devices").once("value");
    if (!devicesSnap.exists()) {
      console.log("[Worker] No devices found in database.");
      return;
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
        const maxOnDurationMinutes = device.maxOnDurationMinutes || 30;
        const elapsedMs = now - turnedOnAt;
        const maxDurationMs = maxOnDurationMinutes * 60 * 1000;

        if (elapsedMs > maxDurationMs) {
          console.log(`[Worker] IRON_SLOT cutoff triggered for ${deviceName} (${deviceId})`);
          updates[`devices/${deviceId}/status`] = "OFF";
          updates[`devices/${deviceId}/turnedOnAt`] = null;

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

      if ((status === "ERROR" || status === "DISCONNECTED") && device.lastAlertedStatus !== status) {
        const newAlertRef = alertsRef.push();
        updates[`devices/${deviceId}/lastAlertedStatus`] = status;
        updates[`alerts/${newAlertRef.key}`] = {
          id: newAlertRef.key,
          deviceId: deviceId,
          message:
            status === "ERROR"
              ? `Device error: ${deviceName} reported an error`
              : `Device disconnected: ${deviceName} is disconnected`,
          timestamp: now,
          acknowledged: false,
        };
      } else if (status === "ON" || status === "OFF") {
        updates[`devices/${deviceId}/lastAlertedStatus`] = null;
      }

      // 2. LIGHT_SCHEDULE Auto On/Off Logic
      if (deviceType === "LIGHT_SCHEDULE") {
        const scheduleStart = device.scheduleStart;
        const scheduleEnd = device.scheduleEnd;

        if (scheduleStart && scheduleEnd) {
          let shouldBeOn = false;

          if (scheduleStart <= scheduleEnd) {
            shouldBeOn = currentTimeStr >= scheduleStart && currentTimeStr < scheduleEnd;
          } else {
            shouldBeOn = currentTimeStr >= scheduleStart || currentTimeStr < scheduleEnd;
          }

          const targetStatus = shouldBeOn ? "ON" : "OFF";
          if (status !== targetStatus) {
            console.log(`[Worker] LIGHT_SCHEDULE toggling ${deviceName} (${deviceId}) to ${targetStatus}`);
            updates[`devices/${deviceId}/status`] = targetStatus;
          }
        }
      }
    });

    if (Object.keys(updates).length > 0) {
      await db.ref().update(updates);
      console.log("[Worker] Database updated successfully with automation changes.");
    } else {
      console.log("[Worker] No changes required at this time.");
    }
  } catch (error) {
    console.error("[Worker] Error executing automation worker:", error);
    process.exitCode = 1;
  }
}

runAutomationWorker().then(() => {
  if (process.env.WORKER_INTERVAL === "true") {
    setInterval(runAutomationWorker, 60 * 1000);
  }
});
