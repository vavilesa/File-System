/**
 * Unit testing for hash map used in file system
 * 
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

public class MyHashMapTest {

   private DefaultMap<String, String> testMap; // use this for basic tests
   private DefaultMap<String, String> mapWithCap; // use for testing proper rehashing
   public static final String TEST_KEY = "Test Key";
   public static final String TEST_VAL = "Test Value";

   @Before
   public void setUp() {
      testMap = new MyHashMap<>();
      mapWithCap = new MyHashMap<>(4, MyHashMap.DEFAULT_LOAD_FACTOR);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testPut_nullKey() {
      testMap.put(null, TEST_VAL);
   }

   @Test
   public void testKeys_nonEmptyMap() {
      // You don't have to use array list
      // This test will work with any object that implements List
      List<String> expectedKeys = new ArrayList<>(5);
      for (int i = 0; i < 5; i++) {
         // key + i is used to differentiate keys since they must be unique
         testMap.put(TEST_KEY + i, TEST_VAL + i);
         expectedKeys.add(TEST_KEY + i);
      }
      List<String> resultKeys = testMap.keys();
      // we need to sort because hash map doesn't guarantee ordering
      Collections.sort(resultKeys);
      assertEquals(expectedKeys, resultKeys);
   }

   /* Add more of your tests below */

   // Test if the map is empty when instantiated, after adding an element, and then
   // removing it
   @Test
   public void testEmpty() {
      assertEquals(true, testMap.isEmpty());

      testMap.put(TEST_KEY, TEST_VAL);
      assertEquals(false, testMap.isEmpty());

      testMap.remove(TEST_KEY);
      assertEquals(true, testMap.isEmpty());
   }

   // test if the replace method correctly updates the value of an existing key
   @Test
   public void testReplace() {
      testMap.put(TEST_KEY, TEST_VAL);
      testMap.replace(TEST_KEY, "New");

      assertEquals("New", testMap.get(TEST_KEY));
   }

   // Test if an identical key cannot be added
   @Test
   public void testUniqueKeys() {
      testMap.put(TEST_KEY, TEST_VAL);
      assertEquals(false, testMap.put(TEST_KEY, TEST_VAL));
      assertEquals(1, testMap.size());
   }

   // Test if containsKey method correctly returns whether the key exists or nor
   @Test
   public void testContains() {
      testMap.put(TEST_KEY, TEST_VAL);
      testMap.put("abc", "123");
      assertEquals(true, testMap.containsKey(TEST_KEY));
      assertEquals(true, testMap.containsKey("abc"));
      assertEquals(false, testMap.containsKey("Should not exist"));
   }

   // Test if the set method correctly adds a non existing key and updates an existing key
   @Test
   public void testSetMethod() {
      testMap.put(TEST_KEY, TEST_VAL);
      testMap.set(TEST_KEY, "New Value");
      testMap.set("New Key", "abc");

      assertEquals(2, testMap.size());
      assertEquals("New Value", testMap.get(TEST_KEY));
      assertEquals("abc", testMap.get("New Key"));
   }
}