import * as admin from "firebase-admin";
import * as path from "path";
import * as fs from "fs";

const serviceAccountPath = path.join(__dirname, "../serviceAccountKey.json");

if (fs.existsSync(serviceAccountPath)) {
  const serviceAccount = JSON.parse(fs.readFileSync(serviceAccountPath, "utf8"));
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: process.env.FIREBASE_DATABASE_URL || "https://madd-mini-project-default-rtdb.firebaseio.com"
  });
} else {
  admin.initializeApp();
}

const db = admin.database();

async function seedDatabase() {
  console.log("[Seed] Starting database seeding with valid DeviceType values...");

  const floors = {
    "floor_1": {
      id: "floor_1",
      name: "Ground Floor",
      levelNumber: 1,
      gridRows: 6,
      gridCols: 6,
      blueprintUrl: ""
    },
    "floor_2": {
      id: "floor_2",
      name: "First Floor",
      levelNumber: 2,
      gridRows: 6,
      gridCols: 6,
      blueprintUrl: ""
    }
  };

  const now = Date.now();
  const devices = {
    "dev_1": {
      id: "dev_1",
      name: "Living Room Outlet",
      type: "OUTLET",
      status: "ON",
      floorId: "floor_1",
      gridX: 2,
      gridY: 3,
      turnedOnAt: now
    },
    "dev_2": {
      id: "dev_2",
      name: "Kitchen Iron",
      type: "IRON_SLOT",
      status: "ON",
      floorId: "floor_1",
      gridX: 4,
      gridY: 2,
      maxOnDurationMinutes: 30,
      turnedOnAt: now - (35 * 60 * 1000)
    },
    "dev_3": {
      id: "dev_3",
      name: "Porch Scheduled Light",
      type: "LIGHT_SCHEDULE",
      status: "OFF",
      floorId: "floor_1",
      gridX: 1,
      gridY: 1,
      scheduleStart: "18:00",
      scheduleEnd: "06:00"
    },
    "dev_4": {
      id: "dev_4",
      name: "Living Room Multi-Switch",
      type: "MULTI_SWITCH",
      status: "ON",
      floorId: "floor_1",
      gridX: 3,
      gridY: 4
    },
    "dev_5": {
      id: "dev_5",
      name: "Master Bedroom Camera",
      type: "CAMERA",
      status: "ON",
      floorId: "floor_2",
      gridX: 2,
      gridY: 2
    },
    "dev_6": {
      id: "dev_6",
      name: "Upstairs Hallway Light",
      type: "LIGHT_SCHEDULE",
      status: "OFF",
      floorId: "floor_2",
      gridX: 4,
      gridY: 3,
      scheduleStart: "19:00",
      scheduleEnd: "05:00"
    }
  };

  const alerts = {
    "alert_1": {
      id: "alert_1",
      deviceId: "dev_2",
      message: "Safety cutoff: Kitchen Iron exceeded max on-duration",
      timestamp: now - (5 * 60 * 1000),
      acknowledged: false
    }
  };

  await db.ref("floors").set(floors);
  await db.ref("devices").set(devices);
  await db.ref("alerts").set(alerts);

  console.log("[Seed] Database successfully seeded with correct DeviceTypes!");
  process.exit(0);
}

seedDatabase().catch((err) => {
  console.error("[Seed] Error seeding database:", err);
  process.exit(1);
});
