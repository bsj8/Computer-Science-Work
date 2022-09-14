/*************************************************************************
 *  Compilation:  javac DST.java
 *  Execution:    java DST < TinyInput.txt
 *  Execution:    java DST < 1Kints.txt
 *  Dependencies: StdIn.java, StdOut.java, Queue.java
 *
 *  A symbol table for Integer keys implemented using a Digital Search Tree.
 *
 *************************************************************************/

public class DST<Value> {

    private static int MAX_BITS;
    private Node root;

    // test client
    public static void main(String[] args) {
        // build symbol table from standard input
        DST<Integer> st = new DST<>();
        //the first integer in the input is the number of bits in the key
        MAX_BITS = StdIn.readInt();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            Integer key = StdIn.readInt();
            st.put(key, i);
        }

        // print results
        StdOut.println("Level order:");
        for (Integer i : st.levelOrder())
            StdOut.println(st.get(i) + ": " + i);

        StdOut.println("Pre-order:");

        for (Integer key : st.keys()) {
            StdOut.println(key + " " + st.get(key));
        }
    }

   /****************************************************
    * Is the key in the symbol table?
    ****************************************************/
    public boolean contains(Integer key) {
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Integer key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        Node x = get(root, key, MAX_BITS-1);
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, Integer key, int pos) {
        Node result = null;
        if ((x != null) && (pos >= 0)){
          if(x.key.equals(key)){
            result = x;
          } else if(bitAt(key, pos) == false){ //move left
            result =get(x.left,key,pos-1);  /* TODO: make a recursive call on the left child and the next bit */
          } else { //move right
            result =get(x.right,key,pos-1); /* TODO: make a recursive call on the right child and the next bit */
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
    public void put(Integer key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, val, MAX_BITS-1);
    }

    private Node put(Node x, Integer key, Value val, int pos) {
        Node result = x;
        if (x == null){
            result = new Node();
            result.key = key;
            result.val = val;
        } else if(pos >= 0) {
          if(x.key.equals(key)){ //key is found, update value
            /* TODO: Change the value of the node x */
              x.val=val;
          } else if(bitAt(key, pos) == false){ //move left
            x.left =put(x.left,key,val,pos-1); /* TODO: make a recursive call on the left child and the next bit */
          } else { //move right
            x.right = put(x.right,key,val,pos-1); /* TODO: make a recursive call on the right child and the next bit */
          }
        }
        return result;
    }

    /**
     * Returns the keys in the DST in pre-order
     *
     * @return the keys in the DST in pre-order traversal
     */
    public Iterable<Integer> keys() {
        Queue<Integer> queue = new Queue<>();
        collect(root, queue);
        return queue;
    }

    private void collect(Node x, Queue<Integer> queue) {
        if (x == null) return;
        if (x.val != null) queue.enqueue(x.key);
        collect(x.left, queue);
        collect(x.right, queue);
    }

    /**
     * Returns the keys in the DST in level order
     *
     * @return the keys in the DST in level order traversal
     */
    public Iterable<Integer> levelOrder() {
        Queue<Integer> keys = new Queue<Integer>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    /****************************************************
     * Returns the bit at position pos in the binary
     * representation of key
     ****************************************************/
    private static boolean bitAt(Integer key, int pos){
      return (key & (1 << pos)) > 0;
    }

    private class Node {
        private Integer key;
        private Value val;
        private Node left;
        private Node right;
    }
}
