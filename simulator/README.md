# Smart Home Hardware Simulator

A standalone web-based hardware simulator for testing the Smart Home Monitoring & Control system in real time.

## Features
- **Live Two-Way Realtime Sync**: Instantly connects to your Firebase Realtime Database and listens to `/devices` via `onValue`. Any change made in the Android mobile app reflects instantly in the web simulator, and vice versa.
- **Hardware Controls**: Each device card has interactive simulator buttons (`Set ON`, `Set OFF`, `Disconnect`) that write back to Firebase to simulate external sensor/hardware actions.
- **No Build Tooling**: Built with plain HTML, CSS, and modular Firebase v10 SDK via CDN. No Node.js or npm install required to run.

---

## How to Use

1. Open the simulator file in any web browser:
   - Double-click `simulator/index.html` on your computer, OR
   - Open it directly in your browser.
2. Enter your Firebase Realtime Database URL in the input box at the top (e.g., `https://madd-mini-project-default-rtdb.firebaseio.com`).
3. Click **Connect & Load Devices**.
4. Observe your devices rendered live as interactive cards with status badges and simulation buttons.
