# UNO Card Game

A Java implementation of the classic UNO card game, featuring support for 2-4 players with both human and computer opponents.

This project was developed as a learning exercise to practice Java OOP concepts including classes, inheritance, encapsulation, and game logic. Implementation based on course tutorial, with debugging and modifications.
## Overview

This project implements the UNO card game rules, allowing players to compete against each other in a turn-based card game. The game enforces all standard UNO rules including card matching and draw penalties.

## Features

- **Multiplayer Support**: Play with 2-4 players in any combination of human and computer players
- **Card Mechanics**: Players must play cards matching the color or rank of the top card on the discard pile
- **Game Rules**: Implements standard UNO rules including card matching by color or rank
- **Player AI**: Computer players make strategic card decisions
- **Turn Management**: Handles player turns and game flow

## How to Run

1. Compile the Java files:
```
javac *.java
```

2. Run the game:
```
java UnoGameRunner
```

3. Follow the on-screen prompts to specify the number of players and make your moves during the game.

## Game Rules

Players take turns playing cards from their hand that match the color or rank of the top card on the discard pile. If a player cannot play a card, they must draw from the deck. The first player to empty their hand wins the round.

Special cards are NOT included in this game design.

## Project Structure

The project consists of the following main classes:

- **Card**: Represents a single card with color and rank
- **Deck**: Manages the deck of cards, including shuffling and drawing
- **Player**: Represents a player with their hand and turn logic
- **UnoGame**: Controls the overall game flow and turn order
- **UnoGameRunner**: Entry point for running the game

## Requirements

- Java 8 or higher
- No external dependencies


