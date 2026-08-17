# Smart Home Hardware Simulator

A standalone web-based hardware simulator for testing the Smart Home Monitoring & Control system in real time.

## Features
- **Live Two-Way Realtime Sync**: Connects to Firebase Realtime Database and listens to `/floors` and `/devices` via Firebase Web SDK `onValue` listeners. It does not run a separate custom WebSocket server; Firebase manages the realtime connection.
- **Separated Floor Simulation**: Renders each floor as its own 2D grid, matching the mobile app's row/column placement.
- **Hardware Controls**: Each simulated device has interactive buttons (`ON`, `OFF`, `Error`, `Disc`) that write back to Firebase to simulate external hardware actions.
- **No Build Tooling**: Built with plain HTML, CSS, and modular Firebase v10 SDK via CDN. No Node.js or npm install required to run.

---

## How to Use

1. Open the simulator file in any web browser:
   - Double-click `simulator/index.html` on your computer, OR
   - Open it directly in your browser.
2. Enter your Firebase Realtime Database URL in the input box at the top (e.g., `https://madd-mini-project-default-rtdb.firebaseio.com`).
3. Click **Connect**.
4. Observe your floors and devices rendered live as separated grid simulations with status badges and simulation buttons.
