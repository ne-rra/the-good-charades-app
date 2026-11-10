# 🎉 The Good Charades App

> The classic forehead charades game — rebuilt the way it should be: **no ads, no paywall, no account, everything unlocked.** Hold the phone to your forehead, your friends shout clues, you guess.

A personal project by **Rafan Sadad Rahman**.

![Platform](https://img.shields.io/badge/platform-Android-brightgreen) ![Version](https://img.shields.io/badge/version-1.4.0-orange) ![License](https://img.shields.io/badge/license-MIT-blue) ![Ads](https://img.shields.io/badge/ads-none-red) ![Paywall](https://img.shields.io/badge/paywall-none-red)

---

## 📱 Download & install

1. Go to the [**Releases**](https://github.com/ne-rra/the-good-charades-app/releases) page and download `TheGoodCharades-v1.4.0.apk`.
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

## 🗂️ The decks — 19 decks · 698 words

| Deck | Deck | Deck |
|---|---|---|
| 🦁 Animals | 🍕 Food & Drink | 🏃 Act It Out |
| 🎬 Movies & TV | ⭐ Famous People | 🌍 Around the World |
| ⚽ Sports & Games | 💼 Jobs & People | 🏠 Everyday Objects |
| 🎵 Music | 🔬 Science & Nature | 🧠 Brain Melters (hard mode) |
| 🦖 Kaiju & Giant Monsters | 👻 Horror Legends | 🌸 Anime & Animation |
| 🦸 Superheroes & Villains | 🐉 Fantasy Books & Worlds | 🇧🇩 Bangladesh & Desi Culture |
| ⚙️ Engineering & Campus Life | ✏️ **+ build your own deck** in the app | 🔀 Mixed Bag (all decks shuffled) |

## ✨ Features

- 🚫 **Zero ads, zero paywall, zero sign-up** — every deck unlocked from the start
- 📐 **Native tilt controls** with return-to-neutral debouncing, plus big on-screen buttons as backup
- ⏱️ Adjustable round timers, streak counter, sound effects & vibration
- 🌑 Refined charcoal-and-cream dark UI with huge forehead-readable words
- 👥 **Team match mode** — two teams, alternating rounds, best total after two rounds each wins
- 🔥 **Streak bonuses** — every 5 correct in a row adds +3 seconds
- ⭐ Per-deck best scores, a 🎲 Surprise-me random deck, and settings that remember themselves
- 🔤 Fonts are bundled in the app — it works fully offline, no network calls at all
- ✏️ **Custom deck builder** — your own words for inside jokes
- 🔀 Shuffled rounds with **no repeats** in a round

## 🛠️ Build from source

Clone the repo and open it in **Android Studio** (JDK 17, Android SDK API 34), then Run — or grab the ready-made APK from [Releases](https://github.com/ne-rra/the-good-charades-app/releases). The Gradle wrapper is not committed yet (see ROADMAP.md), so there is no `./gradlew` in a fresh clone.

How it works under the hood: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)

## 🗺️ Roadmap

- [x] Team-vs-team match mode (v1.3.0)
- [x] Streak bonuses, Surprise-me deck, per-deck best scores (v1.3.0)
- [x] Fully offline — fonts bundled, no network calls (v1.4.0)
- [ ] Sound-effect pack v2
- [ ] More decks (suggest one in Issues!)
- [ ] Play Store release

Full roadmap: [ROADMAP.md](ROADMAP.md)

## 👤 Author

**Rafan Sadad Rahman** — Mechanical Engineering, University of Texas at Arlington (Class of 2028)
GitHub: [@ne-rra](https://github.com/ne-rra)

## 📄 License

MIT — see [LICENSE](LICENSE). Play it, fork it, make it yours.

## Legal note

The Good Charades App is a free, fan-made party game. It is **not affiliated with, sponsored, or endorsed by** any rights holder. Charades itself is a traditional party game. Movie, show, book and character names appear inside the game only as guessing answers — plain-text references that belong to their respective owners; the app uses no third-party logos, artwork, music or fonts beyond openly licensed ones (Archivo Black & Outfit, SIL Open Font License).
