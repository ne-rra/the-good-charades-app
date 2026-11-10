# Architecture

The Good Charades App is a **native Android shell around a self-contained web game**, chosen so the game logic ships as one fast, offline file while tilt uses the phone's real motion hardware.

## Pieces

| Piece | What it does |
|---|---|
| `app/src/main/java/com/rafan/goodcharades/MainActivity.java` | The whole native layer: fullscreen immersive `WebView`, keep-screen-on, and the accelerometer tilt sensor. |
| `app/src/main/assets/index.html` | The complete game (UI, 19 decks, timers, sounds, results) as a single self-contained HTML file that runs fully offline. |
| Native tilt bridge (injected at the end of the asset copy) | Exposes `window.handleNativeTilt('down' \| 'up')`. It validates the direction, reads a live state snapshot (`window.__gcaState`: tilt enabled, tilt mode, playing, paused) so it respects the Settings toggle and flip exactly, ignores repeats of the same direction within 350 ms, and clicks the game's own GOT IT / PASS buttons — the same code path as touch. On page load the native shell calls `window.__setNativeTilt(bool)`: if the device has no accelerometer, the web `deviceorientation` path is restored as a fallback instead of tilt being silently dead. |

## Tilt detection (native)

1. The accelerometer's gravity vector is low-pass filtered (α = 0.6). The nod angle is `atan2(gz, sqrt(gx² + gy²))` — the true in-plane magnitude, so the same nod measures identically in portrait, landscape and diagonal holds.
2. A baseline learns how the player holds the phone: a 15-sample averaged warm-up at launch, then slow drift tracking (0.05/sample, only within ±6° of neutral) so the baseline can never chase and absorb a gentle nod. If the phone settles at a clearly different angle for ~1.5 s (picked up and put on a forehead during the countdown), that angle is adopted as the new neutral.
3. A deviation beyond **24°** sustained for 2 samples fires once, then the detector **locks until the phone returns to within 6° of neutral and settles for 220 ms** (and at least 500 ms since the fire) — the return swing, including its overshoot, can never register as a second gesture.
4. Samples with a gravity magnitude outside 6.5–13.5 m/s² (free-fall, hard shake) are ignored.

The web game's own `deviceorientation` listener is disabled inside the app (the injected bridge replaces `attachTilt` with a no-op) so tilt can never arrive from two sources at once. Outside the app, in a regular browser, the web version uses `deviceorientation` as normal.

## Why this shape?

- **Offline & instant** — no server, no network calls, no ads SDK, nothing to track you.
- **One source of truth for content** — decks live in the single HTML file; the same file powers the web version of the game.
- **Tiny native surface** — one activity, no AndroidX, one permission (`VIBRATE`). Small, auditable, and fast to build.
