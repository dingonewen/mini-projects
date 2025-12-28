/************************************************************************/
/* File Name : lc4_disassembler.c 										*/
/* Purpose   : This file implements the reverse assembler 				*/
/*             for LC4 assembly.  It will be called by main()			*/
/*             															*/
/* Author(s) : Yiwen Ding 												*/
/************************************************************************/

#include <stdio.h>
#include "lc4_memory.h"

int reverse_assemble (row_of_memory* memory)  // only consider ADD, MUL, SUB, DIV, ADD IMM5
{
	row_of_memory* node = NULL;
	while ((node = search_opcode(memory, 0x1)) != NULL) {  // loop over opcode = 0001 && disasembled
		short unsigned int contents = node->contents;     // get the binary instruction and translate to human-readable assembly later
		short unsigned int rd = (contents >> 9) & 0x7;  // Rd = [11:9], 0x7 keeps [2:0]
		short unsigned int rs = (contents >> 6) & 0x7;  // Rs = [8:6]
		short unsigned int rt = contents & 0x7;          // Rt = [2:0]
		short unsigned subopcode = (contents >> 3) & 0x7;   // subopcode = [5:3]
		char assembly[100];   // allocate memory on stack first, save to heap at the end of the function
		
		short unsigned int bit5 = (contents >> 5) & 0x1;
		if (bit5 == 1) {   // check if imm
			short int imm5 = contents & 0x1F;   // get [4:0]
			if (imm5 & 0x10) {         // check if bit4 = 1 (negative)
				imm5 |= 0xFFE0;        // sign extend to 16 bits
			}  // ADD IMM5
			sprintf(assembly, "ADD R%d, R%d, #%d", rd, rs, imm5);   // sprintf to create and save the string

		} else {
			switch(subopcode) {    // use swtich() for different subopcode 
				case 0:   // ADD
					sprintf(assembly, "ADD R%d, R%d, R%d", rd, rs, rt);
					break;
				case 1:   // MUL
					sprintf(assembly, "MUL R%d, R%d, R%d", rd, rs, rt);
					break;
				case 2:   // SUB
					sprintf(assembly, "SUB R%d, R%d, R%d", rd, rs, rt);
					break;
				case 3:   // DIV
					sprintf(assembly, "DIV R%d, R%d, R%d", rd, rs, rt);
					break;
				default:    // if not these instructions, return 1
					return 1;
												
			}
		}
		node->assembly = malloc(strlen(assembly) + 1);   // allocate spaces on the heap for assembly string
		if (node->assembly == NULL) {
			return 1;
		}
		strcpy(node->assembly, assembly);  // copy assembly to the heap
	}
	return 0 ;
}
