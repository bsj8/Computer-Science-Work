public class Main {
  public static void main(String[] args){
    new Main();
  }

  public Main(){
    testBST();
  }

  public void testBST(){
    Character[] data = new Character[14];
    data[0] = 'G';
    data[1] = 'D';
    data[2] = 'B';
    data[3] = 'E';
    data[4] = 'A';
    data[5] = 'C';
    data[6] = 'F';
    data[7] = 'K';
    data[8] = 'N';
    data[9] = 'I';
    data[10] = 'L';
    data[11] = 'H';
    data[12] = 'J';
    data[13] = 'M';

    BinarySearchTree<Character> bst = new BinarySearchTree<>();
    for(Character c : data){
      bst.add(c);
    }

    for(Character c : data){
      testPredecessor(bst, c);
    }
    System.out.println("===========");
    for(Character c : data){
      testSuccessor(bst, c);
    }
  }

  private <T extends Comparable<? super T>>
     void testPredecessor(BinarySearchTree<T> bst, T input){
       System.out.println("Predecssor of " + input + " is " + bst.predecessor(input));
  }

  private <T extends Comparable<? super T>>
     void testSuccessor(BinarySearchTree<T> bst, T input){
       System.out.println("Successor of " + input + " is " + bst.successor(input));
  }
}
