import * as admin from "firebase-admin";
import * as path from "path";
import * as fs from "fs";

// Initialize Firebase Admin SDK
// You can supply serviceAccountKey.json in the functions folder or root
const serviceAccountPath = path.join(__dirname, "../serviceAccountKey.json");

if (fs.existsSync(serviceAccountPath)) {
  const serviceAccount = JSON.parse(fs.readFileSync(serviceAccountPath, "utf8"));
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: process.env.FIREBASE_DATABASE_URL || "https://madd-mini-project-default-rtdb.firebaseio.com"
  });
} else {
  // Fallback to default application credentials if configured via environment variable
  admin.initializeApp();
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
  }
}

// Run every 1 minute (60,000 ms)
const INTERVAL_MS = 60 * 1000;
runAutomationWorker();
setInterval(runAutomationWorker, INTERVAL_MS);
