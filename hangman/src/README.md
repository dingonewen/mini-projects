
# Evil Hangman

A Java implementation of the Evil Hangman game - a devious twist on the classic word-guessing game where the computer actively works against the player by delaying the selection of the target word for as long as possible.

## 🎮 What is Evil Hangman?

Unlike traditional Hangman where the computer picks a word at the start, Evil Hangman maintains a list of possible words and only narrows it down as the player makes guesses. The computer always chooses the largest remaining word family to maximize the game's difficulty.

### Example:
- Player guesses 'E'
- Computer groups remaining words by pattern:
  - Pattern `_E__`: ["heal", "belt"] (2 words)
  - Pattern `E___`: ["echo"] (1 word)
- Computer chooses pattern `_E__` (larger family) ✅
- The game becomes harder!

## 🏗️ Project Structure

```
hangman/
├── src/
│   ├── EvilHangman.java       # Game flow and user interaction
│   ├── EvilSolution.java      # Core game logic and algorithm
│   └── EvilHangmanRunner.java # Main entry point
├── test/
│   ├── EvilHangmanTest.java
│   └── EvilSolutionTest.java
├── README.md
├── engDictionary.txt
└── testDictionary.txt

```

## 🚀 How to Run

### Prerequisites
- Java 21 or higher
- JUnit Jupiter 5.14.1 (for tests)

### Run the Game
```bash
# From project root directory
javac hangman/src/*.java
java -cp hangman/src EvilHangmanRunner
```

### Run Tests
```bash
# Using IntelliJ IDEA: Right-click on test files and select "Run"
# Or use your preferred test runner with JUnit 5
```

## 💡 Key Features

### 1. **Adaptive Word Selection**
The game dynamically selects words of random length from the dictionary, making each game unique and unpredictable.

### 2. **Pattern-Based Word Families**
Words are grouped into families based on where the guessed letter appears:
- `createPattern()`: Generates patterns for each word
- `patternGroup()`: Groups words by identical patterns
- Always selects the largest family to maximize difficulty

### 3. **Robust Input Validation**
- Validates single-character input
- Prevents duplicate guesses
- Ensures alphabetic characters only

### 4. **Mixed-Length Word Support**
The implementation handles dictionaries with words of varying lengths by:
- Collecting all unique word lengths
- Randomly selecting one length
- Filtering to only use words of that length

## 🔧 Implementation Highlights

### EvilSolution Class
**Core Algorithm:**
```java
public boolean addGuess(char guess) {
    // 1. Group words into pattern families
    HashMap<ArrayList<Character>, ArrayList<String>> families = patternGroup(guess);

    // 2. Find the largest family
    ArrayList<Character> updatedPattern = null;
    int maxSize = 0;
    for (ArrayList<Character> pattern : families.keySet()) {
        if (families.get(pattern).size() > maxSize) {
            maxSize = families.get(pattern).size();
            updatedPattern = pattern;
        }
    }

    // 3. Update game state with largest family
    partialSolution = updatedPattern;
    currentWordList = families.get(updatedPattern);

    return guessCorrect;
}
```

**Key Design Decisions:**
- **Private fields** with appropriate access methods for encapsulation
- **Helper methods** (`patternGroup`, `createPattern`) for separation of concerns
- **Defensive copying** to prevent external modification
- **Edge case handling** for empty/null word lists

### EvilHangman Class
**Responsibilities:**
- File I/O for dictionary loading (with proper resource management)
- User interaction and input validation
- Game flow coordination
- Separation of UI logic from game logic

## 📊 Design Principles Applied

- ✅ **Single Responsibility Principle**: Each class has one clear purpose
- ✅ **Encapsulation**: All fields are private with controlled access
- ✅ **High Cohesion**: Methods within classes work together for a unified purpose
- ✅ **Low Coupling**: Minimal dependencies between classes
- ✅ **Resource Management**: File streams properly closed with try-with-resources

## 🧪 Testing

The project includes comprehensive test coverage:
- **EvilSolutionTest**: Tests core game logic, pattern generation, and edge cases
- **EvilHangmanTest**: Tests user interaction, input validation, and game flow

### Test Highlights:
- Empty word list validation
- Mixed-length word list handling
- Game completion scenarios
- Edge cases (single-word lists, duplicate guesses)

## 🎯 Challenges Solved

### 1. IndexOutOfBoundsException
**Problem**: Mixed-length word lists caused crashes when accessing pattern indices.

**Solution**: Filter words by randomly selected length before initializing the game.

### 2. Resource Leaks
**Problem**: File streams weren't being closed properly.

**Solution**: Implemented try-with-resources for automatic resource management.

### 3. Autograder Requirements
**Problem**: Tests expected random length selection from mixed-length dictionaries.

**Solution**: Added length randomization and filtering logic in constructor.

## 📝 Code Style

- Consistent indentation (4 spaces)
- CamelCase naming conventions
- Proper spacing around operators
- Meaningful variable and method names
- Comprehensive inline comments

## 🔮 Future Enhancements

- Add difficulty levels (word length ranges)
- Track statistics (win rate, average guesses)
- GUI interface using JavaFX
- Multiplayer support
- Dictionary customization

## 👨‍💻 Author

Yiwen Ding
github@dingonewen
Developed as part of a Java programming course, focusing on object-oriented design, algorithms, and best practices.

---

**Note**: This implementation demonstrates key computer science concepts including hash-based grouping, greedy algorithms, and defensive programming techniques.
