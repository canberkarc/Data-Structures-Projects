interface KWHashMap <K,V>{
	/** Gives value of key
	* @param key key of entry
	* @return value
	*/
    V get(K key);

    /**
	* Return true is hash table is false otherwise false
	* @return boolean
	*/
    boolean isEmpty();

    /**
    * Puts an entry in hash table by using given key and value
    * @param key key of entry
    * @param value value of entry
    * @return Old value if existent key's value is changed otherwise null
    */
    V put(K key, V value);
}
