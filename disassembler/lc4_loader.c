/************************************************************************/
/* File Name : lc4_loader.c		 										*/
/* Purpose   : This file implements the loader (ld) from PennSim		*/
/*             It will be called by main()								*/
/*             															*/
/* Author(s) : Yiwen Ding    											*/
/************************************************************************/

#include <stdio.h>
#include "lc4_memory.h"
#define SWAP(x) ((x) << 8 | (x) >> 8)  // solve the endianess issue

/* declarations of functions that must defined in lc4_loader.c */

FILE* open_file(char* file_name)
{
	FILE* file = fopen(file_name, "rb");  // rb to read binary file
	return file;
}

int parse_file (FILE* my_obj_file, row_of_memory** memory)
{
	short unsigned int header;
	short unsigned int address;
	short unsigned int n;

	if (my_obj_file == NULL) return 1;

	while (fread(&header, 2, 1, my_obj_file) == 1) {
		header = SWAP(header);           // SWAP endian everytime after sucessful fread
		if (fread(&address, 2, 1, my_obj_file) != 1) return 1;  // error check
		address = SWAP(address);
		if (fread(&n, 2, 1, my_obj_file) != 1) return 1;   // error check
		n = SWAP(n);
		if (header == 0xCADE || header == 0xDADA) {   // CODE or DATA, label is NULL
			for (int i = 0; i < n; i++) {
				short unsigned int contents;   // pass in to add_to_list()
				if (fread(&contents, 2, 1, my_obj_file) != 1) return 1;   // error check, read one word
					contents = SWAP(contents);				
					int result = add_to_list(memory, address + i, contents);   // add node to list
				if (result != 0) return 1;    // return 1 if add_to_list failed
			}
		} else if (header == 0xC3B7) { // SYMBOL 

			char* label = malloc(n + 1);
			if (label == NULL) return 1; //  check malloc
			if (fread(label, 1, n, my_obj_file) != n) {    // read label bytes
				free(label);
				return 1;
			}
			label[n] = '\0';   // don't forget null terninator

			row_of_memory* node = search_address(*memory, address);    // search for node with this address
			if (node != NULL) {
				if (node->label != NULL) {
					free(node->label);    // avoid memory leak
				}
				node->label = label;
			} else {    // SYMBOL before CODE
				int result = add_to_list(memory, address, 0);  // create a new node where contents = 0
				if (result != 0) {
					free(label);
					return 1;
				}
				node = search_address(*memory, address);
				if (node != NULL) {
					node->label = label; 
				} else {
					free(label);
					return 1;
				}
			}
		}
	}
	return 0;
}

