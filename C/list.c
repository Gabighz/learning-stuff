/*
    The purpose of this program is to store 100 integers
    into a singly-linked list and then sort it.
*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

typedef struct node
{
    int data;
    struct node *next;
} node;


node* create_node(int d);
void append(int d, node *h);
void print_list(node *h);
void bubble_sort(node *h);

int main()
{
    // initialize list
    node* head = NULL;
    node* temp = NULL;

    head = create_node(rand() % 20);
    temp = create_node(rand() % 20);
    head -> next = temp;

    int count = 0;

    // 98 because we already have two integers generated
    while (count < 98)
    {
        int n = rand() % 20;
        append(n, temp);
        temp = temp -> next;
        count++;
    }

    print_list(head);
    printf("\n\n");
    bubble_sort(head);
    print_list(head);
}

node* create_node(int d)
{
    node *head = malloc(sizeof(node));
    head -> data = d;
    head -> next = NULL;
    return head;
}

void append(int d, node *n)
{
    node *new_node = create_node(d);
    n -> next = new_node;
}

void print_list(node *h)
{
    while (h != NULL)
    {
        printf("%d ", h-> data);
        h = h -> next;
    }
}

void bubble_sort(node *h)
{
    node *current;
    node *sorted_part = NULL;

    int swapped = 1;

    while (swapped != 0)
    {
        swapped = 0;
        current = h;

        while (current -> next != sorted_part)
        {
            if (current -> data > current -> next -> data)
            {
                int temp = current -> data;
                current -> data = current -> next -> data;
                current -> next -> data = temp;
                swapped = 1;
            }
            current = current -> next;
        }
        sorted_part = current;
    }
}
