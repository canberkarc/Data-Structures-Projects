import java.util.Iterator;
import java.util.TreeSet;

public class HashTableTree<K extends Comparable<K>,V extends Comparable<V>> implements KWHashMap<K,V> {

    /** Hash table
     */
    private TreeSet<Entry<K,V>>[] hashT;
    /** Number of keys */
    private int numKeys = 0;
    /** Capacity*/
    private static int CAPACITY = 101;
    /** Load factor */
    private static final double LOAD_THRESHOLD = 3.0;

    private static class Entry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Entry<K,V>>{
        /** key of entry */
        private K key;
        /** value of entry */
        private V value;

        /**
         * Entry class constructor
         * @param key key of entry
         * @param value value of entry
         */
        public Entry(K key, V value){
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

        /**
         * compareTo method to compare keys
         * @param en entry whose key will be compared
         * @return result
         */
        @Override
        public int compareTo(Entry<K,V> en) {
            return this.key.compareTo(en.key);
        }

        /**
         * Method to compare keys
         * @param o object
         * @return true if equal, else false
         */
        @Override
        public boolean equals(Object o){
            Entry t = (Entry) o;
            if(this.key.equals(t.key))
                return true;
            return false;
        }

        /**
         * Overridden hashCode method
         * @return hash code of key
         */
        @Override
        public int hashCode(){
            int res = 17;
            res = 31*res + key.hashCode();
            return res;
        }

    }

    /**
     * No parameter constructor
     */
    public HashTableTree(){
        this.hashT = new TreeSet[CAPACITY];
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
        Iterator<Entry<K, V>> iterator = hashT[index].iterator();
        Entry t;
        while (iterator.hasNext()){
            t = iterator.next();
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
        Iterator<Entry<K, V>> iterator = hashT[index].iterator();
        Entry t;
        while (iterator.hasNext()){
            t = iterator.next();
            if (t.key.equals(key)) {
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
        TreeSet<Entry<K, V>>[] oldTable;
        oldTable = this.hashT;
        this.CAPACITY = (this.CAPACITY * 2) + 1;
        this.hashT = new TreeSet[this.CAPACITY];
        Entry<K, V> t;
        for(int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                Iterator<Entry<K, V>> iterator = hashT[i].iterator();
                while (iterator.hasNext()){
                    t = iterator.next();
                    this.put(t.getKey(), t.getValue());
                }
            }
        }
    }

    /**
     * Method to put entry in hash table, if they have same index then
     * I put them in tree on same slot
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
            TreeSet ts = new TreeSet();
            ts.add(new Entry(key, value));
            this.hashT[index] = ts;
            this.numKeys = this.numKeys + 1;

            // if load is more than threshold rehash into a new table with more capacity
            if ((this.numKeys) > (this.LOAD_THRESHOLD * this.CAPACITY)) {
                rehash();
            }
            return (V) val;
        } else {
            Entry t;
            Iterator<Entry<K, V>> iterator = hashT[index].iterator();
            //for(int i = 0; i < hashT[index].size(); i++) {
            while (iterator.hasNext()) {
                t = iterator.next();
                if (t.getKey().equals(key)) {
                    val = (V)t.getValue();
                    t.setvalue(value);
                    return val;
                }
            }
            this.hashT[index].add(new Entry(key, value));

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
        Iterator<Entry<K, V>> iter = hashT[index].iterator();
        Entry t;
        while(iter.hasNext()){
            t = iter.next();
            if(t.getKey().equals(key)){
                val = (V)t.getValue();
                hashT[index].remove(t);
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
        int count = 0;
        Iterator<Entry<K, V>> iter;
        Entry<K, V> t;
        for (int i=0; i<this.hashT.length; i++) {
            TreeSet<Entry<K, V>> ts = hashT[i];
            if (ts != null) {
                iter = ts.iterator();
                while (iter.hasNext()) {
                    t = iter.next();
                    if (t.key != null) {
                        count = count + 1;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Method to check whether table is empty or not
     * @return true if empty, false if not empty
     */
    public boolean isEmpty() {
        return (this.size() < 1);
    }
}
