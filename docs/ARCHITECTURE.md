# Architecture

The Good Charades App is a **native Android shell around a self-contained web game**, chosen so the game logic ships as one fast, offline file while tilt uses the phone's real motion hardware.

## Pieces

| Piece | What it does |
|---|---|
| `app/src/main/java/com/rafan/goodcharades/MainActivity.java` | The whole native layer: fullscreen immersive `WebView`, keep-screen-on, and the accelerometer tilt sensor. |
| `app/src/main/assets/index.html` | The complete game (UI, 19 decks, timers, sounds, results) as a single self-contained HTML file that runs fully offline. |
| Native tilt bridge (injected at the end of the asset copy) | Exposes `window.handleNativeTilt('down' \| 'up')`. It only acts while the game screen is active, maps direction → correct/pass using the in-game hint labels (so the Settings flip is respected automatically), and clicks the game's own GOT IT / PASS buttons — the same code path as touch. |

## Tilt detection (native)

1. The accelerometer's Y/Z axes give a pitch angle for the phone (`atan2(z, y)`).
2. A slowly-tracking baseline (exponential average while the phone is near neutral) learns how the player holds the phone, so no calibration step is needed.
3. A tilt beyond **±22°** from baseline fires once, then the detector **disarms until the phone returns to neutral** — the same return-to-neutral rule the classic game uses, so one gesture can never double-fire.
4. A 700 ms minimum interval between triggers guards against shake.

The web game's own `deviceorientation` listener is disabled inside the app (the injected bridge replaces `attachTilt` with a no-op) so tilt can never arrive from two sources at once. Outside the app, in a regular browser, the web version uses `deviceorientation` as normal.

## Why this shape?

- **Offline & instant** — no server, no network calls, no ads SDK, nothing to track you.
- **One source of truth for content** — decks live in the single HTML file; the same file powers the web version of the game.
- **Tiny native surface** — one activity, no AndroidX, one permission (`VIBRATE`). Small, auditable, and fast to build.
