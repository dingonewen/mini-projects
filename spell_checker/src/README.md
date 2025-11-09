# Spell Checker Project

A Java spell checker that detects misspelled words from one file, suggests corrections based on similarity algorithms and return a new file.

## Author

Yiwen Ding - Independent implementation for portfolio

**Note:** This was originally a team assignment, but this version is my complete individual implementation.

## Objectives

- Read a dictionary file and identify misspelled words in an input file
- Generate word suggestions based on length tolerance and character similarity
- Rank suggestions using a custom left-right similarity algorithm
- Provide an interactive interface for users to correct misspelled words
- Write comprehensive unit tests for the recommendation system

## Features

### Word Recommendation System
- Filters candidate words by length difference (tolerance)
- Calculates character commonality using set operations
- Ranks suggestions by left-right similarity score
- Returns top N most similar words
- Custom sorting implementation (no Collections.sort allowed)

### Spell Checker Interface
- Prompts user for dictionary and input files
- Detects words not in dictionary
- Displays top 4 suggestions for each misspelled word
- Allows user to replace, accept, or manually correct words
- Outputs corrected text to a new file

### Testing
- JUnit 4 test suite for WordRecommender
- Tests for similarity calculations, filtering, and sorting
- Edge case testing (empty results, boundary values)

## Technologies

- Java
- JUnit 4
- Data structures: HashMap, HashSet, ArrayList

## How to Run
```bash
javac SpellCheckerRunner.java
java SpellCheckerRunner
```

Follow prompts to enter dictionary file and file to spell check.