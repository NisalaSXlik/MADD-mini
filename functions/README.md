# Smart Home Firebase Cloud Functions

This directory contains the Firebase Cloud Functions (TypeScript) for backend automation of the Smart Home system.

## Features
1. **Iron Slot Safety Cutoff**: Checks devices of type `IRON_SLOT` every 1 minute. If a device has been `ON` longer than `maxOnDurationMinutes`, its status is set to `OFF` and a security alert is recorded in `/alerts`.
2. **Light Schedule Automation**: Checks devices of type `LIGHT_SCHEDULE` every 1 minute and automatically toggles their status (`ON`/`OFF`) based on whether the current time (`HH:mm`) falls within `scheduleStart` and `scheduleEnd`.

---

## Deployment & Setup Instructions

### Prerequisites
- Node.js (v18+ recommended)
- Firebase CLI installed globally (`npm install -g firebase-tools`)

### 1. Initialize / Login to Firebase
```bash
firebase login
```

### 2. Install Dependencies
Navigate into the `functions` folder and install packages:
```bash
cd functions
npm install
```

### 3. Build the TypeScript Code
```bash
npm run build
```

### 4. Run Locally (Emulators)
```bash
npm run serve
```

### 5. Deploy to Firebase
```bash
firebase deploy --only functions
```
