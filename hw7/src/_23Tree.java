public class _23Tree<E extends Comparable<E>> {

    private static class _23Node<E> {

        _23Node<E> LChild;
        _23Node<E> MChild;
        _23Node<E> RChild;
        E firstKey;
        E secondKey;

        /** Constructor */
        public _23Node() {
            this.LChild = null;
            this.MChild = null;
            this.RChild = null;
            this.firstKey = null;
            this.secondKey = null;
        }

        /**
         * Constructor of 3 TTNodes without specific descendants (null references).
         */
        public _23Node(E firstKey, E secondKey) {
            this.firstKey = firstKey;
            this.secondKey = secondKey;
            LChild = null;
            MChild = null;
            RChild = null;
        }

        /**
         * Constructor of 3 TTNodes with given left and middle TTNodes / descendants.
         */
        public _23Node(E firstKey, E secondKey, _23Node<E> LChild, _23Node<E> MChild) {
            this.firstKey = firstKey;
            this.secondKey = secondKey;
            this.LChild = LChild;
            this.MChild = MChild;
        }

        public E getFirstKey() {
            return firstKey;
        }

        public void setFirstKey(E item) {
            this.firstKey = item;
        }

        public E getSecondKey() {
            return secondKey;
        }

        public _23Node<E> getLeftTTNode() {
            return LChild;
        }

        public _23Node<E> getMidTTNode() {
            return MChild;
        }

        public _23Node<E> getRightTTNode() {
            return RChild;
        }

        /**
         * @return true, if we are at the deepest level of a tree, otherwise false
         */
        public boolean isLeafNode() {
            return (LChild == null && MChild == null && RChild == null);
        }

        /**
         * @return true, if the right _23Node does not exist, otherwise false
         */
        public boolean is2Node() {
            return (secondKey == null);
        }

        /**
         * @return true, if the right _23Node exists, otherwise false
         */
        public boolean is3Node() {
            return (secondKey != null);
        }

    }

    /** Tree root */
    private _23Node<E> root;
    /** The number of tree elements */
    private int size;
    /** Tracks if the last item was added correctly or not. */
    private boolean lastAddedSuccessfully;

    /** constructor */
    _23Tree() {
        this.root = new _23Node<E>();
        this.size = 0;
    }

    /**
     * @return true, the tree is empty, otherwise false
     */
    public boolean isEmpty() {
        if (root == null)
            return true;
        return root.getFirstKey() == null;
    }

    /**
     * @return number of elements in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Adds a new item to the tree, keeping it balanced
     *
     * @param item - item to add
     */
    public void add(E item) {
        lastAddedSuccessfully = false;

        if (root == null || root.getFirstKey() == null) {
            lastAddedSuccessfully = true;

            if (root == null) {
                root = new _23Node<E>();
            }

            root.setFirstKey(item);
        } else {
            _23Node<E> newRoot = add(root, item);
            if (newRoot != null) {
                root = newRoot;
            }
        }

        if (lastAddedSuccessfully)
            size++;
    }

    /**
     * @param currNode _23Node to add to
     * @param item     - item to add
     */
    private _23Node<E> add(_23Node<E> currNode, E item) {

        _23Node<E> newParent = null; // _23Node to be added

        // We are not yet at the deepest level
        if (!currNode.isLeafNode()) {

            _23Node<E> newTTNode;

            // Element already exists
            if (currNode.firstKey.compareTo(item) == 0
                    || (currNode.is3Node() && currNode.secondKey.compareTo(item) == 0)) {
            }

            // newTTNode < left item
            else if (currNode.firstKey.compareTo(item) == 1) {
                newTTNode = add(currNode.LChild, item);

                // newTTNode comes from the left branch
                if (newTTNode != null) {

                    // newTTNode < than currNode.left
                    if (currNode.is2Node()) {
                        currNode.secondKey = currNode.firstKey; // Move the currNode left item to the right
                        currNode.firstKey = newTTNode.firstKey;
                        currNode.RChild = currNode.MChild;
                        currNode.MChild = newTTNode.MChild;
                        currNode.LChild = newTTNode.LChild;
                    }

                    // We have a new division, so the currNode item on the left will rise
                    else {

                        // Copy the right side of the subtree
                        _23Node<E> rightCopy = new _23Node<E>(currNode.secondKey, null, currNode.MChild,
                                currNode.RChild);

                        // Create a new "structure" by inserting the right side
                        newParent = new _23Node<E>(currNode.firstKey, null, newTTNode, rightCopy);
                    }
                }
            }

            // newTTNode is > left and < right
            else if (currNode.is2Node() || (currNode.is3Node() && currNode.secondKey.compareTo(item) == 1)) {

                newTTNode = add(currNode.MChild, item);

                // New division
                if (newTTNode != null) {

                    // The right item is empty, so we can set newTTNode on the left, and the
                    // existing left item on the right
                    if (currNode.is2Node()) {
                        currNode.secondKey = newTTNode.firstKey;
                        currNode.RChild = newTTNode.MChild;
                        currNode.MChild = newTTNode.LChild;
                    }

                    // Another case where we have to split again
                    else {
                        _23Node<E> left = new _23Node<E>(currNode.firstKey, null, currNode.LChild, newTTNode.LChild);
                        _23Node<E> mid = new _23Node<E>(currNode.secondKey, null, newTTNode.MChild, currNode.RChild);
                        newParent = new _23Node<E>(newTTNode.firstKey, null, left, mid);
                    }
                }
            }

            // newTTNode is larger than the right item
            else if (currNode.is3Node() && currNode.secondKey.compareTo(item) == -1) {

                newTTNode = add(currNode.RChild, item);

                // Divide -> the right item rises
                if (newTTNode != null) {
                    _23Node<E> leftCopy = new _23Node<E>(currNode.firstKey, null, currNode.LChild, currNode.MChild);
                    newParent = new _23Node<E>(currNode.secondKey, null, leftCopy, newTTNode);
                }
            }
        }

        // We are at the deepest level
        else {
            lastAddedSuccessfully = true;

            // Element already exists
            if (currNode.firstKey.compareTo(item) == 0
                    || (currNode.is3Node() && currNode.secondKey.compareTo(item) == 0)) {
                lastAddedSuccessfully = false;
            }

            // The case when there is no right item
            else if (currNode.is2Node()) {

                // If the currNode left item is larger than newTTNode, we move the left
                // item
                // to the right
                if (currNode.firstKey.compareTo(item) == 1) {
                    currNode.secondKey = currNode.firstKey;
                    currNode.firstKey = item;
                }

                // If newTTNode is larger, we add it to the right
                else if (currNode.firstKey.compareTo(item) == -1)
                    currNode.secondKey = item;
            }

            // The case when the _23Node has 2 elements, and we want to add another one. To
            // do
            // this, we share the _23Node
            else
                newParent = split(currNode, item);
        }

        return newParent;
    }

    /**
     * The method creates a new _23Node structure that will be attached at the
     * bottom of the add() method
     *
     * @param currNode - the _23Node where the separation occurs
     * @param item     - item to insert
     * @return two-_23Node structure with a nonzero left and middle _23Node
     */
    private _23Node<E> split(_23Node<E> currNode, E item) {

        _23Node<E> newParent = null;

        // The left item is larger, so it will rise, allowing newParent to stand on
        // the left
        if (currNode.firstKey.compareTo(item) == 1) {
            _23Node<E> left = new _23Node<>(item, null);
            _23Node<E> right = new _23Node<E>(currNode.secondKey, null);
            newParent = new _23Node<E>(currNode.firstKey, null, left, right);

        } else if (currNode.firstKey.compareTo(item) == -1) {
            _23Node<E> left = new _23Node<E>(currNode.firstKey, null);

            // newParent is greater than the currNode on the right and smaller than the
            // right. newParent rises.
            if (currNode.secondKey.compareTo(item) == 1) {
                _23Node<E> right = new _23Node<E>(currNode.secondKey, null);
                newParent = new _23Node<E>(item, null, left, right);
            }

            // newParent is the largest, so the currNode right item is raised
            else {
                _23Node<E> right = new _23Node<>(item, null);
                newParent = new _23Node<E>(currNode.secondKey, null, left, right);
            }
        }

        return newParent;
    }

    public void inOrderTraversal() {
        if (!isEmpty()) {
            inOrderTraversal(root);
        } else {
            System.out.print("The tree is empty...");
        }
    }

    /**
     * Method for displaying tree elements in the order of the method - "in-order"
     */
    private void inOrderTraversal(_23Node<E> currNode) {
        if (currNode != null) {

            if (currNode.isLeafNode()) {
                System.out.print(currNode.getFirstKey().toString() + " ");
                if (currNode.getSecondKey() != null) {
                    System.out.print(currNode.getSecondKey().toString() + " ");
                }

            } else {
                inOrderTraversal(currNode.getLeftTTNode());
                System.out.print(currNode.getFirstKey().toString() + " ");
                inOrderTraversal(currNode.getMidTTNode());

                if (currNode.getSecondKey() != null) {
                    if (!currNode.isLeafNode()) {
                        System.out.print(currNode.getSecondKey().toString() + " ");
                    }
                    inOrderTraversal(currNode.getRightTTNode());
                }
            }
        }
    }
}
