/**
 * Filename: MyProfiler.java Project: p3b-201901 Authors: TODO: add your name(s) and lecture numbers
 * here
 *
 * Semester: Spring 2019 Course: CS400
 * 
 * Due Date: TODO: add assignment due date and time Version: 1.0
 * 
 * Credits: TODO: name individuals and sources outside of course staff
 * 
 * Bugs: TODO: add any known bugs, or unsolved problems here
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

  HashTableADT<K, V> hashtable;
  TreeMap<K, V> treemap;

  public MyProfiler() {
    // Instantiate your HashTable and Java's TreeMap
    hashtable = new HashTable<K, V>();
    treemap = new TreeMap<K, V>();
  }

  public void insert(K key, V value) {
    // Insert K, V into both data structures
    try {
      hashtable.insert(key, value);
    } catch (IllegalNullKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (DuplicateKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    treemap.put(key, value);

  }

  public void retrieve(K key) {
    // get value V for key K from data structures
    try {
      hashtable.get(key);
    } catch (IllegalNullKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (KeyNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    treemap.get(key);
  }

  public void remove(K key) {
    try {
      hashtable.remove(key);
    } catch (IllegalNullKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    treemap.remove(key);
  }

  public static void main(String[] args) {
    try {
      int numElements = Integer.parseInt(args[0]);
//      int numElements = 7000000;
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
//      for (int i = 0; i < numElements; i++) {
//        key = Integer.toString(i);
//        mp.remove(key);
//      }

      String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
      System.out.println(msg);
    } catch (Exception e) {
      System.out.println("Usage: java MyProfiler <number_of_elements>");
      System.exit(1);
    }
  }
}
