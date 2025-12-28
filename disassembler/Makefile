all: lc4

lc4: lc4.o lc4_memory.o lc4_loader.o lc4_disassembler.o
	clang -g lc4.o lc4_memory.o lc4_loader.o lc4_disassembler.o -o lc4

lc4.o: lc4.c lc4_memory.h lc4_loader.h lc4_disassembler.h
	clang -g lc4.c -c

lc4_memory.o: lc4_memory.c lc4_memory.h
	clang -g lc4_memory.c -c

lc4_loader.o: lc4_loader.c lc4_loader.h lc4_memory.h
	clang -g lc4_loader.c -c

lc4_disassembler.o: lc4_disassembler.c lc4_disassembler.h lc4_memory.h 
	clang -g lc4_disassembler.c -c

clean:
	rm -rf *.o

clobber: clean
	rm -rf lc4