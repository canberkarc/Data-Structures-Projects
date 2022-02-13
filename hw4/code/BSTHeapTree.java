/**
 * BSTHeapTree
 */

import java.util.Arrays;
import java.util.NoSuchElementException;

@SuppressWarnings("all")
public class BSTHeapTree<T extends Comparable<T>> extends HeapDistinct {

    public static class bst_node<T extends Comparable<T>> {
        /** bst left node
        */
        protected bst_node left;
        /** bst right node
        */
        protected bst_node right;
        /** HeapDistinct generic type data
        */
        private HeapDistinct<T> data;
        /**Constructor
        */
        public bst_node() {
            left = right = null;
            data = new HeapDistinct<T>(7);
        }
    }

    bst_node root;

    /** Constructor
    */
    public BSTHeapTree() {
        super(7);
        root = null;
    }

    /**Add element to the heap . If already there then increases the occurences
    * @param element element to be added
    */
    public int add(T element) {

        if (root == null) {
            root = new bst_node();
        }

        bst_node temp = root;

        while (true) {
            int idx[] = new int[2];
            boolean in_this_node = temp.data.search_element(element, idx);

            if (in_this_node) {
                temp.data.insert_to_heap(element);
                return temp.data.get_ith_elements_occurence(idx[0]);
            }

            /* If not Found in Current Node */

            int Hsize = temp.data.get_curr_size();
            int Msize = temp.data.get_max_size();

            if (Hsize >= 0 && Hsize < 7) {
                temp.data.insert_to_heap(element);
                return 1;
            }

            if (Hsize == Msize) {

                if ((temp.data.get_ith_element(1).compareTo(element)) > 0) {

                    if (temp.left != null) {
                        temp = temp.left;
                    } else {
                        temp.left = new bst_node();
                        temp.left.data.insert_to_heap(element);
                        return 1;
                    }
                } else if ((temp.data.get_ith_element(1).compareTo(element)) < 0) {
                    if (temp.right != null) {
                        temp = temp.right;
                    } else {
                        temp.right = new bst_node();
                        temp.right.data.insert_to_heap(element);
                        return 1;
                    }

                }
            }
        }
    }

    /**
    * Finds the element according to the its position in bst. It checks heap at
    * every node and returns the occurences if found else -1
    * @param element element to be found
    * @throws NoSuchElementException when tree is empty. Root is Null.
    */

    public int find(T element) {

        bst_node temp = root;

        if (temp == null) {
            throw new NoSuchElementException("Tree Is Empty. Root is Null.");
        }

        while (temp != null) {
            int idx[] = new int[2];
            idx[0] = -1;
            boolean in_this_node = temp.data.search_element(element, idx);


            if (in_this_node == true) {
                return temp.data.get_ith_elements_occurence(idx[0]);
            }

            /* Not Present in current */

            if (temp.data.get_ith_element(1).compareTo(element) > 0) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        /* Can't Find */
        return -1;
    }

    /**
    * Helper method to mode in the BST which element has maximum frequency by
    * post order traversal of BST and checking heap at every node.
    * @param node 
    * @param element
    * @param mode
    */

    private void helper_find_mode(bst_node node, Comparable[] element, int[] mode) {

        if (node == null) {
            return;
        }

        helper_find_mode(node.left, element, mode);
        helper_find_mode(node.right, element, mode);

        /* find mode for each node in BST */

        int Hsize = node.data.get_curr_size();

        for (int i = 1; i <= Hsize; i++) {

            if (mode[1] < node.data.get_ith_elements_occurence(i)) {

                element[0] = (T) (node.data.get_ith_element(i));
                mode[1] = node.data.get_ith_elements_occurence(i);

            }
        }

    }

    /**
    * Method to find the mode in BST.
    * @return mode
    */
    public int find_mode() {
        bst_node temp = root;
        int mode[] = new int[2];
        Comparable[] Tmode = new Comparable[2];
        helper_find_mode(temp, Tmode, mode);

        return mode[1];
    }

    /**
    * Post order Traversal of tree to print all heap nodes in the BST
    */
    public void showbst(bst_node node) {

        if (node == null) {
            return;
        }

        showbst(node.left);
        showbst(node.right);

        /* find mode for each node in BST */
        node.data.print();
    }

    /**
    * Helper Method to insert into the bst the Heap itself which is used in
    * remove method to maintain the bst satisying the conditions. It positions that
    * heap to proper place in BST.
    * @param root
    * @param node
    * @return root
    */

    private bst_node helper_insert(bst_node root, HeapDistinct<T> node) {
        if (root == null) {
            root = new bst_node();
            root.data.replace_heap(node);
            return root;
        }

        if (root.data.get_ith_element(1).compareTo(node.get_ith_element(1)) > 0) {
            root.left = helper_insert(root.left, node);
        } else if (root.data.get_ith_element(1).compareTo(node.get_ith_element(1)) < 0)
            root.right = helper_insert(root.right, node);

        return root;
    }

    /**
    * Insert Method to insert a heap in the BST.
    * @param root 
    * @param curr_node current node
    */
    private void insert_tree_node(bst_node root, HeapDistinct<T> curr_node) {
        if (curr_node.get_curr_size() == 0) {
            return;
        }
        root = helper_insert(root, curr_node);
    }

    /** Min node right sub tree
    * @param root
    * @return min_heap
    */
    private HeapDistinct<T> minnodeRST(bst_node root) {
        HeapDistinct<T> min_heap = root.data;

        while (root.left != null) {
            min_heap = root.data;
            root = root.left;
        }
        return min_heap;
    }

    /**
    * Method to delete a whole node in the BST with possibly heap in it used when
    * the maximum element is removed from a BST node and the new maximum doesn't
    * satisfies the BST conditions
    * @param root
    * @param heap
    * @param to_remove node to remove
    * @return root
    */
    private bst_node deleteNode(bst_node root, HeapDistinct<T> heap, bst_node[] to_remove) {
        if (root == null)
            return root;

        if (root.data.get_ith_element(1).compareTo(heap.get_ith_element(1)) > 0)
            root.left = deleteNode(root.left, heap, to_remove);
        else if (root.data.get_ith_element(1).compareTo(heap.get_ith_element(1)) < 0)
            root.right = deleteNode(root.right, heap, to_remove);

        else {

            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.data.replace_heap(minnodeRST(root.right));

            root.right = deleteNode(root.right, root.data, to_remove);
        }

        return root;

    }

    /**
    * Method to remove the Element if its occurences are greater than one or if
    * it is not the maximum in current heap or after removal the new maximum
    * satisfies BST criteria.
    * @param root
    * @param element
    * @param occ
    * @param to_remove
    * @param heap
    * @return root 
    */
    private bst_node custom_remove(bst_node root, T element, int occ[], bst_node[] to_remove, HeapDistinct<T> h) {

        if (root == null) {
            return root;
        }

        int Hsize = root.data.get_curr_size();
        Comparable[] ele = new Comparable[Hsize];
        int idx[] = new int[2];
        int itr = 0;
        boolean yes_here = root.data.search_element(element, idx);

        for (int i = 1; i <= Hsize; i++) {
            ele[itr++] = root.data.get_ith_element(i);
        }

        Arrays.sort(ele);
        /* Element is here but not a Max Element - Simply Delete */
        if (yes_here) {
            // System.out.println("Yes there 11");
            if (idx[0] != 1) {

                int pos = -1;
                for (int i = itr - 1; i >= 0; i--) {
                    if (ele[i].compareTo(element) == 0) {
                        pos = itr - i;
                        break;
                    }
                }
                root.data.remove_ith_largest(pos);

                boolean is_there = root.data.search_element(element, idx);
                if (is_there) {
                    occ[0] = root.data.get_ith_elements_occurence(idx[0]);
                } else {
                    occ[0] = 0;
                }

                return root;

            } else {
                if (root.data.get_ith_elements_occurence(1) > 1) {
                    root.data.remove_ith_largest(1);

                    System.out.println();
                    occ[0] = root.data.get_ith_elements_occurence(1);
                    return root;
                } else {

                    /* Remove this whole node and insert the heap after */

                    if (((root.left != null) && (itr > 1)
                            && (root.left.data.get_ith_element(1).compareTo(ele[itr - 2]) < 0))
                            && ((root.right != null) && (itr > 1)
                                    && (root.right.data.get_ith_element(1).compareTo(ele[itr - 2]) > 0))) {
                        root.data.remove_ith_largest(1);
                        occ[0] = 0;
                        return root;
                    } else {
                        /* Delete this whole node and insert again */
                        occ[0] = -2;
                        h.replace_heap(root.data);
                        return root;
                        /* using delete node */
                    }

                }
            }
        } else {
            if (root.data.get_ith_element(1).compareTo(element) > 0) {
                root.left = custom_remove(root.left, element, occ, to_remove, h);
            } else {
                root.right = custom_remove(root.right, element, occ, to_remove, h);
            }
        }

        return root;
    }

    /**
    * Method to remove the element from the BST
    * @param element element to be removed
    * @return int according to success
    */
    public int remove(T element) {

        if (root == null) {
            System.out.println("Binary Search Tree is Empty.");
            return -1;
        }

        int[] occ_after_removal = new int[2];
        bst_node[] removed_node = new bst_node[2];
        HeapDistinct<T> h = new HeapDistinct<T>(7);
        removed_node[0] = removed_node[1] = null;

        root = custom_remove(root, element, occ_after_removal, removed_node, h);

        if (occ_after_removal[0] != -2) {
            return occ_after_removal[0];
        }

        if (occ_after_removal[0] == -1) {
            System.out.println(element + " Not Found in BST");
            return -1;
        }

        root = deleteNode(root, h, removed_node);

        h.remove_ith_largest(1);

        insert_tree_node(root, h);

        return 0;
    }
}
