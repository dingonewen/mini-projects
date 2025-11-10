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

## Skills Demonstrated

Java • Data Structures • Algorithm Design • Unit Testing • File I/O • Problem Solving

---

[View Code on GitHub](www.dingonewen.github.io)