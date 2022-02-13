import java.util.Iterator;
import java.util.LinkedList;

public class HashTableLL<K, V> implements KWHashMap<K, V> {

    /** Hash table
     */
    private LinkedList<Entry<K, V>>[] hashT;
    /** Number of keys */
    private int numKeys = 0;
    /** Capacity */
    private static int CAPACITY = 101;
    /** Load factor */
    private static final double LOAD_THRESHOLD = 3.0;

    private static class Entry<K, V> {

        /** key of entry */
        private final K key;
        /** value of entry */
        private V value;

        /**
         * Entry class constructor
         * @param key key of entry
         * @param value value of entry
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Getter of key
         * @return key
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter of value
         * @return
         */
        public V getValue() {
            return value;
        }

        /**
         * Setter of value
         * @param val Value of value
         * @return old value
         */
        public V setvalue(V val) {
            V oldvalue = value;
            value = val;
            return oldvalue;
        }

    }

    /** No parameter constructor */
    public HashTableLL() {
        this.hashT = new LinkedList[CAPACITY];
    }

    /** Method to show a slot's elements
     * @param key Key that is in a slot whose element is wanted to be seen
     */
    public void show(K key){
        int index = key.hashCode() % this.hashT.length;
        if (index < 0) {
            index += this.hashT.length;
        }
        if (this.hashT[index] == null) {
            return;
        }
        Entry t;
        System.out.println("Chained elements: ");
        for(int i=0; i < hashT[index].size(); i++){
            t = hashT[index].get(i);
            System.out.println(t.getKey());
        }
    }

    /**
     * Method to get key's value
     * @param key Key whose value is wanted
     * @return value of key
     */
    @Override
    public V get(K key) {
        int index = key.hashCode() % this.hashT.length;
        if (index < 0) {
            index += this.hashT.length;
        }
        if (this.hashT[index] == null) {
            return null;
        }
        Entry t;
        for(int i = 0; i < hashT[index].size(); i++) {
            t = hashT[index].get(i);
            if (t.getKey().equals(key)) {
                return (V) t.getValue();
            }
        }
        return null;
    }

    /**
     * Method to enlarge table size when load factor becomes
     * bigger than load than load threshold
     */
    public void rehash() {
        this.numKeys = 0;
        LinkedList<Entry<K, V>>[] oldTable;
        oldTable = this.hashT;
        this.CAPACITY = (this.CAPACITY * 2) + 1;
        this.hashT = new LinkedList[this.CAPACITY];
        Entry<K, V> t;
        for(int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                for(int j = 0; j < oldTable[i].size(); j++) {
                    t = oldTable[i].get(j);
                    this.put(t.getKey(), t.getValue());
                }
            }
        }
    }

    /**
     * Method to put entry in hash table, if they have same index then
     * I put them in linked list on same slot
     * @param key key of entry
     * @param value value of entry
     * @return Old value if existent key's value is changed otherwise null
     */
    @Override
    public V put(K key, V value) {
        int index = key.hashCode() % this.hashT.length;
        if (index < 0) {
            index += this.hashT.length;
        }
        V val = null;
        if (this.hashT[index] == null) {
            LinkedList ll = new LinkedList();
            ll.add(new Entry(key, value));
            this.hashT[index] = ll;
            this.numKeys = this.numKeys + 1;

            /* If load is more than load threshold then rehash */
            if ((this.numKeys) > (this.LOAD_THRESHOLD * this.CAPACITY)) {
                rehash();
            }
            return (V) val;
        }
        else {
            Entry t;
            for(int i = 0; i < hashT[index].size(); i++) {
                t = this.hashT[index].get(i);
                if (t.getKey().equals(key)) {
                    val = (V)t.getValue();
                    t.setvalue(value);
                    return val;
                }
            }
            this.hashT[index].add(new Entry(key, value));

            // if load is more than threshold rehash into a new table with more capacity
            this.numKeys += 1;

            if ((this.numKeys) > (this.LOAD_THRESHOLD * this.hashT.length)) {
                rehash();
            }
            return value;
        }
    }

    /**
     * Method to delete an entry
     * @param key key of entry which is wanted to be deleted
     * @return null if index is empty otherwise value of deleted entry
     */
    public V remove(K key){
        int index = key.hashCode() % this.hashT.length;
        if (index < 0) {
            index += this.hashT.length;
        }
        V val = null;
        if (this.hashT[index] == null) {
           return null;
        }
        for(int i=0; i<hashT[index].size(); i++){
            if(hashT[index].get(i).getKey().equals(key)){
                val = hashT[index].get(i).getValue();
                hashT[index].remove(i);
                numKeys--;
                return val;
            }
        }
        return null;
    }

    /**
     * Method to find size of table
     * @return size
     */
    public int size() {
        int size = 0;
        Iterator<Entry<K, V>> iter;
        Entry<K, V> t;
        LinkedList<Entry<K, V>> ll;
        for(int i=0; i<this.hashT.length; i++){
            ll = hashT[i];
            if (ll != null) {
              iter = ll.listIterator(0);
              while (iter.hasNext()) {
                  t = iter.next();
                  if (t.key != null) {
                      size++;
                  }
              }
            }
        }
        return size;
    }

    /**
     * Method to check whether table is empty or not
     * @return true if empty, false if not empty
     */
    public boolean isEmpty() {
        return (this.size() < 1);
    }

}