/**
 * Hash map for file system
 * 
 */

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
   public static final double DEFAULT_LOAD_FACTOR = 0.75;
   public static final int DEFAULT_INITIAL_CAPACITY = 16;
   public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
   public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
   public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

   private double loadFactor;
   private int capacity;
   private int size;

   // array of lists of hashMap entries
   // Use this instance variable for Separate Chaining conflict resolution
   private List<HashMapEntry<K, V>>[] buckets;

   // Use this instance variable for Linear Probing
//	private HashMapEntry<K, V>[] entries; 	

   public MyHashMap() {
      this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
   }

   /**
    * Constructs a HashMap with the provided capacity and loadFactor
    * 
    * @param initialCapacity the initial capacity of this MyHashMap
    * @param loadFactor      the load factor for rehashing this MyHashMap
    * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
    *                                  positive
    */
   @SuppressWarnings("unchecked")
   public MyHashMap(int initialCapacity, double loadFactor)
         throws IllegalArgumentException {

      if (initialCapacity < 0) {
         throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
      }
      if (loadFactor <= 0) {
         throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
      }

      this.capacity = initialCapacity;
      this.loadFactor = loadFactor;

      // if you use Separate Chaining
      buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];

      // Initializes each list in the HashMap array
      for (int i = 0; i < this.buckets.length; i++) {
         this.buckets[i] = new ArrayList<HashMapEntry<K, V>>();
      }

      // if you use Linear Probing
//		entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
   }

   /**
    * Adds the specified key, value pair to this DefaultMap Note: duplicate keys are not
    * allowed
    * 
    * @param key,   the key value
    * @param value, the value associated with the key
    * @return true if the key value pair was added to this DefaultMap
    * @throws IllegalArgument exception if the key is null
    */
   @Override
   public boolean put(K key, V value) throws IllegalArgumentException {
      if (key == null) {
         throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
      }

      int keyHash = this.getKeyHash(key);

      if (this.containsKey(key)) {
         return false;
      }

      this.buckets[keyHash].add(new HashMapEntry<K, V>(key, value));
      this.size++;
      return true;
   }

   /**
    * Replaces the value that maps to the key if it is present
    * 
    * @param key      The key whose mapped value is being replaced
    * @param newValue The value to replace the existing value with
    * @return true if the key was in this DefaultMap
    * @throws IllegalArgument exception if the key is null
    */
   @Override
   public boolean replace(K key, V newValue) throws IllegalArgumentException {
      if (key == null) {
         throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
      }

      if (!this.containsKey(key)) {
         return false;
      }

      int keyHash = this.getKeyHash(key);

      // find the key-value set and updates its value
      for (HashMapEntry<K, V> entry : this.buckets[keyHash]) {
         if (entry.getKey().equals(key)) {
            entry.setValue(newValue);
            return true;
         }
      }

      return false;
   }

   /**
    * Remove the entry corresponding to the given key
    * 
    * @param the key of the entry that will be removed
    * @return true if an entry for the given key was removed
    * @throws IllegalArgument exception if the key is null
    */
   @Override
   public boolean remove(K key) throws IllegalArgumentException {
      if (key == null) {
         throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
      }

      if (!this.containsKey(key)) {
         return false;
      }

      int keyHash = this.getKeyHash(key);

      // finds the key-value set and removes it
      for (HashMapEntry<K, V> entry : this.buckets[keyHash]) {
         if (entry.getKey().equals(key)) {
            this.buckets[keyHash].remove(entry);
            this.size--;
            return true;
         }
      }

      return false;
   }

   /**
    * Adds the key, value pair to this DefaultMap if it is not present, otherwise,
    * replaces the value with the given value
    * 
    * @param The key that will be added or updated
    * @param The value associated with the key
    * @throws IllegalArgument exception if the key is null
    */
   @Override
   public void set(K key, V value) throws IllegalArgumentException {
      if (key == null) {
         throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
      }

      if (!this.put(key, value)) {
         this.replace(key, value);
      }
   }

   /**
    * Returns the value associated to the specified key
    * 
    * @param The key whose value will be returned
    * @return the value corresponding to the specified key, null if key doesn't exist in
    *         hash map
    * @throws IllegalArgument exception if the key is null
    */
   @Override
   public V get(K key) throws IllegalArgumentException {
      if (key == null) {
         throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
      }

      if (!this.containsKey(key)) {
         return null;
      }

      int keyHash = this.getKeyHash(key);

      // searches for the key
      for (HashMapEntry<K, V> entry : this.buckets[keyHash]) {
         if (entry.getKey().equals(key)) {
            return entry.getValue();
         }
      }

      return null;
   }

   /**
    * @return The number of (key, value) pairs in this DefaultMap
    */
   @Override
   public int size() {
      return this.size;
   }

   /**
    * Returns if this map is empty
    * 
    * @return true iff this.size() == 0 is true
    */
   @Override
   public boolean isEmpty() {
      if (this.size == 0) {
         return true;
      }
      return false;
   }

   /**
    * Returns boolean indicating if a key exists in this map
    * 
    * @param The key to be searched
    * @return true if the specified key is in this DefaultMap
    * @throws IllegalArgument exception if the key is null
    */
   @Override
   public boolean containsKey(K key) throws IllegalArgumentException {
      if (key == null) {
         throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
      }

      if (this.isEmpty()) {
         return false;
      }

      int keyHash = this.getKeyHash(key);

      // searches for the key in the map
      for (HashMapEntry<K, V> entry : this.buckets[keyHash]) {
         if (entry.getKey().equals(key)) {
            return true;
         }
      }

      return false;
   }

   /**
    * Returns an array with the keys on the map
    * 
    * @return an array containing the keys of this DefaultMap. If this DefaultMap is
    *         empty, returns array of length zero.
    */
   @Override
   public List<K> keys() {
      List<K> keys = new ArrayList<K>(0);

      for (int i = 0; i < this.buckets.length; i++) {
         for (HashMapEntry<K, V> e : this.buckets[i]) {
            keys.add(e.getKey());
         }
      }

      return keys;
   }

   private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {

      K key;
      V value;

      private HashMapEntry(K key, V value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public K getKey() {
         return key;
      }

      @Override
      public V getValue() {
         return value;
      }

      @Override
      public void setValue(V value) {
         this.value = value;
      }
   }

   /**
    * Generates a int hash value for the key provided. The hash is positive and modulo
    * this.capacity
    * 
    * @param key
    * @return the hash value
    */
   private int getKeyHash(K key) {
      int keyHash = Objects.hashCode(key);
      keyHash %= this.capacity;
      if (keyHash < 0) {
         keyHash *= -1;
      }
      return keyHash;
   }
}
