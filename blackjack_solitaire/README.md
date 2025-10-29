# BLACKJACK SOLITAIRE

**Student Name:** Yiwen Ding  
**Contact:** dingywn@seas.upenn.edu  
**Date:** 2025-10-29  
**Course:** CIT 5910

*This project was completed independently as part of the course requirements.*

---

## PROJECT DESCRIPTION

This is a Java implementation of a one-player card game called Blackjack Solitaire, where the player attempts to build hands with values as close to 21 as possible without exceeding 21.

---

## FILES INCLUDED

- **Card.java**: Represents a single playing card
- **Deck.java**: Manages the collection of 52 cards
- **BlackjackSolitaire.java**: Manages the entire game flow, rules, and state
- **BlackjackSolitaireRunner.java**: Contains the Main method to run the game

---

## FEATURES IMPLEMENTED

### Board & Layout:
- Cross-shaped 4x5 grid with 16 placement positions
- 4 discard slots for unwanted cards
- Visual display of current game board state

### Card System:
- Standard 52-card deck
- Automatic deck shuffling
- Sequential card dealing (one at a time)

### Player Interaction:
- Position selection (1-20)
- Input validation (prevents placing on occupied spots)
- Current card display

### Scoring System:
- Automatic score calculation for all 9 hands (4 rows + 5 columns)
- Final score display at game end

---

## BOARD LAYOUT

The board is a cross-shaped 4x5 grid with 16 positions:
```
    Col 1  Col 2  Col 3  Col 4  Col 5
Row 1:  1     2      3      4      5
Row 2:  6     7      8      9     10
Row 3:  -    11     12     13      -
Row 4:  -    14     15     16      -
```

**Discard positions:** 17, 18, 19, 20

---

## GAME FLOW

1. Deck is shuffled.
2. A card is dealt from the deck.
3. The card dealt and the current board are displayed.
4. Player chooses a position (1-20).
5. Place the card at the chosen position. If the spot is already taken, the player must choose position again for the same card.
6. Repeat until all 16 board spots are full.
7. Calculate and display the final score based on the scoring rules.

---

## SCORING RULES

| Hand Type   | Description                         | Points |
|-------------|-------------------------------------|--------|
| Blackjack   | 2 cards that sum to 21              | 10     |
| 21          | 3, 4, or 5 cards that sum to 21     | 7      |
| 20          | Any hand that sums to 20            | 5      |
| 19          | Any hand that sums to 19            | 4      |
| 18          | Any hand that sums to 18            | 3      |
| 17          | Any hand that sums to 17            | 2      |
| 16 or less  | Any hand that sums to 16 or less    | 1      |
| BUST        | Any hand that sums to 22 or more    | 0      |

**Note:**
- Aces count as 1 or 11 (whichever is better for the hand).
- Face cards (J, Q, K) count as 10.

**Final score** = sum of all 9 hands (4 rows + 5 columns).

---

*End of Document*