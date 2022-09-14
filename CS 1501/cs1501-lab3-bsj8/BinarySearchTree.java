public class
  BinarySearchTree<T extends Comparable<? super T>> {
    BinaryNode<T> root;

    public BinarySearchTree(){
      root = null;
    }


    public T add(T entry){
      T result = null;
      if(root == null){
        root = new BinaryNode<>(entry);
      } else {
        result = addEntry(root, entry);
      }
      return result;
    }

    private T addEntry(BinaryNode<T> root, T entry){
      T result = null;
      int comparison = root.data.compareTo(entry);
      if(comparison == 0){
        result = root.data;
        root.data = entry;
      } else if(comparison < 0){
        if(root.right != null){
          result = addEntry(root.right, entry);
        } else {
          root.right = new BinaryNode<>(entry);
        }
      } else {
        if(root.left != null){
          result = addEntry(root.left, entry);
        } else {
          root.left = new BinaryNode<>(entry);
        }
      }
      return result;
    }

    public T predecessor(T entry){
        BinaryNode<T> predNode = predecessor(root, entry);
        if(predNode != null){ //found a predecessor node
          return predNode.data;
        } else {//entry is the smallest in the tree; no predecssor
          return null;
        }
    }

    private BinaryNode<T> predecessor(BinaryNode<T> root, T entry){
      BinaryNode<T> result = null;

      if(root != null){
        int compareResult = root.data.compareTo(entry);
        if(compareResult == 0){ //found the node;
                                //predecssor is the largest in left subtree
          result = findLargest(root.left);
        }
        //TODO: replace true the condition in which we have to move right
        else if(compareResult<0){ //entry > root; move right
          result = predecessor(root.right, entry);
          if(result == null){ //couldn't find a predecssor;
                                //the first right parent (root) is the predecessor
            //TODO: replace null with the first right parent (root)
            result = root;
          }
        } else { //entry < root; move left
          result = predecessor(root.left, entry);
        }
      }
      return result;
    }

    public T successor(T entry){
      BinaryNode<T> succNode = successor(root, entry);
      if(succNode != null){ //found a successor node
        return succNode.data;
      } else {//entry is the largest in the tree; no successor
        return null;
      }
    }

    private BinaryNode<T> successor(BinaryNode<T> root, T entry){
      BinaryNode<T> result = null;

      if(root != null){
        int compareResult = root.data.compareTo(entry);
        if(compareResult == 0){ //found the node;
                                //successor is the smallest in right subtree
          //TODO: replace null with code to find the smallest in the right subtree
          result = findSmallest(root.right);
        } else if(compareResult < 0){ //entry > root; move right
          result = successor(root.right, entry);
        } else { //entry < root; move left
          result = successor(root.left, entry);
          if(result == null){ //couldn't find a successor;
                              //the first left parent (root) is the successor
            result = root;
          }
        }
      }
      return result;
    }

    private BinaryNode<T> findLargest(BinaryNode<T> root){
        BinaryNode<T> result = null;
        if(root != null){
          if(root.right != null){
            result = findLargest(root.right);
          } else {
            result = root;
          }
        }
        return result;
    }

    private BinaryNode<T> findSmallest(BinaryNode<T> root){
      BinaryNode<T> result = null;
      if(root != null){
        if(root.left != null){
          //TODO: replace null with code to find the smallest in the left subtree
          result = findSmallest(root.left);
        } else {
          result = root;
        }
      }
      return result;
    }

    private class BinaryNode<T> {
      private T data;
      private BinaryNode<T> left;
      private BinaryNode<T> right;

      public BinaryNode(T data){
        this(data, null, null);
      }

      public BinaryNode(T data, BinaryNode<T> left,
                       BinaryNode<T> right){
          this.data = data;
          this.left = left;
          this.right = right;
      }
    }
}
