import java.util.HashMap;
import java.util.Iterator;

public class MapIterator<K,V> extends HashMap<K,V> implements Iterable<K> {

    private K key;
    public class MapIter implements Iterator<K> {
        /**
         * Index of element
         */
        private int index;
        /**
         * Array to store keys
         */
        private Object [] mArr;
        /**
         * Size of hashmap
         */
        private int size;

        /**
         * No parameter constructor
         */
        MapIter() {
            if(MapIterator.this.key == null) {
                this.mArr = (K []) MapIterator.this.keySet().toArray();
                this.size = MapIterator.this.size();
                this.index = 0;
            }
            else{
                this.mArr = (K []) MapIterator.this.keySet().toArray();
                this.size = MapIterator.this.size();
                for(int i=0; i<this.size; i++){
                    if(mArr[i].equals(MapIterator.this.key)){
                        this.index = i;
                        break;
                    }
                }
            }
        }

        /**
         * Returns whether there is next or not
         * @return boolean
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * Returns next key, it returns the first key when there is no
         * not-iterated key in the Map
         * @return next key
         */
        @Override
        public K next() {
            if(!hasNext()){
                this.index = 0;
                return (K)this.mArr[index++];
            }
            return (K)this.mArr[index++];
        }

        /**
         * Returns previous key, it returns the last key when the
         * iterator is at the first key
         * @return previous key
         */
        public K prev(){
            if(index <= 0){
                index = this.size-1;
                return (K)mArr[this.size-1];
            }
            return (K)mArr[--index];
        }

    }

    /** No parameter constructor */
    MapIterator(){}

    /** Constructor with key */
    MapIterator(K key){
        this.key = key;
    }

    /**
     * Map iterator method
     * @return map iterator
     */
    public MapIter iterator(){
        return new MapIter();
    }
}