# GwentStone - README

## Overview

This project is a Java-based card game where two players take turns in a battle on a **4x5 board**. The game is
designed to be well-organized and easy to maintain using object-oriented programming principles. Players place cards on
the board, representing minions with special abilities, and try to win by bringing down their opponent's **hero**.

---

## Structure

The project is divided into the following main components:

### 1. **Actions**

The `actions` package is where all the main moves and commands in the game are handled. It includes everything the
players or cards can do during the game, like attacking, using abilities, or placing cards on the board.
This package makes sure that all actions follow the rules of the game and are executed properly.

---

### 2. **Cards**
The `cards` package is the foundation of the game, as it contains all the card-related components. Cards are split into
two main categories: heroes and minions. The main class is the Card class. It is extended by the Minion and Hero
classes. These classes are further specialised into various subclasses which have special abilities needed for the game
interaction.

---

### 3. **Game Mechanics**

The `game` package forms the core of the application, managing players, the board, and game flow. The Game class
orchestrates the turn-based gameplay, handling the board's 4x5 grid, turn logic, and win conditions. Each Player has a
Deck for drawing cards, a Hand for playing them, and a Hero with unique abilities. The Row enum distinguishes between
front and back rows on the board, as needed for placing the different types of minions.

---

### 4. **Game Setup**

The setup and initialization of the game are managed by the following classes:

- **StartGame.java**: Handles the setup logic for starting a new game, including shuffling decks and assigning
  starting turns.
- **Main.java**: Entry point for the game. It orchestrates the initialization, execution, and management of game
  commands.

---
### Object-Oriented Programming (OOP) Concepts

1. **Encapsulation**:
- Each class manages its own state and behavior through private fields and public methods.

2. **Inheritance**:
- The `Card` class serves as a base for specialized cards like `Hero` and `Minion`, sharing common attributes like 
`mana` and `description`. Heroes and minions extend this base, adding unique behaviors such as special abilities.

3. **Polymorphism**:
- Hero abilities are implemented as abstract methods in the `Hero` class, allowing each subclass to define its specific
behavior while maintaining a consistent interface.

4. **Abstraction**:
- The use of abstract classes like `Hero` and `Minion`.

---

### Game Flow and Interactions

The game begins with two players selecting a hero and deck, initialized in the `Game` class. Players alternate turns,
drawing cards, placing them on the 4x5 board, and activating abilities. Turn logic and actions like attacking or using
abilities are managed by dedicated classes in the `actions` package. Heroes contribute unique abilities, while the
dynamic interactions between cards, players, and the board create strategic depth and variability in gameplay.

---
