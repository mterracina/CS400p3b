///////////////////////////////////////////////////////////////////////////////
//
// Title:           HashTable
// Course:          CS400 Spring 2019
// Project:         Project 3b
//
// Author:          Mikel Terracina
// Email:           mterracina@wisc.edu
//
// Lecturer's Name: Andrew Kuemmel
// Lecture Number:  004
//
// Due Date:        2019-03-28
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
// Online Sources: 
//  https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java 
//  https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/
//  https://www.youtube.com/playlist?list=PLpPXw4zFa0uKKhaSz87IowJnOTzh9tiBk
// 
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.ArrayList;

/**
 * This class represents a Hash Table. It is comprised of an array of ArrayLists, using chaining as
 * the collision resolution. The hash algorithm is calculated using the positive integer returned
 * from Java's hashCode() method (on the key value <K>), then using the modulus of the returned
 * HashCode() value over the size of the HashTable. The default load factor maximum is 0.75. Once
 * the maximum load factor is reached the HashTable will be resized to (2 * the HashTable size + 1)
 * and the elements will be rehashed.
 * 
 * @author mterracina
 *
 * @param <K> represents Key within the key-value pair
 * @param <V> represents Value within the key-value pair
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  // represents an individual node/element within the HashTable
  protected class HashElement<K extends Comparable<K>, V> {
    K key;
    V value;

    // Constructor
    protected HashElement(K key, V value) {
      this.key = key;
      this.value = value;
    }

  } // end inner class

  /// ****************************** FIELDS *********************************************///
  private int tableSize;
  private double loadFactorThreshold; // aim to keep less than or equal to .75
  private int numKeys; // number of Keys <K> within the HashTable
  private ArrayList<HashElement<K, V>>[] hArray; // array of ArrayLists to store HashElements

  /// *************************** CONSTRUCTORS ******************************************///
  /**
   * Call the HashTable(int, double) constructor, setting the initial capacity to 5 and the
   * loadFactorThreshold to 0.75
   */
  public HashTable() {
    this(17, 0.75);
  }

  /**
   * Constructor that accepts initial capacity and load factor threshold
   * 
   * @param initialCapacity - initial size to set the HashTable to
   * @param loadFactorThreshold - the load factor that causes a resize and rehash
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    this.tableSize = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    this.numKeys = 0;

    // create an array of ArrayList having initial capacity of passed value
    hArray = (ArrayList<HashElement<K, V>>[]) new ArrayList[initialCapacity];

    // initialize all the available elements to an empty array list
    for (int i = 0; i < tableSize; i++) {
      hArray[i] = new ArrayList<HashElement<K, V>>();
    }
  }

  /// ********************** METHODS ************************************************///
  /**
   * Adds the key,value pair to the HashTable and increase the number of keys
   * 
   * @param key - the key within the key, value pair
   * @param value - the value within the key, value pair
   * @throws IllegalNullKeyException if passed key is null
   * @throws DuplicateKeyException if key is already in the HashTable
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // create a new node with passed key, value variables
    HashElement<K, V> he = new HashElement(key, value);

    // get the index to place the new element at
    int index = getHashIndex(key, tableSize);

    // add the new HashElement
    for (int i = 0; i < hArray[index].size(); i++) {
      if (key.compareTo(hArray[index].get(i).key) == 0) {
        throw new DuplicateKeyException();
      }
    }
    hArray[index].add(he);

    // resize the arrayList if we reach the load factor threshold
    if (getLoadFactor() > loadFactorThreshold) {
      hArray = resize(hArray);
    }
    numKeys++;
  }

  /**
   * Removes the HashElement from the HashTable and decreases the number of keys
   * 
   * @param key - the key of the HashElement to remove
   * @return true if the HashElement is removed, otherwise false
   * @throws IllegalNullKeyException if passed key is null
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // apply hash function to find index for given key
    int index = getHashIndex(key, tableSize);

    for (int j = 0; j < hArray[index].size(); j++) {
      if (hArray[index].get(j).key.compareTo(key) == 0) {
        hArray[index].remove(j);
        numKeys--;
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the value associated with the specified key
   * 
   * @param key - the key of the HashElement value to get
   * @return <V> - value of the specified key
   * @throws IllegalNullKeyException if passed key is null
   * @throws KeyNotFoundException if the passed key is not found
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // apply hash function to find index for given key
    int index = getHashIndex(key, tableSize);

    for (int j = 0; j < hArray[index].size(); j++) {
      if (hArray[index].get(j).key.compareTo(key) == 0) {
        return hArray[index].get(j).value;
      }
    }
    throw new KeyNotFoundException();
  }

  /**
   * Returns the number of key,value pairs in the data structure
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  /**
   * Returns the load factor threshold that was passed into the constructor when creating the
   * instance of the HashTable When the current load factor is greater than or equal to the
   * specified load factor threshold, the table is resized and elements are rehashed
   * 
   * @return loadFactorThreshold initialized in the constructor
   */
  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Returns the current load factor for this hash table load factor = number of items / current
   * table size
   * 
   * @return load factor of the HashTable
   */
  @Override
  public double getLoadFactor() {
    double loadFactor = (double) numKeys / tableSize;
    return loadFactor;
  }

  /**
   * Return the current Capacity (table size) of the hash table array. Once increased, the capacity
   * never decreases
   * 
   * @return current capacity (i.e. size) of the hash table
   */
  @Override
  public int getCapacity() {
    return tableSize;
  }

  /**
   * Returns the collision resolution scheme used for this hash table. Schemes include:
   * 
   * 1 OPEN ADDRESSING: linear probe 2 OPEN ADDRESSING: quadratic probe 3 OPEN ADDRESSING: double
   * hashing 4 CHAINED BUCKET: array of arrays 5 CHAINED BUCKET: array of linked nodes 6 CHAINED
   * BUCKET: array of search trees 7 CHAINED BUCKET: linked nodes of arrays 8 CHAINED BUCKET: linked
   * nodes of linked node 9 CHAINED BUCKET: linked nodes of search trees
   * 
   * @return collision resolution scheme used for this hash table
   */
  @Override
  public int getCollisionResolution() {
    return 5; // actually using an array of ArrayLists
  }

  /**
   * Returns a new array reference to to an array that is 2x + 1 the size of the passed array, with
   * all original elements copied to the new array with proper updated indexing
   * 
   * @param hashArray - reference to original array to be copied and resized
   * @return reference to new array
   */
  private ArrayList<HashElement<K, V>>[] resize(ArrayList<HashElement<K, V>>[] hashArray) {
    int newSize = (2 * tableSize + 1);
    ArrayList<HashElement<K, V>>[] newArray =
        (ArrayList<HashElement<K, V>>[]) new ArrayList[newSize];

    // move all the non-null elements of the original array to the new array, recomputing hashIndex
    for (int i = 0; i < newSize; i++) {
      newArray[i] = new ArrayList<HashElement<K, V>>(); // populate new array with null values, just
                                                        // as we did in constructor
    }

    // move the elements to the new array with new hashVal and indexing
    for (int i = 0; i < tableSize; i++) {
      if (hArray[i] != null) {
        for (int j = 0; j < hArray[i].size(); j++) {
          K key = hArray[i].get(j).key;
          V value = hArray[i].get(j).value;
          HashElement<K, V> he = new HashElement(key, value);

          // get the index to place the element at in the resized array
          int index = getHashIndex(key, newSize);

          // add the hash HashElement to the new array
          newArray[index].add(he);
        }
      }
    }
    tableSize = newSize;
    return newArray;
  }

  /**
   * Returns true of false depending on whether or not a key is found in the HashTable
   * 
   * @param key - key to search for
   * @return true if key is found in the HashTable, otherwise false
   */
  protected boolean contains(K key) {
    // apply hash function to find index for given key
    int index = getHashIndex(key, tableSize);

    for (int j = 0; j < hArray[index].size(); j++) {
      if (hArray[index].get(j).key.compareTo(key) == 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the index of where to place the passed key within the HashTable
   * 
   * @param key - key to determine where to place within the HashTable
   * @param tableSize - size of the HashTable array
   * @return index of where to place the key
   */
  private int getHashIndex(K key, int tableSize) {
    // call Java's hashCode method
    int hashVal = key.hashCode();

    // use shift mask to ensure positive integer
    hashVal = hashVal & 0x7FFFFFFF;

    // get the modulus of hashVal / tableSize for the index to place the new element
    int index = hashVal % tableSize;

    return index;
  }

  /**
   * Prints out the contents of the HashTable
   */
  private void printHashTable() {
    for (int i = 0; i < tableSize; i++) {
      System.out.println("array pos " + i);
      for (int j = 0; j < hArray[i].size(); j++) {
        Object k = hArray[i].get(j).key;
        Object v = hArray[i].get(j).value;
        String strK = k.toString();
        String strV = v.toString();
        System.out.println("pos: " + i + "," + j + ": " + strK + ", " + strV);
      }
    }
  }

} // end HashTable class
