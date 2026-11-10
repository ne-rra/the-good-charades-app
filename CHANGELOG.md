# Changelog

## [1.2.1] - 2026-11-10
### Changed
- Footer tidy-up: the in-app legal note is gone; the footer now simply credits the author.

## [1.2.0] - 2026-11-10
### Changed
- **Renamed to The Good Charades App**, with a new minimal logo (cream clue card, single gold accent). The previous name belonged to another company's trademark, so it has been removed everywhere: app label, package id (`com.rafan.goodcharades`), docs, and this repo. The "Godzilla & Kaiju" deck is now "Kaiju & Giant Monsters" (Godzilla remains a card, as a plain-text answer).
- **Refined visual theme**: the rainbow party gradients are replaced by a quiet charcoal-and-cream design with one gold accent and muted, desaturated deck colors throughout.
### Fixed
- **Phantom passes after a correct tilt.** The tilt detector now locks after each gesture and only re-arms once the phone has returned to neutral and settled there, so straightening the phone after a down-tilt can no longer pass the next word. Verified against synthetic sensor traces (down-nod with a hard overshoot return, double nods, fidget noise).
### Added
- **Best score per deck**, saved on the device and shown on each deck card, with a "New best" callout on the results screen.

## [1.1.0] - 2026-11-10
### Fixed
- **Tilt now works in landscape.** The native tilt sensor was rewritten to track the accelerometer's up-vector, so nod detection works the same in portrait, landscape-left and landscape-right. The app now runs in landscape like the classic game.
### Changed
- Full visual redesign: dark party theme, per-deck gradient colors that flood the game screen, gradient deck tiles, giant game typography, confetti bursts on correct answers and big results celebrations.

## [1.0.0] - 2026-11-10
### Added
- Native Android app (fullscreen WebView shell + native accelerometer tilt)
- 19 decks, 696 words — including Godzilla & Kaiju, Horror Legends, Anime & Animation, Superheroes & Villains, Fantasy Books & Worlds, Bangladesh & Desi Culture, and Engineering & Campus Life
- Tilt DOWN = Got It, tilt UP = Pass (flippable in Settings), with button backups
- 30/60/90/120-second rounds, streaks, sound effects, vibration, results screen
- Custom deck builder and Mixed Bag mode
- No ads, no paywall, no account
