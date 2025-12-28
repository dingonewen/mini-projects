/************************************************************************/
/* File Name : lc4_memory.c		 										*/
/* Purpose   : This file implements the linked_list helper functions	*/
/* 			   to manage the LC4 memory									*/
/*             															*/
/* Author(s) : Yiwen Ding												*/
/************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "lc4_memory.h"


/*
 * adds a new node to linked list pointed to by head - MUST keep list in order by address
 */
int add_to_list (row_of_memory** head,
		 short unsigned int address,
		 short unsigned int contents)
{
	/* check if the address exists */
	row_of_memory* exist_add = *head;
	while (exist_add != NULL) {
		if (exist_add->address == address) {
			exist_add->contents = contents;      // update contents if the address already exists
			return 0;
		}
		exist_add = exist_add->next;
	}
	/* allocate memory for a single node */
	row_of_memory* new_node = malloc(1 * sizeof(row_of_memory));
	if (new_node == NULL) {return -1;}     // check NULL malloc
	/* populate fields in newly allocated node w/ address&contents (it's ok if CONTENTS=NULL) */
	new_node->address = address;
	new_node->contents = contents;
	new_node->label = NULL;
	new_node->assembly = NULL;
	new_node->next = NULL;
	/* if head==NULL, node created is the new head of the list! */
	if (*head == NULL) {         // new_node becomes the head node if it's empty list
		*head = new_node;
		return 0;
	}
	/* otherwise, traverse linked list until you reach an address before the address passed in */
	if (address < (*head)->address) {    // address field here is int, not pointer
		new_node->next = *head;          // check if adding to the beginning of the list
		*head = new_node;
		return 0;
	}
	row_of_memory* current = *head;
	while (current->next != NULL && current->next->address < address) {  // advancing the pointer until we hit the end AND the next node's address is still smaller 
		current = current->next;
	}
	/* insert node into the list - perform necessary "surgery" on this list */
	new_node->next = current->next;
	current->next = new_node;
	/* REMEMBER, .OBJ file can be out of ORDER!  Make certain to insert incoming node in order by address!! */
	/* return 0 for success, -1 if malloc fails */
	return 0;
}

int delete_from_list     (row_of_memory** head,
					short unsigned int address) 
{	
	if (*head == NULL) {return -1;}   //check if it's empty list

	row_of_memory* current = *head;
	row_of_memory* last = NULL;
	while (current != NULL) {
		if (current->next != NULL && current->next->address == address) {       // check if we are at the right address
			if (last == NULL) {
				*head = current->next;
			} else {
				last->next = current->next;   // skip current if we are not deleting the first node
			}
			if (current->label != NULL) {     // free nodes
				free(current->label);
			}
			if (current->assembly != NULL) {
				free(current->assembly);
			}
			return 0;
		}
		last = current;      // if not found, advance both pointers
		current = current->next;
	}
	return -1;
}

/*
 * search linked list by address field, returns node if found
 */
row_of_memory* search_address (row_of_memory* head,
			       short unsigned int address )
{
	/* traverse linked list, searching each node for "address"  */
	while ((head != NULL) && (head->address != address)) {
		head = head->next;
	}
	/* return pointer to node in the list if item is found */
	return head;
	/* return NULL if list is empty or if "address" isn't found */
	if (head == NULL) {return NULL;}; 
}

/*
 * search linked list by opcode field, returns node if found
 */
row_of_memory* search_opcode  (row_of_memory* head,
				      short unsigned int opcode  )
{
	/* traverse linked list until node is found with matching opcode
	   AND "assembly" field of node is empty */
	while ((head != NULL)) {
		short unsigned int node = head->contents >> 12;   // shift right to get the 4 MSB
		if (node == opcode && head->assembly == NULL) {return head; }
		head = head->next;
	}
	/* return pointer to node in the list if item is found */
	/* return NULL if list is empty or if no matching nodes */
	return NULL ;
}


void print_list (row_of_memory* head )
{
	/* make sure head isn't NULL */
	if (head == NULL) return;
	/* print out a header */
	/* traverse linked list, print contents of each node */
	printf("%-10s %-10s %-11s %s\n", "<label>", "<address>", "<contents>","<assembly>");
	while(head != NULL) {
		if (head->label != NULL) {   
			printf("%-12s", head->label);       // print label
		} else {
			printf("%-12s","");
		}
		printf("%04X       ", head->address);    // print address
		printf("%04X       ", head->contents);   // print contents
		if (head->assembly != NULL) {
			printf("%s", head->assembly);       // print assembly
		} else {
			printf("");
		}
		printf("\n");
		head = head->next;   // advance pointer
	}
	return ;
}

/*
 * delete entire linked list
 */
int delete_list    (row_of_memory** head )
{
	/* check head is NULL */
	if (head == NULL) {    // check if NULL was passed in
		return -1;
	}
	if (*head == NULL) {     // check if the Linked List is empty
		return 0;   
	}
	/* delete entire list node by node */
	row_of_memory* current = *head;
	row_of_memory* next = NULL;
	while (current != NULL) {      // free mallocs one by one and advance the current pointer
		next = current->next;
		if (current->label != NULL) {     // we cannot free a NULL pointer, have to check they are not NULL
			free(current->label);
		}
		if (current->assembly != NULL) {
			free(current->assembly);
		}
		free(current);
		current = next;
	}
	/* if no errors, set head = NULL upon deletion */
	*head = NULL;
	/* return 0 if no error, -1 for any errors that may arise */
	return 0 ;
}

