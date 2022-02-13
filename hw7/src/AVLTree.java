
/**
 * Self-balancing binary search tree using the algorithm defined by
 * Adelson-Velskii and Landis.
 * 
 * @author Koffman and Wolfgang
 */

import java.util.*;

@SuppressWarnings("all")
public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {

  // Insert nested class AVLNode<E> here.

  // Data Fields
  /** Flag to indicate that height of tree has increased. */
  private boolean increase;

  /** Flag to indicate that height of tree has decreased */
  private boolean decrease;

  /**
   * Class to represent an AVL Node. It extends the BinaryTree.Node by adding the
   * balance field.
   */
  private static class AVLNode<E> extends Node<E> {
    /** Constant to indicate left-heavy */
    public static final int LEFT_HEAVY = -1;

    /** Constant to indicate balanced */
    public static final int BALANCED = 0;

    /** Constant to indicate right-heavy */
    public static final int RIGHT_HEAVY = 1;

    /** balance is right subtree height � left subtree height */
    private int balance;

    // Methods
    /**
     * Construct a node with the given item as the data field.
     * 
     * @param item The data field
     */
    public AVLNode(E item) {
      super(item);
      balance = BALANCED;
    }

    /**
     * Return a string representation of this object. The balance value is appended
     * to the contents.
     * 
     * @return String representation of this object
     */
    public String toString() {
      return "[Balance: " + balance + ", Item: " + super.toString() + "]\n";
    }
  }

  /**
   * add starter method. pre: the item to insert implements the Comparable
   * interface.
   * 
   * @param item The item being inserted.
   * @return true if the object is inserted; false if the object already exists in
   *         the tree
   * @throws ClassCastException if item is not Comparable
   */
  public boolean add(E item) {
    increase = false;
    root = add((AVLNode<E>) root, item);
    return isAdded;
  }

  /**
   * Recursive add method. Inserts the given object into the tree. post: isAdded
   * is set true if the item is inserted, false if the item is already in the
   * tree.
   * 
   * @param localRoot The local root of the subtree
   * @param item      The object to be inserted
   * @return The new local root of the subtree with the item inserted
   */
  private AVLNode<E> add(AVLNode<E> localRoot, E item) {
    if (localRoot == null) {
      isAdded = true;
      increase = true;
      return new AVLNode<E>(item);
    }

    if (item.compareTo(localRoot.data) == 0) {
      // Item is already in the tree.
      increase = false;
      isAdded = false;
      return localRoot;
    }

    else if (item.compareTo(localRoot.data) < 0) {
      // item < data
      localRoot.left = add((AVLNode<E>) localRoot.left, item);

      if (increase) {
        decrementBalance(localRoot);
        if (localRoot.balance < AVLNode.LEFT_HEAVY) {
          increase = false;
          return rebalanceLeft(localRoot);
        }
      }
      return localRoot; // Rebalance not needed.
    } else {
      // item > data
      localRoot.right = add((AVLNode<E>) localRoot.right, item);
      if (increase) {
        incrementBalance(localRoot);
        if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
          return rebalanceRight(localRoot);
        } else {
          return localRoot;
        }
      } else {
        return localRoot;
      }
    }

  }

  /**
   * Method to increment the balance field and to reset the value of increase or
   * decrease.
   * 
   * @pre The balance field was correct prior to an insertion or removal, and an
   *      item is either been added to the right or removed from the left.
   * @post The balance is incremented and the increase and decrease flags are set
   *       to false if the overall height of this subtree has not changed.
   * @param node The AVL node whose balance is to be incremented
   */
  private void incrementBalance(AVLNode<E> node) {
    node.balance++;
    if (node.balance > AVLNode.BALANCED) {
      /*
       * if now right heavy, the overall height has increased, but it has not
       * decreased
       */
      increase = true;
      decrease = false;
    } else {
      /*
       * if now balanced, the overall height has not increased, but it has decreased.
       */
      increase = false;
      decrease = true;
    }
  }

  /**
   * rebalanceRight
   * 
   * @pre localRoot is the root of an AVL subtree that is more than one right
   *      heavy.
   * @post balance is restored and increase is set false
   * @param localRoot Root of the AVL subtree that needs rebalancing
   * @return a new localRoot
   */
  private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
    // Obtain reference to right child
    AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
    // See if right-left heavy
    if (rightChild.balance < AVLNode.BALANCED) {
      // Obtain reference to right-left child
      AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
      /*
       * Adjust the balances to be their new values after the rotates are performed.
       */
      if (rightLeftChild.balance > AVLNode.BALANCED) {
        rightChild.balance = AVLNode.BALANCED;
        rightLeftChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.LEFT_HEAVY;
      } else if (rightLeftChild.balance < AVLNode.BALANCED) {
        rightChild.balance = AVLNode.RIGHT_HEAVY;
        rightLeftChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.BALANCED;
      } else {
        rightChild.balance = AVLNode.BALANCED;
        rightLeftChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.BALANCED;
      }
      /**
       * After the rotates the overall height will be reduced thus increase is now
       * false, but decrease is now true.
       */
      increase = false;
      decrease = true;
      // Perform double rotation
      localRoot.right = rotateRight(rightChild);
      return (AVLNode<E>) rotateLeft(localRoot);
    } else {
      /*
       * In this case both the rightChild (the new root) and the root (new left child)
       * will both be balanced after the rotate. Also the overall height will be
       * reduced, thus increase will be fasle, but decrease will be true.
       */
      rightChild.balance = AVLNode.BALANCED;
      localRoot.balance = AVLNode.BALANCED;
      increase = false;
      decrease = true;
      // Now rotate the
      return (AVLNode<E>) rotateLeft(localRoot);
    }
  }

  /**
   * Method to rebalance left. pre: localRoot is the root of an AVL subtree that
   * is critically left-heavy. post: Balance is restored.
   * 
   * @param localRoot Root of the AVL subtree that needs rebalancing
   * @return a new localRoot
   */
  private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
    // Obtain reference to left child.
    AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
    // See whether left-right heavy.
    if (leftChild.balance > AVLNode.BALANCED) {
      // Obtain reference to left-right child.
      AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
      /**
       * Adjust the balances to be their new values after the rotations are performed.
       */
      if (leftRightChild.balance < AVLNode.BALANCED) {
        leftChild.balance = AVLNode.BALANCED;
        leftRightChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.RIGHT_HEAVY;
      } else {
        leftChild.balance = AVLNode.LEFT_HEAVY;
        leftRightChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.BALANCED;
      }
      // Perform left rotation.
      localRoot.left = rotateLeft(leftChild);
    } else { // Left-Left case
      /**
       * In this case the leftChild (the new root) and the root (new right child) will
       * both be balanced after the rotation.
       */
      leftChild.balance = AVLNode.BALANCED;
      localRoot.balance = AVLNode.BALANCED;
    }
    // Now rotate the local root right.
    return (AVLNode<E>) rotateRight(localRoot);
  }

  /** Decrements the balance.
  * @param node
  */
  private void decrementBalance(AVLNode<E> node) {
    node.balance--;
    if (node.balance == AVLNode.BALANCED) {
      /** If now balanced, overall height has not increased. */
      increase = false;
    }
  }

  /******************************* Part 1 Helpers **************************/

  /** Inorderly Traverse and returns an array of element from which a Set can be Made and returned
  * @param localRoot
  * @param array
  * @param idx
  * @param item
  */
  private void getHeadSet(Node<E> localRoot, Comparable<E>[] array, int[] idx, E item) {

    if (localRoot == null) {
      return;
    }
    getHeadSet(localRoot.left, array, idx, item);

    // check if Smaller
    if (localRoot.data.compareTo(item) < 0) {
      array[idx[0]] = localRoot.data;
      idx[0]++;
    }

    getHeadSet(localRoot.right, array, idx, item);
  }
  /** Inorderly Traverse and returns an array of element from which a Set can be Made and returned.. Returns Inclusive Array if the Item is Present
  * @param localRoot
  * @param array
  * @param idx
  * @param item
  */
  private void getHeadSetInclusive(Node<E> localRoot, Comparable<E>[] array, int[] idx, E item) {

    if (localRoot == null) {
      return;
    }
    getHeadSetInclusive(localRoot.left, array, idx, item);

    // check if Smaller
    if (localRoot.data.compareTo(item) <= 0) {
      array[idx[0]] = localRoot.data;
      idx[0]++;
    }

    getHeadSetInclusive(localRoot.right, array, idx, item);
  }

  /** Helper method
  * @param localRoot
  * @param item 
  * @param sz
  */
  public Comparable<E>[] headSetHelper(Node<E> localRoot, E item, int[] sz) {
    int treeSize = size(localRoot);
    Comparable<E>[] array = (Comparable<E>[]) new Comparable[treeSize + 2];

    int idx[] = new int[1];
    idx[0] = 0;
    getHeadSet(localRoot, array, idx, item);

    sz[0] = idx[0];
    return array;
  }

  /** Overloaded for Boolean Parameter
  *
  */
  public Comparable<E>[] headSetHelper(Node<E> localRoot, E item, int[] sz, boolean inclusive) {
    int treeSize = size(localRoot);
    Comparable<E>[] array = (Comparable<E>[]) new Comparable[treeSize + 2];

    int idx[] = new int[1];
    idx[0] = 0;

    getHeadSetInclusive(localRoot, array, idx, item);

    sz[0] = idx[0];
    return array;
  }

  /*************************** TailSET Helpers *****************************/

  /** Inorderly Traverse and returns an array of element from which a Set can be Made and returned
  * @param localRoot
  * @param array
  * @param idx
  * @param item
  */
  private void getTailSet(Node<E> localRoot, Comparable<E>[] array, int[] idx, E item) {

    if (localRoot == null) {
      return;
    }
    getTailSet(localRoot.left, array, idx, item);

    // check if Greater
    if (localRoot.data.compareTo(item) > 0) {
      array[idx[0]] = localRoot.data;
      idx[0]++;
    }

    getTailSet(localRoot.right, array, idx, item);
  }

  /** Inorderly Traverse and returns an array of element from which a Set can be Made and returned.. Returns Inclusive Array if the Item is Present
  * @param localRoot
  * @param array
  * @param idx
  * @param item
  */
  private void getTailSetInclusive(Node<E> localRoot, Comparable<E>[] array, int[] idx, E item) {

    if (localRoot == null) {
      return;
    }
    getTailSetInclusive(localRoot.left, array, idx, item);

    // check if Greater or Equal to
    if (localRoot.data.compareTo(item) >= 0) {
      array[idx[0]] = localRoot.data;
      idx[0]++;
    }

    getTailSetInclusive(localRoot.right, array, idx, item);
  }

  /** tailset helper
  * @param item
  * @param sz
  */
  public Comparable<E>[] tailSetHelper(Node<E> localRoot, E item, int[] sz) {
    int treeSize = size(localRoot);
    Comparable<E>[] array = (Comparable<E>[]) new Comparable[treeSize + 2];

    int idx[] = new int[1];
    idx[0] = 0;
    getTailSet(localRoot, array, idx, item);

    sz[0] = idx[0];
    return array;
  }

  /** Overloaded tailset helper
  * @param localRoot
  * @param item
  * @param sz
  * @param inclusive
  */
  public Comparable<E>[] tailSetHelper(Node<E> localRoot, E item, int[] sz, boolean inclusive) {
    int treeSize = size(localRoot);
    Comparable<E>[] array = (Comparable<E>[]) new Comparable[treeSize + 2];

    int idx[] = new int[1];
    idx[0] = 0;

    getTailSetInclusive(localRoot, array, idx, item);

    sz[0] = idx[0];
    return array;
  }

}
