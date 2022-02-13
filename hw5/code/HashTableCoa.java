import java.util.NoSuchElementException;

public class HashTableCoa<K,V> implements KWHashMap<K,V> {

    /** Hash table
     */
    private Entry<K,V> [] hashT;
    /** Number of keys */
    private int numKeys = 0;
    /** Number of deleted keys */
    private int numDel = 0;
    /** Capacity */
    private static int CAPACITY = 101;
    /** Load factor */
    private static final double LOAD_THRESHOLD = 0.75;
    /** DELETED value */
    private final Entry < K, V > DELETED = new Entry < K, V > (null, null);

    public static class Entry<K,V>{
        /** key of entry */
        private K key;
        /** value of entry */
        private V value;
        /** next of entry */
        private  Object next;

        /**
         * Entry class constructor
         * @param key key of entry
         * @param value value of entry
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
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
    public HashTableCoa(){
        this.hashT = new Entry[CAPACITY];
    }

    /**
     * Shows information of hash table elements
     */
    public void show() {
        for (int i = 0; i < hashT.length; i++) {
            Entry t = hashT[i];
            if(t != null)
                System.out.format("Index: %d, Key: %s, Next: %d\n",i, t.key, t.next);

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
        int i = 1;
        Entry t = hashT[index];
        while((t != DELETED) && (t != null) && (!t.key.equals(key))) {
            index = (key.hashCode() + (i * i)) % this.hashT.length;
            if (index >= hashT.length) {
                index = 0;
            }
            t = hashT[index];
            i++;
        }

        if (t != null) {
            return (V)t.getValue();
        }
        else
            return null;
    }

    /**
     * Puts entry in hash table
     * @param key key of entry
     * @param value value of entry
     * @return Old value if existent key's value is changed otherwise null
     */
    @Override
    public V put(K key, V value){

        int index = key.hashCode() % this.hashT.length;
        if (index < 0) {
            index += this.hashT.length;
        }
        int oldIndex = -1, check = -1;
        int i = 1;
        Entry t = hashT[index];
        if(t != null && t.next == null) {
            oldIndex = index;
        }

        while((t != DELETED) && (t != null) && (!t.key.equals(key))) {
            index = (key.hashCode() + (i * i)) % this.hashT.length;
            t = hashT[index];
            if (index >= hashT.length) {
                index = 0;
            }
            if(t != null && oldIndex == -1)
                oldIndex = index;
            i++;
        }
        if(oldIndex != -1)
            hashT[oldIndex].next = index;

        V val = null;
        if(hashT[index] == null){

            hashT[index] = new Entry<K,V>(key, value);
            numKeys++;
            double load_factor = (double) (numKeys+numDel)/hashT.length;
            if(load_factor > LOAD_THRESHOLD){
                rehash();
            }

            return (V) val;
        }
        else{
            val = hashT[index].value;
            hashT[index].value = value;
            return (V) val;
        }
    }

    /**
     * Method to enlarge table size when load factor becomes
     * bigger than load than load threshold
     */
    private void rehash(){
        Entry<K,V> [] ref = hashT;
        hashT = new Entry[2 * ref.length + 1];

        numDel = 0;
        numKeys = 0;
        for(int i=0; i<ref.length; i++){
            if((ref[i] != DELETED) && (ref[i] != null)){
                put(ref[i].getKey(), ref[i].getValue());
            }
        }
    }

    /**
     * Method to delete a key
     * @param key key of entry which is wanted to be deleted
     * @return null if index is empty otherwise value of deleted entry
     */
    public V remove(K key){
        if(key == null)
            return null;
        int index = key.hashCode() % this.hashT.length;
        if (index < 0) {
            index += this.hashT.length;
        }
        Entry t = hashT[index];
        if(hashT[index] == null) {
            return null;
        }
        V oldVal;
        try {
            while ((t != null) && (t != DELETED) && !t.key.equals(key)) {
                index++;
                if (index >= hashT.length) {
                    index = 0;
                }
                t = hashT[index];
            }
        }
        catch (Exception e){
            return null;
        }
        if(t == null){
            return null;
        }
        else if(t != null && t.next != null){
            int nextInd = (int)t.next;
            oldVal = hashT[index].getValue();
            hashT[index]= hashT[nextInd];
            if(nextInd != hashT.length-1){
                for(int i=nextInd; i<numKeys; i++){
                    hashT[i] = hashT[i+1];
                }
            }

            hashT[numKeys] = new Entry<>(null,null);
        }
        else{
            oldVal = hashT[index].getValue();
            hashT[index] = DELETED;
            if(index != hashT.length-1){
                for(int i=index; i<numKeys; i++){
                    hashT[i] = hashT[i+1];
                }
            }
            hashT[numKeys] = new Entry<>(null,null);
        }
        return (V)oldVal;
    }

    /** Returns number of entries
     */
    public int size() {
        return numKeys;
    }

    /** Returns true if empty
     * else false
     */
    public boolean isEmpty(){
        return numKeys == 0;
    }

}
