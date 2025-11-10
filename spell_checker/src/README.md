# Spell Checker with Word Recommendation

A Java command-line application that detects misspelled words and suggests corrections using custom similarity algorithms.

## Technical Overview

**Language:** Java  
**Testing:** JUnit 5  
**Key Concepts:** Algorithm design, data structures, string processing

## Core Features

### Word Recommendation Engine
- **Candidate Filtering:** Filters dictionary words by length tolerance and character overlap
- **Similarity Ranking:** Custom left-right similarity algorithm compares character matches from both ends
- **Efficient Lookup:** HashSet implementation for O(1) dictionary searches
- **Custom Sorting:** Selection sort algorithm to return top N suggestions (Collections.sort not permitted per requirements)

### Interactive Spell Checker
- File I/O with robust exception handling
- User-friendly prompts for dictionary and input file selection
- Interactive correction options (replace with suggestion, accept as-is, or manual entry)
- Automated output file generation

## Algorithm Highlights

**Character Commonality Calculation:**
```
Percentage = |Set A ∩ Set B| / |Set A ∪ Set B|
```
Uses set theory to determine shared characters between words.

**Left-Right Similarity:**
```
Similarity = (Left-aligned matches + Right-aligned matches) / 2
```
Compares characters from both ends to rank suggestions by relevance.

## How to Run

### Prerequisites
- Java 11 or higher

### Running the Application

1. Compile the source files:
```bash
javac src/*.java
```

2. Run the program:
```bash
java -cp src SpellCheckerRunner
```

3. When prompted, enter file paths:
    - Dictionary file: `spell_checker/src/engDictionary.txt`
    - Input file to check: `spell_checker/src/sampleCheck.txt`

4. Output will be generated as: `spell_checker/src/sampleCheck_chk.txt`

### Sample Files Included

- **src/engDictionary.txt** - English dictionary for word validation
- **src/sampleCheck.txt** - Sample input text with misspellings
- **src/sampleCheck_chk.txt** - Example output showing corrections

## Testing

Comprehensive JUnit 5 test suite including:
- Unit tests for similarity calculations
- Property-based testing (validates size constraints, sorting order)
- Edge case handling (empty results, boundary values)
- Test fixture setup with `@BeforeEach` for test isolation

## Implementation Notes

This was originally a team assignment for CIT 591. This repository contains my complete independent implementation demonstrating:
- Data structure optimization for performance
- Algorithm design without using library sorting functions
- Test-driven development practices
- Clean code organization and error handling

## Sample Output
```
Input: "morbit"
Suggestions:
1. morbid
2. hobbit  
3. sorbet
4. forbid
```

## Skills Demonstrated

Java • Data Structures • Algorithm Design • Unit Testing • File I/O • Problem Solving

---

[View Code on GitHub](www.github.com/dingonewen)