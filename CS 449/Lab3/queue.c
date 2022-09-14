/*
 * Developed by R. E. Bryant, 2017
 * Extended to store strings, 2018
 */

/*
 * This program implements a queue supporting both FIFO and LIFO
 * operations.
 *
 * It uses a singly-linked list to represent the set of queue elements
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "harness.h"
#include "queue.h"

/*
  Create empty queue.
  Return NULL if could not allocate space.
*/
queue_t *q_new()
{
  /*
  * If the queue is null return null otherwise initilize the head,tail and size data of the queue
  */
    queue_t *q =  malloc(sizeof(queue_t));
    /* What if malloc returned NULL? */
    if(q==NULL){
      return NULL;
    }else{
      q->head = NULL;
      q->tail=NULL;
      q->size=0;
      return q;
    }
}

/* Free all storage used by queue */
void q_free(queue_t *q)
{
  /*
  * I created a curr, temp and next linked list element so that I can iterate through the queue
  * curr is set to the head and then temp is later set to curr
  * then I set next to the next of curr and then I set curr to next
  * after which I free temp value first and then temp
  */
    /* How about freeing the list elements and the strings? */

    /* Free queue structure */
    if(q==NULL){

    }else{
      list_ele_t *curr=q->head;
      list_ele_t *temp;
      list_ele_t *next;
      while(curr!=NULL){
        temp=curr;
        next=curr->next;
        curr=next;
        free(temp->value);
        free(temp);
      }
      free(q);
    }
}
int stringLength(char * s) {
  int n=0;
  int count=0;
  while((*(s+n)) != 0){
    count++;
    n++;
  }
  return count;
}

/*
  Attempt to insert element at head of queue.
  Return true if successful.
  Return false if q is NULL or could not allocate space.
  Argument s points to the string to be stored.
  The function must explicitly allocate space and copy the string into it.
 */
bool q_insert_head(queue_t *q, char *s)
{
  /*
  * created space for a new linked list element and then created space for the value of the linked list element
  *copied the string into the space created for the value and then set the next value of the new linked list to the q-> q_insert_head
  *then set Q-> head to the new linked list element
  *also if it is the first inset I also set tail to the new linked list element
  */
    list_ele_t *newh;
    /* What should you do if the q is NULL? */
    if(q==NULL){
      return false;
    }
    newh = malloc(sizeof(list_ele_t));
    if(newh==NULL){
      return false;
    }
    /* Don't forget to allocate space for the string and copy it */
    newh->value=(char*) malloc(stringLength(s)+1);
    /* What if either call to malloc returns NULL? */
    if(newh->value==NULL){
      free(newh);
      return false;
    }
    strcpy(newh->value,s);
    q->size+=1;
    newh->next = q->head;
    q->head = newh;
    if(q->size==1){
      q->tail=newh;
    }
    return true;
}


/*
  Attempt to insert element at tail of queue.
  Return true if successful.
  Return false if q is NULL or could not allocate space.
  Argument s points to the string to be stored.
  The function must explicitly allocate space and copy the string into it.
 */
bool q_insert_tail(queue_t *q, char *s)
{
  /*
  * Similar process to insert head except I set the next of the tail to the new linked list elements
  * then I set the tail to this linKed list element
  */
    /* You need to write the complete code for this function */
    /* Remember: It should operate in O(1) time */
    list_ele_t *newt;

    if(q==NULL){
      return false;
    }
    newt = malloc(sizeof(list_ele_t));
    if(newt==NULL){
      return false;
    }
    newt->value=(char*) malloc(stringLength(s)+1);
    if(newt->value==NULL){
      free(newt);
      return false;
    }

    strcpy(newt->value,s);
    if(q->size==0){
      q->size++;
      q->tail=newt;
      q->head=newt;
      q->tail->next=NULL;
    }else{
      q->size++;
      q->tail->next=newt;
      q->tail=newt;
      q->tail->next=NULL;
    }
    return true;
}

/*
  Attempt to remove element from head of queue.
  Return true if successful.
  Return false if queue is NULL or empty.
  If sp is non-NULL and an element is removed, copy the removed string to *sp
  (up to a maximum of bufsize-1 characters, plus a null terminator.)
  The space used by the list element and the string should be freed.
*/
bool q_remove_head(queue_t *q, char *sp, size_t bufsize)
{
  /*
  *so I first copy the value to sp
  * then I free the value inside the node
  * then I move the head to the next node
  * then I free them old head
  */
    /* You need to fix up this code. */
    if(q==NULL || q->head==NULL){
      return false;
    }
    list_ele_t *temp=q->head;
    if(sp!=NULL && temp->value!=NULL){
      strncpy(sp,temp->value,bufsize-1);
      *(sp+(bufsize-1))='\0';
    }
    if(temp->value!=NULL){
      free(temp->value);
    }
    q->head = q->head->next;
    free(temp);
    q->size--;
    return true;
}

/*
  Return number of elements in queue.
  Return 0 if q is NULL or empty
 */
int q_size(queue_t *q)
{
    /* You need to write the code for this function */
    /* Remember: It should operate in O(1) time */
    if(q==NULL){
      return 0;
    }
        return q->size;

}

/*
  Reverse elements in queue
  No effect if q is NULL or empty
  This function should not allocate or free any list elements
  (e.g., by calling q_insert_head, q_insert_tail, or q_remove_head).
  It should rearrange the existing ones.
 */
void q_reverse(queue_t *q)
{
  /*
  * so I have linked list elements and set them to a previous value and then curr is the q-> head and then next is value after q-> q_insert_head
  *I have while loop that sets curr to the previous node and then moves curr and next and repeats untill curr is NULL
  * then I set the head to previous and the tail to the old head
  */
    /* You need to write the code for this function */
    if(q==NULL || q->head==NULL){

    }else{
      list_ele_t *prev=NULL;
      list_ele_t *curr=q->head;
      list_ele_t *next=curr->next;
      while(curr!=NULL){
        curr->next=prev;
        prev=curr;
        curr=next;
        if(curr!=NULL){
        next=curr->next;
       }
      }
      q->tail=q->head;
      q->head=prev;
    }
}
