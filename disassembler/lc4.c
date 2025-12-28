/************************************************************************/
/* File Name : lc4.c 													*/
/* Purpose   : This file contains the main() for this project			*/
/*             main() will call the loader and disassembler functions	*/
/*             															*/
/* Author(s) : Yiwen Ding												*/
/************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "lc4_memory.h"
#include "lc4_loader.h"
#include "lc4_disassembler.h"

/* program to mimic pennsim loader and disassemble object files */

int main (int argc, char** argv) {

	/**
	 * main() holds the linked list &
	 * only calls functions in other files
	 */

	/* step 1: create head pointer to linked list: memory 	*/
	row_of_memory* memory = NULL ;

	/* step 2: determine filename, then open it		*/
	/*   TODO: extract filename from argv, pass it to open_file() */
	if (argc < 2) {    // make sure there is a file passed in
		printf("error1: usage: ./lc4 <object_file.obj>\n"); 
		return 1;
	}
	FILE* file = open_file(argv[1]);
		if (file == NULL) {
			printf("error1: failed to open the file\n");
			return 1;
		}
	/* step 3: call function: parse_file() in lc4_loader.c 	*/
	/*   TODO: call function & check for errors		*/
	if (parse_file(file, &memory) != 0) {
		printf("error1: failed to parse the file\n");
		fclose(file);
		delete_list(&memory); // free the allocated memory
		return 1;
	}
	if (fclose(file) != 0) {           // return 2 if cannot close the file
		printf("error2: failed to close the file\n");
		delete_list(&memory);
		return 2;
	}

	/* step 4: call function: reverse_assemble() in lc4_disassembler.c */
	/*   TODO: call function & check for errors		*/
	if (reverse_assemble(memory) != 0) {
		printf("error1: failed to reverse assemble\n");
		delete_list(&memory);
		return 1;
	}

	/* step 5: call function: print_list() in lc4_memory.c 	*/
	/*   TODO: call function 				*/
	print_list(memory);

	/* step 6: call function: delete_list() in lc4_memory.c */
	/*   TODO: call function & check for errors		*/
	if (memory != NULL) {
		delete_list(&memory);
	}

	/* only return 0 if everything works properly */
	return 0 ;
}
