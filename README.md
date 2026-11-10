# 🎉 The Good Charades App

> The classic forehead charades game — rebuilt the way it should be: **no ads, no paywall, no account, everything unlocked.** Hold the phone to your forehead, your friends shout clues, you guess.

A personal project by **Rafan Sadad Rahman**.

![Platform](https://img.shields.io/badge/platform-Android-brightgreen) ![Version](https://img.shields.io/badge/version-1.2.1-orange) ![License](https://img.shields.io/badge/license-MIT-blue) ![Ads](https://img.shields.io/badge/ads-none-red) ![Paywall](https://img.shields.io/badge/paywall-none-red)

---

## 📱 Download & install

1. Go to the [**Releases**](https://github.com/ne-rra/the-good-charades-app/releases) page and download `TheGoodCharades-v1.2.1.apk`.
2. Open the APK on your Android phone.
3. If Android asks, allow **“Install unknown apps”** for your browser/files app (this app isn't on the Play Store — it installs directly).
4. Tap **Install**, open **The Good Charades App**, pick a deck, and play.

> Requires Android 8.0 (Oreo) or newer.

## 🎮 How to play

One player holds the phone against their forehead, screen facing their friends. Friends describe, act out, or make sounds for the word on screen — without saying the word itself.

| Action | Control |
|---|---|
| ✅ **Got it!** | **Tilt the phone DOWN** (or tap **GOT IT!**) |
| ⏭️ **Pass** | **Tilt the phone UP** (or tap **PASS**) |
| ⏸️ Pause | Pause button (top corner) |

- Tilt uses the phone's **real motion sensor** (native accelerometer) — and you can **flip** which direction means what in Settings.
- Rounds are **30 / 60 / 90 / 120 seconds** — your choice.
- Results screen shows your score, accuracy, best streak, and every word you got or passed.

Full rules: [docs/HOW_TO_PLAY.md](docs/HOW_TO_PLAY.md)

## 🗂️ The decks — 19 decks · 696 words

| Deck | Deck | Deck |
|---|---|---|
| 🦁 Animals | 🍕 Food & Drink | 🏃 Act It Out |
| 🎬 Movies & TV | ⭐ Famous People | 🌍 Around the World |
| ⚽ Sports & Games | 💼 Jobs & People | 🏠 Everyday Objects |
| 🎵 Music | 🔬 Science & Nature | 🧠 Brain Melters (hard mode) |
| 🦖 Godzilla & Kaiju | 👻 Horror Legends | 🌸 Anime & Animation |
| 🦸 Superheroes & Villains | 🐉 Fantasy Books & Worlds | 🇧🇩 Bangladesh & Desi Culture |
| ⚙️ Engineering & Campus Life | ✏️ **+ build your own deck** in the app | 🔀 Mixed Bag (all decks shuffled) |

## ✨ Features

- 🚫 **Zero ads, zero paywall, zero sign-up** — every deck unlocked from the start
- 📐 **Native tilt controls** with return-to-neutral debouncing, plus big on-screen buttons as backup
- ⏱️ Adjustable round timers, streak counter, sound effects & vibration
- 🌗 Polished UI that adapts to light/dark mode, huge forehead-readable words
- ✏️ **Custom deck builder** — your own words for inside jokes
- 🔀 Shuffled rounds with **no repeats** in a round

## 🛠️ Build from source

```bash
git clone https://github.com/ne-rra/the-good-charades-app.git
cd the-good-charades-app
./gradlew assembleDebug
# APK lands in app/build/outputs/apk/debug/
```

Requirements: **JDK 17** and the **Android SDK (API 34)** — or just open the project in Android Studio and hit Run.

How it works under the hood: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)

## 🗺️ Roadmap

- [ ] Sound-effect pack v2
- [ ] Team-vs-team score mode
- [ ] More decks (suggest one in Issues!)
- [ ] Play Store release

## 👤 Author

**Rafan Sadad Rahman** — Mechanical Engineering, University of Texas at Arlington (Class of 2028)
GitHub: [@ne-rra](https://github.com/ne-rra)

## 📄 License

MIT — see [LICENSE](LICENSE). Play it, fork it, make it yours.

## Legal note

The Good Charades App is a free, fan-made party game. It is **not affiliated with, sponsored, or endorsed by** any rights holder. Charades itself is a traditional party game. Movie, show, book and character names appear inside the game only as guessing answers — plain-text references that belong to their respective owners; the app uses no third-party logos, artwork, music or fonts beyond openly licensed ones (Archivo Black & Outfit, SIL Open Font License).
