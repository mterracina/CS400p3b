///////////////////////////////////////////////////////////////////////////////
//
// Title:           HashTableTest
// Course:          CS400 Spring 2019
// Project:         Project 3
//
// Author:          Mikel Terracina
// Email:           mterracina@wisc.edu
//
// Lecturer's Name: Andrew Kuemmel
// Lecture Number:  004
//
// Due Date:        2019-03-14
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
// Online Sources: 
//  none
// 
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*; 
import org.junit.jupiter.api.Assertions;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
 
import java.util.Random;


/**
 * This class performs JUnit tests on the HashTable class
 * 
 * @author mterracina
 *
 */
public class HashTableTest {

  // fields that will be used by multiple tests
  protected HashTableADT<String, String> ht;
  protected HashTableADT<Integer, String> ht2;


  // add code that runs before each test method
  @Before
  public void setUp() throws Exception {
    ht = new HashTable<String, String>();
    ht2 = new HashTable<Integer, String>();
  }

  // add code that runs after each test method
  @After
  public void tearDown() throws Exception {
    ht = null;
    ht2 = null;
  }

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used. REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  /**
   * insert one key,value pair and remove it, then confirm size is 0
   */
  @Test
  public void test002_after_insert_one_remove_one_size_is_0() {
    try {
      ht.insert("02", "Test02");
      ht.remove("02");
      if (ht.numKeys() != 0)
        fail("HashTable should have a size = 0, but size=" + ht.numKeys());
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  /**
   * insert a few key,value pairs such that one of them has the same key as an earlier one. Confirm
   * that a RuntimeException is thrown
   */
  @Test
  public void test003_insert_duplicate_exception_is_thrown() {
    try {
      ht.insert("03", "Test03");
      ht.insert("03a", "Test03a");
      ht.insert("03b", "Test03b");
      ht.insert("03a", "Test03a");
      fail("HashTable should have thrown a duplicate exception, but did not");
    } catch (DuplicateKeyException d) {
      // expected result
    } catch (Exception e) {
      e.printStackTrace();
      e.getMessage();
    }
  }

  /**
   * insert some key,value pairs, then try removing a key that was not inserted. Confirm that a
   * KeyNotFoundException is thrown
   */
  @Test
  public void test004_remove_returns_false_when_key_not_present() {
    try {
      ht.insert("04", "Test04");
      ht.insert("04a", "Test04a");
      ht.insert("04b", "Test04b");
      if (ht.remove("4c"))
        fail("HashTable remove method should have returned false when attempting to remove "
            + "non-existent key, but did not");
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  /**
   * get a key with a null value. Confirm that an IllegalArgumentException is thrown
   */
  @Test
  public void test005_get_null_key() {
    try {
      ht.get(null);
      fail("HashTable should have thrown a an IllegalNullKeyException when getting a null "
          + "key, but did not");
    } catch (IllegalNullKeyException e) {
      // test passed
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert some key,value pairs, then try removing a key that was not inserted. Confirm that a
   * KeyNotFoundException is thrown
   */
  @Test
  public void test006_get_key_that_does_not_exist_throws_KeyNotFoundException() {
    try {
      ht.insert("04", "Test04");
      ht.insert("04a", "Test04a");
      ht.insert("04b", "Test04b");
      ht.get("4c");
      fail("HashTable remove method should have returned false when attempting to remove "
          + "non-existent key, but did not");
    } catch (KeyNotFoundException e) {
      // test passed
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  /**
   * insert 3 key,value pairs and confirm contains() method returns the middle key
   */
  @Test
  public void test007_after_insert_three_contains_equals_mid() {
    try {
      HashTable<String, String> h = new HashTable<String, String>();
      h.insert("01", "Test07_1");
      h.insert("02", "Test07_2");
      h.insert("03", "Test07_3");
      if (h.contains("02")) {
        // test passed
      } else {
        fail("HashTable contains() method should have returned true when passed the 2nd "
            + "inserted value, but did not");
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert 500,000 key value pairs into the data structure and then confirm that size() is 500k
   */
  @Test
  public void test008_insert_500k_size_equals_500k() {
    String key;
    String value;
    int target = 500000;

    try {
      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test008_" + key;
        ht.insert(key, value);
      }
      if (ht.numKeys() != target)
        fail("HashTable should have a size = " + target + ", but size=" + ht.numKeys());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert N key value pairs into the data structure, then remove N-1. Confirm that size() is 1
   */
  @Test
  public void test009_insert_N_remove_N_minus_1_size_equals_1() {
    String key;
    String value;
    int target = 500000;

    try {
      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test009_" + key;
        ht.insert(key, value);
      }

      for (int i = target; i > 1; i--) {
        key = Integer.toString(i);
        value = "Test009_" + key;
        ht.remove(key);
      }

      if (ht.numKeys() != 1)
        fail("HashTable should have a size = 1, but size=" + ht.numKeys());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert N key value pairs into the data structure, then remove N. Confirm that size() is 0
   */
  @Test
  public void test010_insert_N_remove_N_size_equals_0() {
    String key;
    String value;
    int target = 500000;

    try {
      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test010_" + key;
        ht.insert(key, value);
      }

      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test010_" + key;
        ht.remove(key);
      }

      if (ht.numKeys() != 0)
        fail("HashTable should have a size = 0, but size=" + ht.numKeys());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert 25 key value pairs into the data structure, then remove 15. Insert another 10, then
   * remove 5. Confirm that size() is 15
   */
  @Test
  public void test011_insert_25_remove_15_insert_10_remove_5_size_equals_15() {
    String key;
    String value;

    try {
      for (int i = 1; i <= 25; i++) {
        key = Integer.toString(i);
        value = "Test11_" + key;
        ht.insert(key, value);
      }

      // remove keys 5 - 19
      for (int i = 19; i >= 5; i--) {
        key = Integer.toString(i);
        value = "Test11_" + key;
        ht.remove(key);
      }

      // insert keys 5 - 14
      for (int i = 5; i < 15; i++) {
        key = Integer.toString(i);
        value = "Test11_" + key;
        ht.insert(key, value);
      }

      // remove keys 5 - 9
      for (int i = 9; i >= 5; i--) {
        key = Integer.toString(i);
        value = "Test11_" + key;
        ht.remove(key);
      }

      if (ht.numKeys() != 15)
        fail("data structure should have a size = 15, but size=" + ht.numKeys());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert 1 key value pair, then attempt to remove a key with a null value. Confirm that an
   * IllegalNullKeyException is thrown
   */
  @Test
  public void test012_remove_null_key_illegal_null_key_exception_thrown() {
    try {
      ht.insert("01", "Test01");
      ht.remove(null);
      fail("data structure should have thrown an IllegalNullKeyException when attempting "
          + "to remove a null key, but didn't");
    } catch (IllegalNullKeyException e) {
      // passed
      return;
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert 1 key value pair, then remove the key. Confirm that the remove() method returns true
   */
  @Test
  public void test013_insert_remove_returns_true() {
    try {
      ht.insert("01", "Test01");
      if (!ht.remove("01")) {
        fail("data structure should have returned true when removing an existing key, but did not");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  /**
   * insert 500,000 key value pairs into the data structure, then get key "250,000". Confirm that
   * the expected value is returned
   * 
   */
  @Test
  public void test014_insert_500k_get_250k() {
    String key;
    String value;
    int target = 500000;

    // insert the key, value pairs
    try {
      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test014_" + key;
        ht.insert(key, value);
      }

      // confirm key "15" = "Test14_15"
      if (!ht.get("250000").equals("Test014_250000")) {
        fail("data structure should have returned 'Test014_15' getting the value of key 15, "
            + "but did not");
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert 3 key value pairs, remove 1, then attempt to get a key with a null value. Confirm that
   * an IllegalNullKeyException is thrown
   */
  @Test
  public void test015_get_null_key_illegal_null_key_exception_thrown() {
    try {
      ht.insert("01", "Test01");
      ht.insert("02", "Test02");
      ht.insert("03", "Test03");
      ht.remove("02");
      ht.get(null);
      fail("data structure should have thrown an IllegalNullKeyException when attempting to get"
          + "a null key, but didn't");
    } catch (IllegalNullKeyException e) {
      // passed
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * insert N key value pairs into the data structure, then remove N, then insert N again. Confirm
   * that duplicate error is not thrown and that size() is = N
   */
  @Test
  public void test017_insert_N_remove_N_insert_N_size_equals_N() {
    String key;
    String value;
    int target = 500000;

    try {
      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test017_" + key;
        ht.insert(key, value);
      }

      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test017_" + key;
        ht.remove(key);
      }

      for (int i = 1; i <= target; i++) {
        key = Integer.toString(i);
        value = "Test017_" + key;
        ht.insert(key, value);
      }

      if (ht.numKeys() != target)
        fail("HashTable should have a size = " + target + ", but size=" + ht.numKeys());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

} // end class
