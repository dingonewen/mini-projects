# LC4 Disassembler

A disassembler for LC4 (Little Computer 4) assembly language. This project reads LC4 object files and converts machine code back into human-readable assembly instructions.

## Features

- Parses LC4 object files (.obj format)
- Supports CODE, DATA, and SYMBOL sections
- Disassembles arithmetic instructions (ADD, SUB, MUL, DIV)
- Handles both register and immediate mode instructions
- Memory management using ordered linked list
- Proper endianness handling

## Project Structure

```
disassembler/
├── lc4.c                    # Main program entry point
├── lc4_memory.c             # Linked list memory management
├── lc4_memory.h             # Memory structures and declarations
├── lc4_loader.c             # Object file loader implementation
├── lc4_loader.h             # Loader function declarations
├── lc4_disassembler.c       # Disassembler implementation
├── lc4_disassembler.h       # Disassembler declarations
├── lc4_memory_test.c        # Unit tests for memory management
└── Makefile                 # Build configuration
```

## Building

To compile the project, simply run:

```bash
make
```

This will generate the `lc4` executable.

To clean build artifacts:

```bash
make clean      # Removes .o files
make clobber    # Removes .o files and executable
```

## Usage

Run the disassembler with an LC4 object file:

```bash
./lc4 <object_file.obj>
```

### Example

```bash
./lc4 program.obj
```

Output format:
```
<label>    <address>   <contents>  <assembly>
           0000        1234        ADD R1, R2, R3
LOOP       0001        5678        MUL R5, R6, R7
```

## Testing

To test the memory management module independently:

```bash
clang -g lc4_memory_test.c lc4_memory.c -o test_memory
./test_memory
```

Run with Valgrind to check for memory leaks:

```bash
valgrind --leak-check=full ./test_memory
valgrind --leak-check=full ./lc4 program.obj
```

## Supported Instructions

Currently supports disassembly of:
- `ADD` (register and immediate modes)
- `SUB` (register mode)
- `MUL` (register mode)
- `DIV` (register mode)

## Requirements

- C compiler (clang or gcc)
- Make build system
- LC4 object files for testing

## Author

Yiwen Ding

## License

See LICENSE file for details.
