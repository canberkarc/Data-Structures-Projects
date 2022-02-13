import java.io.*;
import java.util.*;

@SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {
        /* Random Numbers Generator */

        Random rand = new Random();

        /**************************** PART 1 ****************************/

        System.out.println("\t\t\t\t/*************** PART 1 ***************/\n\n");

        /* A. NavigableSet using SkipList */

        SkipListNavigableSet<Integer> ns1 = new SkipListNavigableSet<Integer>();

        /* Add Method */
        int p1_arr1[] = new int[10];

        for (int i = 0; i < 10; i++) {
            p1_arr1[i] = rand.nextInt(1000000);
            ns1.add(p1_arr1[i]);
        }

        System.out.println("Navigable Set 1 : ");
        ns1.display();
        System.out.println();

        System.out.println("Descending Iteration Navigable Set 1 : ");
        Iterator<Integer> ns1_itr1 = ns1.descendingIterator();

        while (ns1_itr1.hasNext()) {
            System.out.println("Data: " + ns1_itr1.next());
        }
        System.out.println();

        for (int i = 3; i < 6; i++) {
            System.out.println("To Remove : " + p1_arr1[i] + " <-> Removed :" + ns1.remove(p1_arr1[i]));
        }

        System.out.println("To Remove : " + (-30000) + " <-> Removed :"
                + (ns1.remove(-30000) == null ? "Item Not Present" : ns1.remove(-30000)));

        System.out.println();
        System.out.println("Navigable Set 1 after Removal : ");
        ns1.display();
        System.out.println();

        // ----------------------------------------------------------------//

        /* B. NavigableSet using AVLTrees */

        AVLNavigableSet<Integer> ns2 = new AVLNavigableSet<Integer>();

        /* Add Method */
        int p1_arr2[] = new int[10];

        for (int i = 0; i < 10; i++) {
            p1_arr2[i] = rand.nextInt(1000000);
            ns2.add(p1_arr2[i]);
        }

        /* Inorder Traversal Should give a Sorted Set */
        System.out.println("Navigable Set 2 : ");
        ns2.display();

        AVLNavigableSet<Integer> head = ns2.headSet(p1_arr2[4]);

        System.out.println();
        System.out.println("HeadSet of Value " + p1_arr2[4] + " is :\n");
        head.display();
        System.out.println();

        AVLNavigableSet<Integer> tail = ns2.tailSet(p1_arr2[4]);

        System.out.println("TailSet of Value " + p1_arr2[4] + " is :\n");
        tail.display();
        System.out.println();

        AVLNavigableSet<Integer> headinc = ns2.headSet(p1_arr2[4], true);

        System.out.println("HeadSet Inclusive of Value " + p1_arr2[4] + " is :\n");
        headinc.display();
        System.out.println();

        AVLNavigableSet<Integer> tailinc = ns2.tailSet(p1_arr2[4], true);

        System.out.println("TailSet Inclusive of Value " + p1_arr2[4] + " is :\n");
        tailinc.display();
        System.out.println();

        System.out.println("Iterating Navigable Set 2 : ");

        Iterator<Integer> ns2_itr1 = ns2.iterator();

        while (ns2_itr1.hasNext()) {
            System.out.println("Data: " + ns2_itr1.next());
        }
        System.out.println();

        System.out.println("Iterating HeadSet of Navigable Set : ");

        Iterator<Integer> ns2_itr2 = head.iterator();

        while (ns2_itr2.hasNext()) {
            System.out.println("Data: " + ns2_itr2.next());
        }
        System.out.println();

        // ------------------------------------------------------ //

        /************************* PART 2 *************************/

        /*
         * A. Checking if a given BST is an AVL Balanced BST or Not
         */

        int[] p2_arr1 = { 8, 6, 10, 4, 7, 9, 11, 3, 5 };

        BinarySearchTree<Integer> p2_bst1 = new BinarySearchTree<Integer>();

        for (int i = 0; i < 9; i++) {
            p2_bst1.add(p2_arr1[i]);
        }

        System.out.println("Binary Search Tree 1 : ");
        System.out.println(p2_bst1.toString());
        System.out.println();
        System.out.println("Checking if Its AVL Balanced Tree : ");
        System.out.println(p2_bst1.isAVL(p2_bst1.getRoot()));
        System.out.println();

        // -----------------------------------------------------//

        int[] p2_arr2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        BinarySearchTree<Integer> p2_bst2 = new BinarySearchTree<Integer>();

        for (int i = 0; i < 10; i++) {
            p2_bst2.add(p2_arr2[i]);
        }

        System.out.println("Binary Search Tree 2 : ");
        System.out.println(p2_bst2.toString());
        System.out.println();
        System.out.println("Checking if Its AVL Balanced Tree : ");
        System.out.println(p2_bst2.isAVL(p2_bst2.getRoot()));
        System.out.println();

        /*
         * A. Checking if a given BST has Properties of RedBlack tree with Root as Red
         */

        int[] p2_arr3 = { 12, 5, 15, 3, 10, 13, 17, 4, 7, 11, 14, 6, 8 };

        BinarySearchTree<Integer> p2_bst3 = new BinarySearchTree<Integer>();

        for (int i = 0; i < 13; i++) {
            p2_bst3.add(p2_arr3[i]);
        }

        System.out.println("Binary Search Tree 3 : ");
        System.out.println(p2_bst3.toString());
        System.out.println();
        System.out.println("Checking if Its a Red Black [Balanced] Tree : ");
        System.out.println(p2_bst3.isRed(p2_bst3.getRoot()));
        System.out.println();

        int[] p2_arr4 = { 10, 5, 100, 50, 40, 150, 32, 1, 160, 170, 200 };

        BinarySearchTree<Integer> p2_bst4 = new BinarySearchTree<Integer>();

        for (int i = 0; i < 11; i++) {
            p2_bst4.add(p2_arr4[i]);
        }

        System.out.println("Binary Search Tree 4 : ");
        System.out.println(p2_bst4.toString());
        System.out.println();
        System.out.println("Checking if Its a Red Black [Balanced] Tree : ");
        System.out.println(p2_bst4.isRed(p2_bst4.getRoot()));
        System.out.println();

        /************************* PART 3 **************************/

        /*
         * Comparing Insertion time of Different Size of Input to the 4 Balanced Trees
         * and SkipList
         *
         * 1. Binary Serach Trees 2. Red-Black Trees 3. 2-3 Trees 4. B Trees 5. Skip
         * List
         *
         * Number Will be Inserting at First - 1. 10000 2. 20000 3. 40000 4.80000
         *
         */

        System.out.println("\n\t\t\t\t/*************** PART 3 ***************/\n\n");

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the number of Integers to Insert : ");
        int numTimes = scan.nextInt();

        System.out.println("\n\n\t\t\t\tNumber to be Inserted are " + numTimes + "\n");

        long[][] stats = new long[5][2];
        int ds = 0;

        /* NumTimes Distinct Integers */
        Set<Integer> set = new LinkedHashSet<Integer>();
        while (set.size() < numTimes) {
            set.add(rand.nextInt(1000000));
        }

        int p3_arr1[] = new int[numTimes];
        int k = 0;
        Iterator<Integer> itr = set.iterator();
        while (itr.hasNext()) {
            p3_arr1[k++] = itr.next();
        }

        /* Extra 100 Distinct Integers */
        Set<Integer> set1 = new LinkedHashSet<Integer>();
        while (set1.size() < 100) {
            // Will be distinct from previous set
            set1.add(rand.nextInt(100000) + 1000000);
        }

        int p3_arr2[] = new int[100];
        k = 0;
        Iterator<Integer> itr2 = set1.iterator();
        while (itr2.hasNext()) {
            p3_arr2[k++] = itr2.next();
        }

        // Binary Search Trees
        int timer = 0;
        while (timer++ < 1) {
            int counter = 0;

            BinarySearchTree<Integer> _dataStruct = new BinarySearchTree<Integer>();

            long startTime = System.nanoTime();

            // adding num times
            for (int i = 0; i < numTimes; i++) {
                _dataStruct.add(p3_arr1[i]);
            }
            long endTime = System.nanoTime();
            stats[ds][counter++] = (endTime - startTime);

            startTime = System.nanoTime();

            // Adding 100
            for (int i = 0; i < 100; i++) {
                _dataStruct.add(p3_arr2[i]);
            }

            endTime = System.nanoTime();

            stats[ds++][counter++] = (endTime - startTime);
        }

        // Red Black Trees

        timer = 0;
        while (timer++ < 1) {
            int counter = 0;

            RedBlackTree<Integer> _dataStruct = new RedBlackTree<Integer>();

            long startTime = System.nanoTime();
            // adding num times
            for (int i = 0; i < numTimes; i++) {
                _dataStruct.add(p3_arr1[i]);
            }
            long endTime = System.nanoTime();
            stats[ds][counter++] = (endTime - startTime);

            startTime = System.nanoTime();

            // Adding 100
            for (int i = 0; i < 100; i++) {
                _dataStruct.add(p3_arr2[i]);
            }

            endTime = System.nanoTime();

            stats[ds++][counter++] = (endTime - startTime);
        }

        // 23 Trees
        timer = 0;
        while (timer++ < 1) {
            int counter = 0;

            _23Tree<Integer> _dataStruct = new _23Tree<Integer>();

            long startTime = System.nanoTime();
            // adding num times
            for (int i = 0; i < numTimes; i++) {
                _dataStruct.add(p3_arr1[i]);
            }
            long endTime = System.nanoTime();
            stats[ds][counter++] = (endTime - startTime);

            startTime = System.nanoTime();

            // Adding 100
            for (int i = 0; i < 100; i++) {
                _dataStruct.add(p3_arr2[i]);
            }

            endTime = System.nanoTime();

            stats[ds++][counter++] = (endTime - startTime);
        }

        // Btrees
        timer = 0;
        while (timer++ < 1) {
            int counter = 0;

            BTree<Integer> _dataStruct = new BTree<Integer>(4);

            long startTime = System.nanoTime();
            // adding num times
            for (int i = 0; i < numTimes; i++) {
                _dataStruct.add(p3_arr1[i]);
            }
            long endTime = System.nanoTime();
            stats[ds][counter++] = (endTime - startTime);

            startTime = System.nanoTime();

            // Adding 100
            for (int i = 0; i < 100; i++) {
                _dataStruct.add(p3_arr2[i]);
            }

            endTime = System.nanoTime();

            stats[ds++][counter++] = (endTime - startTime);
        }

        // SkipList
        timer = 0;
        while (timer++ < 1) {
            int counter = 0;
            SkipList<Integer> _dataStruct = new SkipList<Integer>();

            long startTime = System.nanoTime();
            // adding num times
            for (int i = 0; i < numTimes; i++) {
                _dataStruct.add(p3_arr1[i]);
            }
            long endTime = System.nanoTime();
            stats[ds][counter++] = (endTime - startTime);

            startTime = System.nanoTime();

            // Adding 100
            for (int i = 0; i < 100; i++) {
                _dataStruct.add(p3_arr2[i]);
            }

            endTime = System.nanoTime();

            stats[ds++][counter++] = (endTime - startTime);
        }

        System.out.println("(ns)\tTime\t  Extra Time\n");
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                System.out.print("BST \t");
            } else if (i == 1) {
                System.out.print("RBT \t");
            } else if (i == 2) {
                System.out.print("23T \t");
            } else if (i == 3) {
                System.out.print("BT \t");
            } else if (i == 4) {
                System.out.print("SL \t");
            }
            System.out.println(stats[i][0] + "    " + stats[i][1]);
        }

        System.out.println("\nSimulation Completed !!");
    }
}
