# Standalone Node.js Automation Worker

This directory contains a standalone Node.js background worker using the Firebase Admin SDK (`src/worker.ts`), providing a free alternative to Firebase Cloud Functions (no Blaze plan required).

## What it does
- Runs continuously (every 1 minute) and checks Realtime Database `/devices`.
- **Iron Slot Safety Cutoff**: Automatically turns `OFF` any `IRON_SLOT` device running longer than `maxOnDurationMinutes` and pushes a security alert to `/alerts`.
- **Light Schedule Automation**: Automatically syncs `LIGHT_SCHEDULE` device status (`ON`/`OFF`) based on current time against `scheduleStart` and `scheduleEnd`.

---

## How to Run

### 1. Generate Firebase Service Account Key
1. Go to Firebase Console > Project Settings > Service Accounts.
2. Click **Generate new private key** and save the JSON file as `serviceAccountKey.json` inside the `functions/` folder.

### 2. Run Locally
```bash
cd functions
npm install
npm run worker
```

### 3. Run via GitHub Actions (Free Scheduled Cloud Cron)
1. Push this repository to GitHub.
2. In your GitHub Repository Settings > Secrets and variables > Actions, add:
   - `FIREBASE_DATABASE_URL`: Your Realtime Database URL (e.g., `https://your-project-default-rtdb.firebaseio.com`)
   - `FIREBASE_SERVICE_ACCOUNT_KEY`: The entire content of your `serviceAccountKey.json` file as a secret string.
3. The GitHub Actions workflow (`.github/workflows/automation-worker.yml`) will automatically trigger every 1 minute to execute the worker on GitHub's runners for free.
