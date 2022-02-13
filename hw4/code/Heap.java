import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Arrays; /*Just used when resizing array*/

public class Heap<T extends Comparable<T>> implements Iterable<T> {

    private T[] heap;
    private int MAX_CAPACITY;
    private int heap_size;

    @SuppressWarnings("unchecked")

    /**Constructor
	* @param maximum_size maximum size
    */
    public Heap(int maximum_size) {

        this.heap_size = 0;
        this.MAX_CAPACITY = maximum_size;
        heap = (T[]) new Comparable[this.MAX_CAPACITY + 1];
    }

    /**Function to swap the two elements of heap array 
    * @param idx1 index1
    * @param idx2 index2
    */
    public void swap_elements(int idx1, int idx2) {
        T elem;

        elem = heap[idx1];

        heap[idx1] = heap[idx2];

        heap[idx2] = elem;
    }

    /** Get the Maximum Size of the Heap 
	* @return MAX_CAPACITY maximum capacity
    */
    public int get_max_size() {
        return MAX_CAPACITY;
    }

    /** Get the current size of the heap 
	* @return heap_size size of heap
    */
    public int get_curr_size() {
        return heap_size;
    }

    /** Get Element at Ith Index 
	* @param idx index
	* @throws NoSuchElementException when index is out of bounds
	* @return element
    */
    public T get_ith_element(int idx) {

        if (idx < 1 || idx > heap_size) {
            throw new NoSuchElementException("Index Out of Bounds");
        }
        return heap[idx];
    }

    /** Resize the heap with new size provided 
	* @param new_max_size new max size
    */
    public void resize_heap(int new_max_size) {
        this.MAX_CAPACITY = new_max_size;
        heap = Arrays.copyOf(heap, this.MAX_CAPACITY);
        return;
    }

    /** Heapify the heap to bottom carries the smaller element to bottom 
	* @param curr_idx current index
    */

    public void heapify(int curr_idx) {
        if ((curr_idx <= heap_size) && (curr_idx > heap_size / 2))
            return;

        if ((heap[curr_idx].compareTo(heap[2 * curr_idx]) < 0)
                || (heap[curr_idx].compareTo(heap[2 * curr_idx + 1]) < 0)) {

            if (heap[2 * curr_idx].compareTo(heap[2 * curr_idx + 1]) <= 0) {
                swap_elements(curr_idx, (2 * curr_idx + 1));
                heapify(2 * curr_idx + 1);
            } else {
                swap_elements(curr_idx, 2 * curr_idx);
                heapify(2 * curr_idx);
            }
        }
    }

    /** Search an element in the heap 
	* @param element element to be searched
	* @return found boolean value
    */
    public boolean search_element(T element) {
        boolean found = false;
        for (int i = 1; i <= heap_size; i++) {
            if (heap[i].compareTo(element) == 0) {
                return true;
            }
        }
        return found;
    }

    /** Insert an element to the heap 
	* @param element element to be inserted
	* @return boolean according to success
    */
    public boolean insert_to_heap(T element) {

        /* Not present */

        if (heap_size == MAX_CAPACITY) {
            throw new NoSuchElementException("Heap is Full. Resize it to continue Further.");
        }

        if (heap_size == 0) {
            heap_size++;
            heap[heap_size] = element;
            return true;
        }

        heap_size++;
        heap[heap_size] = element;
        // adjust the heap;

        /* Bring the maximum element to top bottom up approach */
        int newly_inserted_idx = heap_size;
        while ((newly_inserted_idx > 1) && (heap[newly_inserted_idx].compareTo(heap[(newly_inserted_idx / 2)])) > 0) {
            swap_elements(newly_inserted_idx, (newly_inserted_idx / 2));
            newly_inserted_idx = (newly_inserted_idx / 2);
        }
        return true;

    }

    /** Print the heap Array 
    */
    public void print() {
        for (int i = 1; i <= heap_size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    /** Get the maximum Element 
    * @return max element
    */
    public T peek_max() {
        if (heap_size < 1) {
            throw new NoSuchElementException("Heap is Empty");
        }

        return heap[1];
    }

    /**
    * Remove the largest element and decrease size and again heapify to bring
    * second max to array
    * @return deleted max element
    */
    public T remove_max_element() {

        if (heap_size == 1) {
            heap_size = 0;
            return heap[1];
        }

        T max_elem = heap[1];
        heap[1] = heap[heap_size];
        heap_size -= 1;

        /* Generate New Max */
        heapify(1);
        return max_elem;
    }

    @SuppressWarnings("unchecked")

    /** Remove Ith maximum element from the heap 
    * @param index
    * @throws NoSuchElementException when heap is empty or index out of bounds
    * @return kth_max removed element
    */
    public T remove_ith_largest(int kth) {

        if (heap_size == 0) {
            throw new NoSuchElementException("Heap is Empty");
        }

        if (kth > heap_size || kth < 1) {
            throw new NoSuchElementException("Index Out of Bounds");
        }
        /* Remove the first i - 1 elements and then re-insert */
        T[] rest_elements = (T[]) new Comparable[this.MAX_CAPACITY + 1];
        int itr = 0;

        T kth_max = null;

        for (int i = 0; i < kth; i++) {
            T removed_element = this.remove_max_element();
            if (i != kth - 1) {
                rest_elements[itr++] = removed_element;
            } else {
                kth_max = removed_element;
            }
        }

        for (int i = 0; i < itr; i++) {
            this.insert_to_heap(rest_elements[i]);
        }

        return kth_max;
    }

    /** Merge the two heaps 
	* @param secondHeap heap to be merged
    */
    public void merge_heaps(Heap<T> secondHeap) {
        this.resize_heap(this.MAX_CAPACITY + secondHeap.get_max_size() + 5);
        for (int i = 1; i <= secondHeap.get_curr_size(); i++) {
            this.insert_to_heap(secondHeap.get_ith_element(i));
        }
    }

    /** To provide user with heap array 
	* @return heap
    */
    public T[] return_heap() {
        return heap;
    }

    /** Heap Iterator class 
    */
    public class HeapIterator implements Iterator<T> {
        int idx = 1;
        /** Tells whether there is next or not
        * @return boolean
        */
        public boolean hasNext() {
            return idx < heap_size;
        }

        /** next method
        * @throws NoSuchElementException when there is no next
        * @return next element
        */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return heap[idx++];
        }

        /** Method to set last value to given parameter
        * @param item new value of last item
        * @return result
        */
        public T setValue(T item) {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = heap[heap_size];
            heap[heap_size] = item;
            int newly_inserted_idx = heap_size;
            while ((newly_inserted_idx > 1) && heap[newly_inserted_idx].compareTo(heap[(newly_inserted_idx / 2)]) > 0) {
                swap_elements(newly_inserted_idx, (newly_inserted_idx / 2));
                newly_inserted_idx = (newly_inserted_idx / 2);
            }
            return result;
        }
    }

    /** Iterator method
    * @return HeapIterator
    */
    @Override
    public HeapIterator iterator() {
        return new HeapIterator();
    }

}