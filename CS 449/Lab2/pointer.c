/*
 * pointer.c - Source file with your solutions to the Lab.
 *             This is the file you will hand in to your instructor.
 *
 * WARNING: Do not include the <stdio.h> header; it confuses the dlc
 * compiler. You can still use printf for debugging without including
 * <stdio.h>. The included file, "common.c" contains a function declaration
 * that should prevent a compiler warning. In general, it's not good practice
 * to ignore compiler warnings, but in this case it's OK.
 */

#ifndef COMMON_H
#include "common.h"
#endif

/*
 * Instructions to Students:
 *
 * STEP 1: Read the following instructions carefully.
 */

#if 0
You will provide your solution to this homework by
editing the collection of functions in this source file.

INTEGER CODING RULES:

  Replace the "return" statement in each function with one
  or more lines of C code that implements the function. Your code
  must conform to the following style:

  int Funct(arg1, arg2, ...) {
      /* brief description of how your implementation works */
      int var1 = Expr1;
      ...
      int varM = ExprM;

      varJ = ExprJ;
      ...
      varN = ExprN;
      return ExprR;
  }

  Each "Expr" is an expression using ONLY the following:
  1. Integer constants 0 through 255 (0xFF), inclusive. You are
     not allowed to use big constants such as 0xffffffff.
     However, you are allowed to combine constants to values greater
     than 255 or less than 0. e.g. 250 + 250 = 500, so long as
     the operator you are using to combine the constants is listed as
     "legal" at the top of the method you are writing.
  2. Function arguments and local variables (no global variables).
  3. Any operators listed as "allowed" at the top of the method you are writing.
  4. casting.

  Some of the problems restrict the set of allowed operators even further.
  Each "Expr" may consist of multiple operators. You are not restricted to
  one operator per line.

  You are expressly forbidden to:
  1. Use any control constructs such as if, do, while, for, switch, etc. (unless
     otherwise stated).
  2. Define or use any macros.
  3. Define any additional functions in this file.
  4. Call any functions (unless otherwise stated).
  5. Use any operators not "allowed" at the top of the method you are writing.

  You may assume that your machine:
  1. Uses 2s complement, 32-bit representations of integers.
  2. Performs right shifts arithmetically.
  3. Has *undefined* behavior when shifting by a negative amount or an amount
     greater than or equal to the number of bits in the value being shifted.
     e.g. For x >> n, shifts by n < 0 or n >= *size of x* are undefined
     e.g. if x is a 32-bit int, shifts by >= 32 bits are undefined
     Undefined means you do not know what result you will get from the operation.
#endif

/*
 * STEP 2: Modify the following functions according the coding rules.
 */

/*
 * Return the size of an integer in bytes.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: ex. +=, *=, ++, --, eFDIStc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
int intSize() {
  /*
  * set prtr1 to address of arr[0] and ptr2 to address of arr[1]
  * cast both to long and then subtract and return the value
  */
  int intArray[10];
  int * intPtr1;
  int * intPtr2;
  // Write code to compute size of an integer.
  int temp;
  intPtr1=&(*(intArray));
  intPtr2=&(*(intArray+1));
   temp= ((long)intPtr2) - ((long)intPtr1);
  return temp;
}

/*
 * Return the size of a double in bytes.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: ex. +=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
int doubleSize() {
  /*
  * same process as intSize() method above
  */
  double doubArray[10];
  double * doubPtr1;
  double * doubPtr2;
  // Write code to compute size of a double.
  int temp;
  doubPtr1=&(*doubArray);
  doubPtr2=&(*(doubArray+1));
   temp= ((long)doubPtr2) - ((long)doubPtr1);

  return temp;
}

/*
 * Return the size of a pointer in bytes.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: ex. +=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
int pointerSize() {
  /*
  * same process as intSize() method above
  */
  int * ptrArray[10];
  int ** ptrPtr1;
  int ** ptrPtr2;
  // Write code to compute size of a pointer.
  int temp;
  ptrPtr1=&(*(ptrArray));
  ptrPtr2=&(*(ptrArray+1));
   temp= ((long)ptrPtr2) - ((long)ptrPtr1);

  return temp;
}

/*
 * Given pointers to two distinct variables write a function to swap the values
 * of said variables.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: ex. +=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
void swapInts(int * ptr1, int * ptr2) {
  /*
  * dereferenced ptr1 and ptr2 and assiged the values to ints and then I set ptr1 and ptr2 to address of the opposite int
  */
  // Your code here

  int  x= *ptr1;
  int  y=*ptr2;
  *ptr1=*(&y);
  *ptr2=*(&x);

}

/*
 * Modify intArray[5] to be the value 449 using only intArray and pointer arithmetic.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, <<, >>, ==, ^
 *   Unary integer operators: !, ~
 *   Shorthand operators based on the above: ex. +=, *=, ++, --, ~= etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, !=, /, %
 *   Unary integer operators: -
 */
int changeValue() {
  /*
  * I just added 5 to intArray and then set the value to x;
  */
  int intArray[10];
  int * intPtr1 = intArray;
  // Remember not to use constants greater than 255.
  // Remember to use * to dereference. You cannot use '[<index>]' syntax.

  // replace the line below to use pointer arithmetic
  int x;
  x=250+199;
  *(intArray+5)=x;
  return intArray[5];
}

/*
 * Return 1 if the addresses stored in ptr1 and ptr2 are within the
 * *same* 64-byte aligned  block of memory. Check the spec for examples if you are
 * confused about what this means. Return zero otherwise.
 * Operators / and % and loops are NOT allowed.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, <<, >>, ==, ^
 *   Unary integer operators: !, ~
 *   Shorthand operators based on the above: ex. <<=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, !=, /, %
 *   Unary integer operators: -
 */
int withinSameBlock(int * ptr1, int * ptr2) {
  /*
  * I casted both ptr1 and ptr2 to longs and then I shifted by 6
  * then I compared addresses
  */
  // Your code here
int x= (long)ptr1;
int y= (long)ptr2;
  return (x>>6)==(y>>6);
}

/*
 * Return 1 if ptr points to an element within the specified intArray, 0 otherwise.
 * Pointing anywhere in the array is fair game, ptr does not have to
 * point to the beginning of an element. Check the spec for examples if you are
 * confused about what this method is determining.
 * size is the size of intArray in number of ints. Can assume size != 0.
 * Operators / and % and loops are NOT allowed.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, <<, >>, ==, ^
 *   Unary integer operators: !, ~
 *   Shorthand operators based on the above: ex. <<=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, !=, /, %
 *   Unary integer operators: -
 */
int withinArray(int * intArray, int size, int * ptr) {
  /*
  * here I did the samething but I just shifted with size of array
  */
  // Your code here
  int  x=(long) intArray;
  int y= (long) ptr;

  return (x>>size)==(y>>size);
}

/*
 * In C characters are are terminated by the null character ('\0')
 * given a pointer to the start of the string return the length of this string.
 * (The null character is not counted as part of the string length.)
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, ==, !=, <, >
 *   Unary integer operators: !
 *   Shorthand operators based on the above: ex. <<=, *=, ++, --, etc.
 *   Control constructs: for, while
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <<, >>, ^, /, %
 *   Unary integer operators: ~, -
 */
int stringLength(char * s) {
  /*
  * I created a count variable and then used a while loop to increment count until s=0
  * I just added s + n to count the characters
  */
  // Your code here
  int n=0;
  int count=0;
  while((*(s+n)) != 0){
    count++;
    n++;
  }
  return count;
}

/*
 * Returns the length of the initial portion of str1 which consists only of characters that are part of str2.
 * The search does not include the terminating null-characters of either strings, but ends there.
 *
 * Example, stringSpan("abcdefgh", "abXXcdeZZh"); // returns 5
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, ==, !=, <, >
 *   Unary integer operators: !
 *   Shorthand operators based on the above: ex. <<=, *=, ++, --, etc.
 *   Control constructs: for, while
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <<, >>, ^, /, %
 *   Unary integer operators: ~, -
 */
int stringSpan(char * str1, char * str2) {
  /*
  * I used a while loop to move through str2 and only moving through str1 if I found a match, I also incremented count if there was a match
  * if str2=0 then I exited the loop
  */
  // Your code here
  int n=0;
  int k=0;
  int count=0;
  while((*(str1+n)) != 0){
    if((*(str1+n)) == (*(str2+k))){
       count++;
       n++;
    }else{
      if((*(str2+k)) !=0){
        k++;
      }else{
        return count;
      }
    }

}
  return count;

}

/*
 * Change the value pointed to by ptr byte-by-byte so that when returned as an integer
 * the value is 449449.
 *
 * Hint: Remember that an int is 4 bytes.
 *
 * Hint: Remember how little endian works for data storage, how is it different between an multiple bytes(int) and a single byte?
 *
 * Hint: It will be easiest to start convert 449449 into binary form and starting seeing how the endian works from there.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Shorthand operators based on the above: ex. +=, *=, ++, --, etc.
 *   Unary integer operators: !
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
int endianExperiment(int * ptr) {
  /*
  * I cast ptr to a character pointer and then set it to bytePtr
  * Because I casted to a character pointer I can now increment it by one byte which would not have been possible
  * with an int pointer
  * then I simply set the  addresses I need to hexadecimal values of 449449
  */
  char *bytePtr;
  char * ptr1;
  char*ptr2;
  char*ptr3;
  char*tmpByte;
  bytePtr=(char *) ptr;
  tmpByte=bytePtr+1;
  //printf("ptr = %p\n",ptr);
  ptr1= tmpByte;
  //printf("ptr1 = %p\n",ptr1);
  ptr2=(tmpByte +1);
  //printf("ptr2 = %p\n",ptr2);
  ptr3=(tmpByte+2);
  //printf("ptr3= %p\n",ptr3);
  *bytePtr=0xA9;
  *ptr1=0xDB;
  *ptr2=0x06;
  *ptr3=0x00;

  // Your code here
  return *ptr;
}

/*
 * Selection sort is a sorting algorithim that works by partitioning the array into
 * a sorted section and unsorted section. Then it repeatedly selects the minimum element
 * from the unsorted section and moves it to the end of the sorted section.
 *
 * So the pseudo-code might look something like this:
 * arr - an array
 * n - the length of arr
 *
 * for i = 0 to n - 1
 *   minIndex = i
 *   for  j = i + 1 to n
 *       if arr[minIndex] > arr[j]
 *           minIndex = j
 *       end if
 *   end for
 *   Swap(arr[i], arr[minIndex])
 * end for
 *
 * Implement selection sort below, it might be helpful to use the swapInts function you
 * defined earlier.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, ==, !=, <, >
 *   Unary integer operators: !
 *   Shorthand operators based on the above: ex. +=, *=, ++, --, etc.
 *   Control constructs: for, while, if
 *   Function calls: swapInt()
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <<, >>, ^, /
 *   Unary integer operators: ~, -
 */
void selectionSort(int arr[], int arrLength) {
  /*
  * I just used a nested for loop for this
  * find the Ith smallest elment and then swap into that position
  */
  int i, j, min_index;
  // Your code here
  for( i=0; i<arrLength; i++){
    min_index=i;
    for( j=i+1; j<arrLength; j++){
      if((*(arr+min_index))>(*(arr+j))){
        min_index=j;
      }
  }
  swapInts((arr+i),(arr+min_index));
 }
}
