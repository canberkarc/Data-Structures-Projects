import java.util.*;

public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> implements SearchTree<E>, Iterable<E> {
  // Data Fields
  /** Return value from the public add method. */
  protected boolean isAdded;

  /** Return value from the public delete method. */
  protected E deletedData;

  // Methods
  /**
   * Starter method find. pre: The target object must implement the Comparable
   * interface.
   * 
   * @param target The Comparable object being sought
   * @return The object, if found, otherwise null
   */
  public E find(E target) {
    return find(root, target);
  }

  /**
   * Recursive find method.
   * 
   * @param localRoot The local subtree�s root
   * @param target    The object being sought
   * @return The object, if found, otherwise null
   */
  private E find(Node<E> localRoot, E target) {
    if (localRoot == null)
      return null;

    // Compare the target with the data field at the root.
    int compResult = target.compareTo(localRoot.data);
    if (compResult == 0)
      return localRoot.data;
    else if (compResult < 0)
      return find(localRoot.left, target);
    else
      return find(localRoot.right, target);
  }

  /**
   * Starter method add. pre: The object to insert must implement the Comparable
   * interface.
   * 
   * @param item The object being inserted
   * @return true if the object is inserted, false if the object already exists in
   *         the tree
   */
  public boolean add(E item) {
    root = add(root, item);
    return isAdded;
  }

  /**
   * Recursive add method. post: The data field isAdded is set true if the item is
   * added to the tree, false if the item is already in the tree.
   * 
   * @param localRoot The local root of the subtree
   * @param item      The object to be inserted
   * @return The new local root that now contains the inserted item
   */
  private Node<E> add(Node<E> localRoot, E item) {
    if (localRoot == null) {
      // item is not in the tree � insert it.
      isAdded = true;
      return new Node<E>(item);
    } else if (item.compareTo(localRoot.data) == 0) {
      // item is equal to localRoot.data
      isAdded = false;
      return localRoot;
    } else if (item.compareTo(localRoot.data) < 0) {
      // item is less than localRoot.data
      localRoot.left = add(localRoot.left, item);
      return localRoot;
    } else {
      // item is greater than localRoot.data
      localRoot.right = add(localRoot.right, item);
      return localRoot;
    }
  }

  /**
   * Starter method delete. post: The object is not in the tree.
   * 
   * @param target The object to be deleted
   * @return The object deleted from the tree or null if the object was not in the
   *         tree
   * @throws ClassCastException if target does not implement Comparable
   */
  public E delete(E target) {
    root = delete(root, target);
    return deletedData;
  }

  /**
   * Recursive delete method. post: The item is not in the tree; deletedData is
   * equal to the deleted item as it was stored in the tree or null if the item
   * was not found.
   * 
   * @param localRoot The root of the current subtree
   * @param item      The item to be deleted
   * @return The modified local root that does not contain the item
   */
  private Node<E> delete(Node<E> localRoot, E item) {
    if (localRoot == null) {
      // item is not in the tree.
      deletedData = null;
      return localRoot;
    }

    // Search for item to delete.
    int compResult = item.compareTo(localRoot.data);
    if (compResult < 0) {
      // item is smaller than localRoot.data.
      localRoot.left = delete(localRoot.left, item);
      return localRoot;
    } else if (compResult > 0) {
      // item is larger than localRoot.data.
      localRoot.right = delete(localRoot.right, item);
      return localRoot;
    } else {
      // item is at local root.
      deletedData = localRoot.data;
      if (localRoot.left == null) {
        // If there is no left child, return right child
        // which can also be null.
        return localRoot.right;
      } else if (localRoot.right == null) {
        // If there is no right child, return left child.
        return localRoot.left;
      } else {
        // Node being deleted has 2 children, replace the data
        // with inorder predecessor.
        if (localRoot.left.right == null) {
          // The left child has no right child.
          // Replace the data with the data in the
          // left child.
          localRoot.data = localRoot.left.data;
          // Replace the left child with its left child.
          localRoot.left = localRoot.left.left;
          return localRoot;
        } else {
          // Search for the inorder predecessor (ip) and
          // replace deleted node�s data with ip.
          localRoot.data = findLargestChild(localRoot.left);
          return localRoot;
        }
      }
    }
  }

  /**
   * Removes target from tree.
   * 
   * @param target Item to be removed
   * @return true if the object was in the tree, false otherwise
   * @post target is not in the tree
   * @throws ClassCastException if target is not Comparable
   */
  public boolean remove(E target) {
    return delete(target) != null;
  }

  /**
   * Determine if an item is in the tree
   * 
   * @param target Item being sought in tree
   * @return true If the item is in the tree, false otherwise
   * @throws ClassCastException if target is not Comparable
   */
  public boolean contains(E target) {
    return find(target) != null;
  }

  /**
   * Find the node that is the inorder predecessor and replace it with its left
   * child (if any). post: The inorder predecessor is removed from the tree.
   * 
   * @param parent The parent of possible inorder predecessor (ip)
   * @return The data in the ip
   */
  private E findLargestChild(Node<E> parent) {
    // If the right child has no right child, it is
    // the inorder predecessor.
    if (parent.right.right == null) {
      E returnValue = parent.right.data;
      parent.right = parent.right.left;
      return returnValue;
    } else {
      return findLargestChild(parent.right);
    }
  }

  /**************** PART2 Functions ***************/

  /** Return the Height of the Tree
  * @param root
  */
  private int heightofBST(Node<E> root) {

    int leftST_height, rightST_height;

    if (root == null) {
      return 0;
    }

    leftST_height = heightofBST(root.left);
    rightST_height = heightofBST(root.right);

    if (leftST_height > rightST_height) {
      return (1 + leftST_height);
    } else {
      return (1 + rightST_height);
    }

  }

  /** Checks if the Tree is AVL Balanced Or Not
  * @param root
  */
  public boolean isAVL(Node<E> root) {
    int leftST_height, rightST_height;

    if (root == null) {
      return true;
    }

    leftST_height = heightofBST(root.left);
    rightST_height = heightofBST(root.right);

    int difference = Math.abs(leftST_height - rightST_height);

    if (difference <= 1 && isAVL(root.left) && isAVL(root.right)) {
      return true;
    }
    return false;
  }

  /**
   * Checks Red Black Balancing of Tree For every node, length of the longest leaf
   * to node path has not more than twice the nodes on shortest path from node to
   * leaf.
   * @param root
   * @param maxHeight
   * @param minHeight
   */

  private boolean isRedBlackBalancedUtil(Node<E> root, int[] maxHeight, int[] minHeight) {

    // Base case
    if (root == null) {
      maxHeight[0] = minHeight[0] = 0;
      return true;
    }

    // To store max and min heights of left subtree
    int[] leftST_maxHeight = new int[1];
    int[] leftST_minHeight = new int[1];
    leftST_maxHeight[0] = 0;
    leftST_minHeight[0] = 0;

    // To store max and min heights of right subtree
    int[] rightST_minHeight = new int[1];
    int[] rightST_maxHeight = new int[1];
    rightST_maxHeight[0] = 0;
    rightST_minHeight[0] = 0;

    // Check if left subtree is balanced,
    // also set leftST_maxHeight and leftST_minHeight
    if (!isRedBlackBalancedUtil(root.left, leftST_maxHeight, leftST_minHeight))
      return false;

    // Check if right subtree is balanced,
    // also set rightST_maxHeight and rightST_minHeight
    if (!isRedBlackBalancedUtil(root.right, rightST_maxHeight, rightST_minHeight))
      return false;

    // Set the max and min heights
    // of this node for the parent call

    // System.out.println(leftST_minHeight + " " + rightST_minHeight);
    maxHeight[0] = Math.max(leftST_maxHeight[0], rightST_maxHeight[0]) + 1;
    minHeight[0] = Math.min(leftST_minHeight[0], rightST_minHeight[0]) + 1;

    // See if this node is Red Black balanced
    if (maxHeight[0] <= 2 * minHeight[0])
      return true;

    return false;
  }

  /**
   * Returns if root node is RED. Checks the Height Balanced Properites of Red
   * Black Tree
   * @param root
   */
  public boolean isRed(Node<E> root) {
    int[] maxHeight = new int[1];
    int[] minHeight = new int[1];
    maxHeight[0] = 0;
    minHeight[0] = 0;
    return isRedBlackBalancedUtil(root, maxHeight, minHeight);
  }

  /****************************** Iterator *****************************/

  /** iterator
  * @return BSTIterator
  */
  public Iterator<E> iterator() {
    return new BSTIterator();
  }

  // Iterator class for BST iteration
  private class BSTIterator implements Iterator<E> {
    Node<E> localRoot = root;
    Stack<Node<E>> st;

    public BSTIterator() {

      // Initialises the Stack
      st = new Stack<Node<E>>();

      // Pushes all the left nodes to the BST

      while (localRoot != null) {
        st.push(localRoot);
        localRoot = localRoot.left;
      }

    }

    /**
    * hasNext method
    */
    @Override
    public boolean hasNext() {
      return !st.isEmpty();
    }

    /**
    * next method
    */
    @Override
    public E next() {
      // Pops the current NOde and returns its data
      Node<E> node = st.pop();
      E data_popped = node.data;

      // if it has right subtree go to that and push all its left nodes to the stack
      // as well....
      if (node.right != null) {
        node = node.right;
        while (node != null) {
          st.push(node);
          node = node.left;
        }
      }
      return data_popped;
    }
  }

}
