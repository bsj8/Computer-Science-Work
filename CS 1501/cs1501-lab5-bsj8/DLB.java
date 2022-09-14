/*************************************************************************
 *  Compilation:  javac DLB.java
 *  Execution:    java DLB < TinyInput.txt
 *  Execution:    java DLB < LargeInput.txt
 *  Dependencies: StdIn.java, StdOut.java, Queue.java
 *
 *  A symbol table for String keys implemented using
 *  a De La Briandais (DLB) Trie.
 *
 *************************************************************************/

public class DLB<Value> {
    private static final char SENTINEL = '^';

    private Node root;

    // test client
    public static void main(String[] args) {
        // build symbol table from standard input
        DLB<Integer> st = new DLB<>();
        StdOut.println("The (key: value) pairs:");
        StdOut.println("======================");

        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
            //(Comment the next line out for LargeInput.txt)
            StdOut.println("("+key + ": " + i + ")");
        }

        // print results (Comment this part out for LargeInput.txt)
        StdOut.println("\nLevel order traversal of the trie (for debugging):");
        StdOut.println("==================================================");
        for (String i : st.levelOrder()){
            StdOut.print(i);
        }
        StdOut.println();


        StdOut.println("\nEnumerating all keys in the trie:");
        StdOut.println("=================================");
        for (String key : st.keys()) {
            StdOut.println("("+key + ": " + st.get(key)+")");
        }
    }

   /****************************************************
    * Is the key in the symbol table?
    ****************************************************/
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return true if the key is in the symbol table
     *         and false if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        key = key + SENTINEL;
        Node result = get(root, key, 0);
        if(result != null)
          return result.val;
        else
          return null;
    }

    private Node get(Node x, String key, int pos) {
        Node result = null;
        if(x != null){
          if(x.letter == key.charAt(pos)){
            if(pos == key.length()-1){
              result = x;
            } else {
              result = get(x.child,key,pos+1); /*TODO: Recurse on the child node*/
            }
          } else {
            /*TODO: Recurse on the next sibling node*/
            result = get(x.sibling,key,pos);
          }
        }
        return result;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        key = key + SENTINEL;
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int pos) {
        Node result = x;
        if (x == null){
            result = new Node();
            result.letter = key.charAt(pos);
            result.level = pos;
            if(pos < key.length()-1){
              result.child = put(result.child,key,val,pos+1);
            } else {
              result.val = val;
            }
        } else if(x.letter == key.charAt(pos)) {
            if(pos < key.length()-1){
              /*TODO: Recurse on the child node*/;
              result.child = put(result.child,key,val,pos+1);
            } else {
              result.val = val; //update
            }
        } else {
          /*TODO: Recurse on the sibling node*/;
          result.sibling=put(result.sibling,key,val,pos);
        }
        return result;
    }

    /**
     * Returns the keys in the DLB in pre-order
     *
     * @return the keys in the DLB in pre-order traversal
     */
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        collect(root, queue, new StringBuilder());
        return queue;
    }

    private void collect(Node x, Queue<String> queue,
                         StringBuilder current) {
        if (x == null) return;
        Node curr = x;
        while(curr != null){
          current.append(curr.letter);
          if(curr.letter == SENTINEL){
            queue.enqueue(current.substring(0, current.length()-1));
          }
          collect(curr.child, queue, current);
          current.deleteCharAt(current.length()-1);
          curr = curr.sibling;
        }
    }

    /**
     * Returns the keys in the DLB in level order
     *
     * @return the keys in the DLB in level order traversal
     */
    public Iterable<String> levelOrder() {
        Queue<String> letters = new Queue<>();
        Queue<Node> queue = new Queue<Node>();
        int level = 0;
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null){
              continue;
            }
            if(x.level != level){
              letters.enqueue("\n");
              level = x.level;
            }
            Node curr = x;
            letters.enqueue("(");
            while(curr != null){
              if(curr.sibling == null){
                letters.enqueue(curr.letter + ")");
              } else {
                  letters.enqueue(curr.letter + "->");
              }

              queue.enqueue(curr.child);

              curr = curr.sibling;
            }
            letters.enqueue("\t");
        }
        return letters;
    }

    private class Node {
        private Value val;
        private char letter;
        private Node sibling;
        private Node child;
        private int level; //for level-order traversal
    }
}
