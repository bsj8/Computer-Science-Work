//TO-DO Add necessary imports
import java.util.*;
import java.io.FileInputStream;
public class AutoComplete{

  //TO-DO: Add instance variable: you should have at least the tree root
  private DLBNode root;

  public AutoComplete(String dictFile) throws java.io.IOException {
    //TO-DO Initialize the instance variables
    root=null;
    Scanner fileScan = new Scanner(new FileInputStream(dictFile));
    while(fileScan.hasNextLine()){
      StringBuilder word = new StringBuilder(fileScan.nextLine());
      //TO-DO call the public add method or the private helper method if you have one
      add(word);
    }
    fileScan.close();
  }

  /**
   * Part 1: add, increment score, and get score
   */

  //add word to the tree
  public void add(StringBuilder word){
    //TO-DO Implement this method
    if(word==null || word.length()==0){
      return;
    }else{
      root=add(word,root,0);
    }
  }
  /**
  * @param word is first parameter of add method
  * @param x is second parameter of add method
  * @param pos is third parameter of add method
  * @return DLBNode this returns the newly added DLBNode
  * This private add method takes a StringBuilder and adds it to the Tree. The pos variable is used to keep track of
  * position in the StringBuilder. The method checks to see if there are any prefixes of the StringBuilder and if there are
  * then the characters excluding the prefix are added to the tree as soon as a null node is found.
  */
  private DLBNode add(StringBuilder word,DLBNode x,int pos){
    DLBNode result = x;
    if (x == null){
        result = new DLBNode(word.charAt(pos),0);
        if(pos < word.length()-1){
          result.child = add(word,result.child,pos+1);
        } else {
          result.isWord = true;
        }
    } else if(x.data == word.charAt(pos)) {
        if(pos < word.length()-1){
          result.child = add(word,result.child,pos+1);
        } else {
          result.isWord = true;
        }
    } else {
      result.sibling=add(word,result.sibling,pos);
    }
    return result;

  }

  //increment the score of word
  /*
  * @param word is the first parameter of the method
  * This method simply increases the score of a word by calling the getNode method. The getNode method returns the
  * non null node of a string(that is isWord of node is true). After getting that node simply increment the score
  * of that node
  */
  public void notifyWordSelected(StringBuilder word){
    //TO-DO Implement this method
    DLBNode result=getNode(root,word.substring(0),0);

      if(result!=null) result.score++;
  }

  //get the score of word
  /*
  * @param word is the first parameter of the method
  * @return int the method returns the score which is an int
  * the getScore method returns the score by simply calling the getNode method which returns the node in which isWord=true.
  * that node also contains the score which you return.
  */
  public int getScore(StringBuilder word){
    //TO-DO Implement this method
    DLBNode result=getNode(root,word.substring(0),0);
    if(result!=null) return result.score;
    return -1;


  }

  /**
   * Part 2: retrieve word suggestions in sorted order.
   */

  //retrieve a sorted list of autocomplete words for word. The list should be sorted in descending order based on score.
  /*
  * @param word is first parameter of method
  * @return ArrayList<Suggestion>  it will return an ArrayList of Suggestions in descending order
  * retrieveWords method creates an ArrayList of type Suggestion and also creates a node called start. This node will be
  * the node containing the last character of StringBuilder word. If word is an actual word then it will be added to the
  * ArrayList. The method creates a StringBuilder called begin and appends word to it. This is because
  * when we call the collect method we can simply begin appending letters after the word. For example if word="abs" then
  * we begin appending after the s forming suggestions like "absolute","absolutly","absolve" etc.
  *The method calls the private method collect passing start.child, the ArrayList collect, and StringBuilder begin
  * as arguments. start.child is passed because we want to begin appending after the last letter of the given word.
  */
  public ArrayList<Suggestion> retrieveWords(StringBuilder word){
    //TO-DO Implement this method
    ArrayList<Suggestion> collect= new ArrayList<Suggestion>();
    DLBNode start=getNode(root,word.substring(0),0);
    StringBuilder begin=new StringBuilder();
      begin.append(word);
      if(start!=null && start.isWord){
        Suggestion temp=new Suggestion(start.score,word);
        collect.add(temp);
      }
      if(start!=null){
    collect(start.child, collect, begin);
    Collections.sort(collect);
    }
    return collect;
  }
  /*
  * @param x is the first parameter
  * @param collect is the second parameter
  * @param current is the third parameter
  * This private method goes through the trie and appends letters to StringBuilder current. If it reaches a node and isWord is true
  *  then it will add the word to ArrayList collect. After going through every node it will return.
  */
  private void collect(DLBNode x, ArrayList<Suggestion> collect, StringBuilder current){
    if (x == null) return;
    DLBNode curr = x;
    while(curr != null){
      current.append(curr.data);
      if(curr.isWord){
        StringBuilder copy=new StringBuilder(current.substring(0));
        Suggestion temp=new Suggestion(curr.score,copy);
        collect.add(temp);

      }
      collect(curr.child, collect, current);
      current.deleteCharAt(current.length()-1);
      curr = curr.sibling;
    }
    return;
 }
 private void shift(ArrayList<Suggestion> collect, int end){
   for(int i=collect.size()-1;i>end;i--){
     collect.set(i,collect.get(i-1));
   }
 }

  /**
   * Helper methods for debugging.
   */

  //Print the subtree after the start string
  public void printTree(String start){
    System.out.println("==================== START: DLB Tree Starting from "+ start + " ====================");
    DLBNode startNode = getNode(root, start, 0);
    if(startNode != null){
      printTree(startNode.child, 0);
    }
    System.out.println("==================== END: DLB Tree Starting from "+ start + " ====================");
  }

  //A helper method for printing the tree
  private void printTree(DLBNode node, int depth){
    if(node != null){
      for(int i=0; i<depth; i++){
        System.out.print(" ");
      }
      System.out.print(node.data);
      if(node.isWord){
        System.out.print(" *");
      }
        System.out.println(" (" + node.score + ")");
      printTree(node.child, depth+1);
      printTree(node.sibling, depth);
    }
  }

  //return a pointer to the node at the end of the start string. Called from printTree.
  private DLBNode getNode(DLBNode node, String start, int index){
    DLBNode result = node;
    if(node != null){
      if((index < start.length()-1) && (node.data.equals(start.charAt(index)))) {
          result = getNode(node.child, start, index+1);
      } else if((index == start.length()-1) && (node.data.equals(start.charAt(index)))) {
          result = node;
      } else {
          result = getNode(node.sibling, start, index);
      }
    }
    return result;
  }


  //A helper class to hold suggestions. Each suggestion is a (word, score) pair.
  //This class should be Comparable to itself.
  /*
  * This Suggetion class implements comparable because we have to present the suggestions in descending order which means
  * we have to compare Suggestons. It has variables score and word because we need the word to present to the user and we
  * need the score to compare with other Suggestions. The compareTo method works by comparing scores however if the
  * scores are equal then the method checks alphabetically.
  */
  public class Suggestion implements Comparable<Suggestion> {
    //TO-DO Fill in the fields and methods for this class. Make sure to have them public as they will be accessed from the test program A2Test.java.
    public int score;
    public StringBuilder word;
    public Suggestion(int x, StringBuilder y){
      score=x;
      word=y;
    }
    public int compareTo(Suggestion other){
      if(this.score>other.score){
        return -1;
      }else if(this.score<other.score){
        return 1;
      }else{
        int compare= this.word.substring(0).compareTo(other.word.substring(0));
        if(compare>0){
          return 1;
        }else{
          return -1;
        }
      }
    }
  }

  //The node class.
  private class DLBNode{
    private Character data;
    private int score;
    private boolean isWord;
    private DLBNode sibling;
    private DLBNode child;

    private DLBNode(Character data, int score){
        this.data = data;
        this.score = score;
        isWord = false;
        sibling = child = null;
    }
  }
}
