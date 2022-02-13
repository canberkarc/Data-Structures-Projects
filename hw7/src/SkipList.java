import java.util.*;

@SuppressWarnings("unchecked")
public class SkipList<E extends Comparable<E>> implements Iterable<E> {

    private static class SLNode<E> {
        SLNode<E>[] links;
        E data;

        public SLNode(int m, E data) {
            links = (SLNode<E>[]) new SLNode[m];
            this.data = data;
        }
    }

    /**
    * Starting of the List 
    */
    private SLNode<E> head;

    /**
    * Maximum level of Node 
    */
    private int maxLevel;

    /** 
    * Max Capacity of list .... Resizable 
    */
    private int maxCap;

    /**
    * Current Size f the List 
    */
    private int size = 0;

    private static final double LOG2 = Math.log(2.0);

    /** Constructor
    */
    public SkipList() {
        this.maxLevel = 4;
        this.maxCap = (int) (Math.pow(2, this.maxLevel) - 1);
        head = new SLNode<E>(this.maxLevel, null);
    }

    /**
     * Returns an iterator over elements.
     * @return an Iterator.
     */
    public Iterator<E> iterator() {
        return new SLIterator();
    }

     /**
     * Returns an descending iterator over elements.
     * @return an Iterator.
     */
    public Iterator<E> descIterator() {
        return new SLDescIterator();
    }

    /**
    * Returns size
    * @return size
    */
    public int size() {
        return size;
    }

    /**
     * Method to generate a logarithmic distributed integer between 1 and maxLevel.
     * i.e., 1/2 of the values returned are 1, 1/4 are 2, 1/8 are 3, etc.
     * 
     * @return a random logarithmic distributed int between 1 and maxLevel
     */
    private int logRandom() {
        Random rand = new Random();
        int r = rand.nextInt(maxCap);
        int k = (int) (Math.log(r + 1) / LOG2);
        if (k > maxLevel - 1) {
            k = maxLevel - 1;
        }
        return maxLevel - k;
    }

    /** Searches target
    */
    private SLNode<E>[] search(E target) {
        SLNode<E>[] predecessor = (SLNode<E>[]) new SLNode[maxLevel];
        SLNode<E> current = head;
        for (int i = current.links.length - 1; i >= 0; i--) {
            while (current.links[i] != null && current.links[i].data.compareTo(target) < 0) {
                current = current.links[i];
            }
            predecessor[i] = current;
        }
        return predecessor;
    }

    /**
    * Method to find
    * 
    */
    public E find(E target) {
        SLNode<E>[] predecessor = search(target);
        if (predecessor[0].links[0] != null && predecessor[0].links[0].data.compareTo(target) == 0) {
            return predecessor[0].links[0].data;
        } else {
            return null;
        }
    }

    /** Method to remove
    */
    public E remove(E target) {
        SLNode<E>[] predecessor = search(target);
        if (predecessor[0].links[0] != null && predecessor[0].links[0].data.compareTo(target) == 0) {
            E returnVal = predecessor[0].links[0].data;
            int level = predecessor[0].links[0].links.length;
            for (int i = 0; i < level; i++) {
                predecessor[i].links[i] = predecessor[i].links[i].links[i];
            }
            size--;
            return returnVal;
        } else {
            return null;
        }
    }

    /**
    * Method to add item
    */
    public boolean add(E item) {

        if (find(item) != null) {
            return false;
        }

        int level = logRandom();
        SLNode<E>[] predecessor = search(item);
        SLNode<E> newNode = new SLNode<>(level, item);
        for (int i = 0; i < level; i++) {
            newNode.links[i] = predecessor[i].links[i];
            predecessor[i].links[i] = newNode;
        }
        size++;
        if (size > maxCap) {
            maxLevel++;
            maxCap = (int) (Math.pow(2, maxLevel) - 1);
            head.links = Arrays.copyOf(head.links, maxLevel);
        }

        return true;
    }

    /** contains method
    * @return boolean value that found or not
    */
    public boolean contains(E target) {
        return find(target) != null;
    }

    /** toString method
    */
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<E> iterator = new SLIterator();
        int count = 0;
        if (size == 0) {
            return "List is Empty..!!";
        }

        s.append("[");
        while (iterator.hasNext()) {
            if (count == size - 1) {
                s.append(iterator.next()).append("]");
            } else {
                s.append(iterator.next()).append(", ");
            }
            count++;
        }
        return s.toString();
    }

    /** Displays each element at all the heights till the maximum level 
    * of the skip list
    */
    public String topLinksView() {
        SLNode<E> current;
        StringBuilder s = new StringBuilder();
        int level = maxLevel - 1;

        while (level >= 0) {
            current = head;
            while (current.links[level] != null) {
                s.append((current = current.links[level]).data).append(" ");
            }
            s.append("\n");
            level--;
        }
        return s.toString();
    }

    private class SLIterator implements Iterator<E> {
        private SLNode<E> current = head;

        /**
         * Returns {@code true} if the iteration has more elements. (In other words,
         * returns {@code true} if {@link #next} would return an element rather than
         * throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current.links[0] != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (current = current.links[0]).data;
        }
    }

    private class SLDescIterator implements Iterator<E> {

        /** current
        */
        private SLNode<E> current = head;
        Stack<SLNode<E>> st;

        /** constructor
        */
        public SLDescIterator() {
            st = new Stack<SLNode<E>>();
            System.out.println();
            while (current != null) {
                st.push(current);
                current = current.links[0];
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements. (In other words,
         * returns {@code true} if {@link #next} would return an element rather than
         * throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return (st.size() > 1);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            SLNode<E> curr = st.pop();
            return curr.data;
        }
    }
}