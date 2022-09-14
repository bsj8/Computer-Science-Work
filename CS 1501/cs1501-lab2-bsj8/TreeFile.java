/** A program to read and write binary trees from/to files.
* @author Sherif Khattab (Code adapted from Dr. Ramirez's CS 0445 Assignment 5)
*
* There are three parts to be implemented by students
*/

import java.io.*;
import java.util.*;

public class TreeFile {
   private BinaryNode<Character> bTree = null;
   Scanner inScan;
   Scanner fReader;
   File fName;
   String userInput = "";

   public TreeFile() throws IOException{
     inScan = new Scanner(System.in);
     // display menu and read user selection
     while(true){
       switch(menu()){
         case 1:
           Scanner fileScan = new Scanner(new FileInputStream(readFileName()));
           bTree = readTree(fileScan);
           break;
         case 2:
            inorderTraverse(bTree);
            break;
         case 3:
           System.out.println("Enter new root's character data:");
           userInput = inScan.nextLine();
           bTree = new BinaryNode<>(userInput.charAt(0), bTree, null);
           break;
         case 4:
             System.out.println("Enter new root's character data:");
             userInput = inScan.nextLine();
             //TODO: create a new node that has the existing tree as
             //its right child and assign it to the bTree variable
             bTree= new BinaryNode<>(userInput.charAt(0),null,bTree);
             break;
         case 5:
            FileWriter fileWriter = new FileWriter(readFileName());
            writeTree(fileWriter, bTree);
            fileWriter.close();
            break;
         case 6:
           inScan.close();
           System.exit(0);
           break;
         default:
           System.out.println("Incorrect option.");
       }
     }



   }

   /** A method to display the program menu and read user selection */

   private int menu(){
     System.out.println("*********************************");
     System.out.println("Welcome to CS 1501 Persistent Tree Program!");
     System.out.println("1. Read tree from a file");
     System.out.println("2. Display inorder traversal of the tree");
     System.out.println("3. Attach tree as left child to a new root");
     System.out.println("4. Attach tree as right child to a new root");
     System.out.println("5. Write tree to a file");
     System.out.println("6. Exit.");
     System.out.println("*********************************");
     System.out.print("Please choose a menu option (1-6): ");

     int choice = Integer.parseInt(inScan.nextLine());
     return choice;
   }

   private String readFileName(){
     String fName = null;
     System.out.println("Please enter tree filename:");
     fName = inScan.nextLine();
     return fName;
   }

   /** A recursive method to read a binary tree from a file
   */

   private BinaryNode<Character> readTree(Scanner file) throws IOException{
     BinaryNode<Character> result = null;
     if(file.hasNext()){
       String line = file.nextLine();
       if(line.charAt(0) == 'I'){ //internal node
         /*TODO: replace null below with a new node and recursively call
          readTree on its left and right children */
         result = new BinaryNode<>(line.charAt(2),readTree(file),readTree(file));
       } else if(line.charAt(0) == 'L'){ //leaf node
         result = new BinaryNode<>(line.charAt(2));
       } else { //NULL child
         result = null;
       }
     }
     return result;
   }

   private void writeTree(FileWriter file, BinaryNode<Character> root) throws IOException{
     if(root != null){
       if(root.left == null && root.right == null){ //leaf: write L
         //TODO: write "L root.data" to the file and return
         file.write("L " + root.data + "\n");
         return;
       } else { //internal: write I
         file.write("I " + root.data + "\n");
       }
       writeTree(file, root.left);
       writeTree(file, root.right);
     } else {
       file.write("N " + "\n"); //null child: write N
     }
   }

   /** inorder traversal of a binary tree   */
   private void inorderTraverse(BinaryNode<Character> root){
     if(root != null){
       inorderTraverse(root.left);
       System.out.println(root.data);
       inorderTraverse(root.right);
     }
   }

   public static void main(String[] args) throws IOException{
     new TreeFile();
   }

   private class BinaryNode<Character>{
     private Character data;
     private BinaryNode<Character> left;
     private BinaryNode<Character> right;

     private BinaryNode(Character c){
       this(c, null, null);
     }

     private BinaryNode(Character c, BinaryNode<Character> left,
                        BinaryNode<Character> right){
        this.data = c;
        this.left = left;
        this.right = right;
      }
   }


}
