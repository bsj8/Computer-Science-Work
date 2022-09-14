/** A PHPArray is a hybrid of a hash table and a linked list.
* It allows hash table access, and FIFO sequential access.
* @author Sherif Khattab
**/

import java.util.Iterator;

public class PHPArray<V> implements Iterable<V> {
  private static final int INIT_CAPACITY = 4;

  private int N;           // number of key-value pairs in the symbol table
  private int M;           // size of linear probing table
  private Node<V>[] entries;  // the hash table
  private Node<V> head;       // head of the linked list
  private Node<V> tail;       // tail of the linked list

  // create an empty hash table - use 16 as default size
  public PHPArray() {
    this(INIT_CAPACITY);
  }

  // create a PHPArray of given capacity
  public PHPArray(int capacity) {
    M = capacity;
    @SuppressWarnings("unchecked")
    Node<V>[] temp = (Node<V>[]) new Node[M];
    entries = temp;
    head = tail = null;
    N = 0;
  }

  public Iterator<V> iterator() {
    return new MyIterator();
  }


  // insert the key-value pair into the symbol table
  public void put(String key, V val) {
    // double table size if 50% full
    if (N >= M/2) resize(2*M);

    // linear probing
    int i;
    for (i = hash(key); entries[i] != null; i = (i + 1) % M) {
      // update the value if key already exists
      if (entries[i].key.equals(key)) {
        entries[i].value = val; return;
      }
    }
    // found an empty entry
    entries[i] = new Node<V>(key, val);

    // TODO: insert the node into the tail of the linked list in O(1) time
    if(head==null){
      head = tail = entries[i];
    }else{
      tail.next=entries[i];
      tail=entries[i];
    }

    N++;
  }

  // return the value associated with the given key, null if no such value
  public V get(String key) {
    for (int i = hash(key); entries[i] != null; i = (i + 1) % M)
      if (entries[i].key.equals(key))
        return entries[i].value;
    return null;
  }

  // hash function for keys - returns value between 0 and M-1
  private int hash(String key) {
    return (key.hashCode() & 0x7fffffff) % M;
  }

  // resize the hash table to the given capacity by re-hashing all of the keys
  private void resize(int capacity) {
    PHPArray<V> temp = new PHPArray<V>(capacity);

    // TODO: reinsert the entries from this into temp in original FIFO order
    Node<V> current= head;
    while(current !=null){
      temp.put(current.key,current.value);
      current=current.next;
    }
    entries = temp.entries;
    head    = temp.head;
    tail    = temp.tail;
    M       = temp.M;
  }


  //An inner class to store nodes of a singly-linked list
  //Each node contains a (key, value) pair
  private class Node<V> {
    private String key;
    private V value;
    private Node<V> next;

    Node(String key, V value){
      this(key, value, null);
    }

    Node(String key, V value, Node<V> next){
      this.key = key;
      this.value = value;
      this.next = next;
    }
  }


  public class MyIterator implements Iterator<V> {
    private Node<V> current;

    public MyIterator() {
      current = head;
    }

    public boolean hasNext() {
      return current != null;
    }

    public V next() {
      V result = current.value;
      current = current.next;
      return result;
    }
  }

  private static <V> void show(PHPArray<V> array){
    // print values in order of insertion
    System.out.println("Values in FIFO order:");
    System.out.print("\t");
    for (V i : array) {
        System.out.print(i + " ");
    }
    System.out.println();

    for (int i = 0; i < 10; i++) {
        System.out.println("A[\"Key" + i + "\"] = " + array.get("Key" + i));
    }
  }

  public static void main(String[] args) {
      PHPArray<Integer> A = new PHPArray<>(2);

      for (int i = 9; i >= 0; i--) {
          //insert ("Key0", 0), ("Key1", 1), ...
          A.put("Key" + i, i);
      }

      show(A);
  }
}
