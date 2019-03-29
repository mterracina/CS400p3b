///////////////////////////////////////////////////////////////////////////////
//
// Title:           MyProfiler
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
//  none
// 
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

// Used as the data structure to test our hash table against
import java.util.TreeMap;

/**
 * This class will profile to determine relative performance between the HashTable and Java's 
 * TreeMap structures. It accepts a String argument of a number which represents the number 
 * of inserts, retrieves, and deletes to perform within both classes. 
 * 
 * @author mterracina
 */
public class MyProfiler<K extends Comparable<K>, V> {

  HashTableADT<K, V> hashtable;
  TreeMap<K, V> treemap;

  /**
   * Constructor that instantiates the HashTable and TreeMap Classes
   * 
   */
  public MyProfiler() {
    hashtable = new HashTable<K, V>();
    treemap = new TreeMap<K, V>();
  }

  /**
   * Inserts a key, value pair into both the HashTable and TreeMap
   * 
   * @param key - the key within the key, value pair
   * @param value - the value within the key, value pair
   */
  public void insert(K key, V value) {
    // Insert K, V into both data structures
    try {
      hashtable.insert(key, value);
    } catch (IllegalNullKeyException e) {
      e.printStackTrace();
    } catch (DuplicateKeyException e) {
      e.printStackTrace();
    }
    treemap.put(key, value);

  }

  /**
   * Gets the value of a given key from both the HashTable and TreeMap
   * 
   * @param key - the key within the key, value pair
   */
  public void retrieve(K key) {
    try {
      hashtable.get(key);
    } catch (IllegalNullKeyException e) {
      e.printStackTrace();
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    }
    treemap.get(key);
  }

  /**
   * Removes the key, value pair from both the HashTable and TreeMap
   * 
   * @param key - the key within the key, value pair to remove
   */
  public void remove(K key) {
    try {
      hashtable.remove(key);
    } catch (IllegalNullKeyException e) {
      e.printStackTrace();
    }
    treemap.remove(key);
  }

  /**
   * Main driver method. Accepts String argument representing the number of elements to insert,
   * get, and remove from the HashTable and TreeMap classes
   * 
   * @param args - the number of elements to insert, get, and remove
   */
  public static void main(String[] args) {
    try {
      int numElements = Integer.parseInt(args[0]);
      MyProfiler<Integer, Integer> mp = new MyProfiler<Integer, Integer>();
      Integer key, value;

      // execute the insert method of profile as many times as numElements
      for (int i = 0; i < numElements; i++) {
        key = i;
        value = 10 + key;
        mp.insert(key, value);
      }

      // execute the retrieve method of profile as many times as numElements
      for (int i = 0; i < numElements; i++) {
        key = i;
        mp.retrieve(key);
      }

      // execute the remove method of profile as many times as numElements
      for (int i = 0; i < numElements; i++) {
        key = i;
        mp.remove(key);
      }

      String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
      System.out.println(msg);
    } catch (Exception e) {
      System.out.println("Usage: java MyProfiler <number_of_elements>");
      System.exit(1);
    }
  }
}
