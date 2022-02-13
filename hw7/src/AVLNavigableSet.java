import java.util.*;

// A simple interface
abstract interface NavigableSet<E extends Comparable<E>> {

    /** adds the item to the tree
    * @param item
    */
    public boolean add(E item);

    /** Gives the HeadSet -- elements smaller than the item
    * @param item
    */
    public AVLNavigableSet<E> headSet(E item);

    /** Gives the HeadSet -- elements smaller or equal to than the item
    * @param item
    * @param inclusive
    */
    public AVLNavigableSet<E> headSet(E item, boolean inclusive);

    /** Gives the Tailset -- elements greater  to than the item
    * @param item
    */
    public AVLNavigableSet<E> tailSet(E item);

    /** Gives the Tailset -- elements greater or equal to than the item
    * @param item
    * @param inclusive
    */
    public AVLNavigableSet<E> tailSet(E item, boolean inclusive);

    /** Displays the Set..
    */
    public Iterator<E> iterator();
}

@SuppressWarnings("unchecked")
public class AVLNavigableSet<E extends Comparable<E>> extends AVLTree<E> implements NavigableSet<E> {

    /** Tree for the Navigable Set Implementation
    */
    AVLTree<E> setTree = new AVLTree<E>();

    /** adds the item to the tree
    */
    public boolean add(E item) {
        boolean added = setTree.add(item);
        return added;
    }

    /** Gives the HeadSet -- elements smaller than the item
    * @param item
    */
    public AVLNavigableSet<E> headSet(E item) {
        int[] sz = new int[1];
        Comparable<E>[] array = setTree.headSetHelper(setTree.root, item, sz);

        AVLNavigableSet<E> newSet = new AVLNavigableSet<E>();

        for (int i = 0; i < sz[0]; i++) {
            newSet.add((E) (array[i]));
        }

        return newSet;
    }

    /** Gives the HeadSet -- elements smaller or equal to than the item
    * @param item
    * @param inclusive
    */
    public AVLNavigableSet<E> headSet(E item, boolean inclusive) {
        int[] sz = new int[1];
        Comparable<E>[] array;

        if (!inclusive) {
            array = setTree.headSetHelper(setTree.root, item, sz);
        } else {
            array = setTree.headSetHelper(setTree.root, item, sz, inclusive);
        }

        AVLNavigableSet<E> newSet = new AVLNavigableSet<E>();

        for (int i = 0; i < sz[0]; i++) {
            newSet.add((E) (array[i]));
        }

        return newSet;
    }

    /** Gives the Tailset -- elements greater  to than the item
    * @param item
    */

    public AVLNavigableSet<E> tailSet(E item) {
        int[] sz = new int[1];
        Comparable<E>[] array = setTree.tailSetHelper(setTree.root, item, sz);

        AVLNavigableSet<E> newSet = new AVLNavigableSet<E>();

        for (int i = 0; i < sz[0]; i++) {
            newSet.add((E) (array[i]));
        }

        return newSet;
    }

    /** Gives the Tailset -- elements greater or equal to than the item
    * @param item
    * @param inclusive
    */

    public AVLNavigableSet<E> tailSet(E item, boolean inclusive) {
        int[] sz = new int[1];
        Comparable<E>[] array;

        if (!inclusive) {
            array = setTree.tailSetHelper(setTree.root, item, sz);
        } else {
            array = setTree.tailSetHelper(setTree.root, item, sz, inclusive);
        }

        AVLNavigableSet<E> newSet = new AVLNavigableSet<E>();

        for (int i = 0; i < sz[0]; i++) {
            newSet.add((E) (array[i]));
        }

        return newSet;
    }

    /** Returns Forward iterator of normal Binary Search Tree
    */
    public Iterator<E> iterator() {
        return setTree.iterator();
    }

    /** Displays the Set..
    */
    public void display() {
        System.out.println(setTree.toString());
    }

}
