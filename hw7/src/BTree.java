@SuppressWarnings("unchecked")
public class BTree<E extends Comparable<E>> {

    private static class BTNode<E extends Comparable<E>> {
        private int size = 0;
        private E[] data;
        private BTNode<E>[] child;

        public BTNode(int order) {
            data = (E[]) new Comparable[order - 1];
            child = (BTNode<E>[]) new BTNode[order];
            size = 0;
        }
    }

    private BTNode<E> root = null;
    private int order;
    private BTNode<E> newChild = null;
    private E newParent = null;

    public BTree(int order) {
        this.order = order;
        root = null;
    }

    /** returns the index of the item if it is present or the index of the position
    * where the item should be inserted.
    * @param data
    * @param item
    * @param leftBound
    * @param rightBound
    */
    private int binarySearch(E[] data, E item, int leftBound, int rightBound) {
        if (leftBound > rightBound)
            return -1;
        else {
            int mid = (leftBound + rightBound) / 2;
            int compareVal = 0;
            if (data[mid] != null)
                compareVal = item.compareTo(data[mid]);
            if (compareVal == 0)
                return mid;
            else if (compareVal < 0) {
                if (mid < 1)
                    return mid;
                else if (data[mid - 1] != null && item.compareTo(data[mid - 1]) > 0)
                    return mid;
                else
                    return binarySearch(data, item, leftBound, mid - 1);
            } else {
                if (mid + 1 >= root.size)
                    return mid + 1;
                else if (data[mid + 1] != null && item.compareTo(data[mid + 1]) < 0)
                    return mid + 1;
                else
                    return binarySearch(data, item, mid + 1, rightBound);
            }
        }
    }

    /** adds item
    * @param item
    * @return result boolean
    */
    public boolean add(E item) {
        if (root == null) {
            root = new BTNode<E>(order);
            root.data[0] = item;
            root.size = 1;
            return true;
        }
        newChild = null;
        boolean result = insert(root, item);
        if (newChild != null) {
            BTNode<E> newRoot = new BTNode<E>(order);
            newRoot.child[0] = root;
            newRoot.child[1] = newChild;
            newRoot.data[0] = newParent;
            newRoot.size = 1;
            root = newRoot;
        }
        return result;
    }

    /** inserts item
    * @param root
    * @param item
    * @return result boolean
    */
    private boolean insert(BTNode<E> root, E item) {
        int index = binarySearch(root.data, item, 0, root.size);
        if (index != root.size && item.compareTo(root.data[index]) == 0) {
            return false;
        }
        if (root.child[index] == null) {
            if (root.size < order - 1) {
                insertIntoNode(root, index, item, null);
                newChild = null;
            } else {
                splitNode(root, index, item, null);
            }
            return true;
        } else {
            boolean result = insert(root.child[index], item);
            if (newChild != null) {
                if (root.size < order - 1) {
                    insertIntoNode(root, index, newParent, newChild);
                    newChild = null;
                } else {
                    splitNode(root, index, newParent, newChild);
                }
            }
            return result;
        }
    }

    /** inserts into node
    * @param node
    * @param index
    * @param obj
    * @param child
    */
    private void insertIntoNode(BTNode<E> node, int index, E obj, BTNode<E> child) {
        for (int i = node.size; i > index; i--) {
            node.data[i] = node.data[i - 1];
            node.child[i + 1] = node.child[i];
        }
        node.data[index] = obj;
        node.child[index + 1] = child;
        node.size++;
    }
    
    private void splitNode(BTNode<E> node, int index, E item, BTNode<E> child) {
        newChild = new BTNode<E>(order);
        int numToMove = (order - 1) - ((order - 1) / 2);
        if (index > (order - 1) / 2) {
            numToMove--;
        }
        System.arraycopy(node.data, order - numToMove - 1, newChild.data, 0, numToMove);
        System.arraycopy(node.child, order - numToMove, newChild.child, 1, numToMove);
        node.size = order - numToMove - 1;
        newChild.size = numToMove;

        if (index == ((order - 1) / 2)) {
            newParent = item;
            newChild.child[0] = child;
        } else {
            if (index < ((order - 1) / 2))
                insertIntoNode(node, index, item, child);
            else
                insertIntoNode(newChild, index - ((order - 1) / 2) - 1, item, child);
            newParent = node.data[node.size - 1];
            newChild.child[0] = node.child[node.size];
            node.size--;
        }
        for (int i = node.size; i < node.data.length; i++) {
            node.data[i] = null;
            node.child[i + 1] = null;
        }
    }

}