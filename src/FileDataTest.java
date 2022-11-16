/*
 * Unit testing for files
 */

import static org.junit.Assert.*;

import org.junit.*;

public class FileDataTest {

   public final String TEST_NAME = "PA6";
   public final String TEST_DIR = "\\CSE12";
   public final String TEST_DATE = "02/17/2021";

   // Test the argument constructor
   @Test
   public void testConstructor() {

      FileData fd = new FileData(TEST_NAME, TEST_DIR, TEST_DATE);

      assertEquals(TEST_NAME, fd.name);
      assertEquals(TEST_DIR, fd.dir);
      assertEquals(TEST_DATE, fd.lastModifiedDate);
   }

   // Test the toString method
   @Test
   public void testToString() {

      String expected = "{Name: " + TEST_NAME + ", Directory: " + TEST_DIR
            + ", Modified Date: " + TEST_DATE + "}";

      FileData fd = new FileData(TEST_NAME, TEST_DIR, TEST_DATE);
      assertEquals(expected, fd.toString());
   }

   // Test null inputs for the constructor
   @Test
   public void testNullInput() {
      FileData fd = new FileData(null, null, null);

      // Expected are default values
      assertEquals("", fd.name);
      assertEquals("/", fd.dir);
      assertEquals("01/01/2021", fd.lastModifiedDate);
   }
}
