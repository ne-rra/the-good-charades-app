# Roadmap — The Good Charades App

## Shipped
- **v1.3.0** — Team match mode, streak bonuses (+3s every 5), Surprise-me random deck, per-deck best scores, refined charcoal/cream/gold theme.
- **v1.4.0** — The QA release: an automated audit of 400,000+ simulated checks drove fixes to match fairness (tied matches now get equal reply rounds), countdown/pause lifecycle, saved-data hardening, tilt detection (slow nods, diagonal holds, warm-up baseline, reposition adoption), settings + custom deck persistence, match resume, missed-words replay, bundled offline fonts, and accessibility (aria-live words, focus rings, contrast).

## Next
- Sound-effect pack v2
- More decks (suggest one in Issues!)
- Deck favorites / recently played row on the home screen
- Move Settings above the deck grid for quicker access

## Later / under consideration
- Play Store release — needs targetSdk 36, a committed Gradle wrapper, and round-state survival across process death (today a killed app restarts at the home screen; an in-progress round is lost)
- Prune the first-generation CSS that later themes fully override (~15 KB)
- Persist an in-progress round so rotating/recreating the activity mid-round resumes it

## Known trade-offs
- A fast, full reposition of the phone *mid-round* (e.g. handing it to another player) can register as a tilt — the detector cannot distinguish it from a deliberate nod. Between rounds and during the countdown it is absorbed by baseline adoption.
- Team matches trust the players: any deck and round length can be picked for any turn. A locked match format (same deck/length for all turns) is under consideration.
