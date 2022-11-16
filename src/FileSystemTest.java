/**
 * Unit testing for file system
 */

import static org.junit.Assert.*;

import org.junit.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class FileSystemTest {

   private FileSystem fs;

   // Creates a FileSystem with entries from a text file
   @Before
   public void initializeFromFile() {
      fs = new FileSystem("C:\\Users\\vicen\\Desktop\\Projects"
            + "\\File System\\test\\input.txt");
   }

   // Tests findFile method
   @Test
   public void testFindFile() {
      assertNotEquals(null, fs.findFile("mySample.txt", "/home"));
      assertEquals(null, fs.findFile("mySample.txt", "Not a directory"));
      assertEquals(null, fs.findFile("Not In File", "/home"));
   }

   // Test if the add method adds a new entry and does not add an existing entry
   @Test
   public void testAddEntries() {
      assertEquals(true, fs.add("test.txt", "/home", "04/02/2021"));
      assertNotEquals(null, fs.findFile("test.txt", "/home"));
      assertEquals(false, fs.add("test.txt", "/home", "04/02/2021"));
   }

   // Test if method finds and returns all the keys
   @Test
   public void testFindAllFilesName() {
      ArrayList<String> ls = fs.findAllFilesName();
      ArrayList<String> expectedKeys = new ArrayList<>();

      expectedKeys.add("mySample.txt");
      expectedKeys.add("notes.txt");
      expectedKeys.add("homework.pdf");
      expectedKeys.add("project.pdf");
      expectedKeys.add("important.pdf");

      Collections.sort(ls);
      Collections.sort(expectedKeys);

      assertArrayEquals(expectedKeys.toArray(), ls.toArray());
   }

   // Test if find by name returns all the FileData objects with the same name
   @Test
   public void testFindByName() {
      ArrayList<FileData> nameMySample = fs.findFilesByName("mySample.txt");
      ArrayList<FileData> nameHomework = fs.findFilesByName("homework.pdf");
      ArrayList<FileData> notInMap = fs.findFilesByName("NotInMap.pdf");
      ArrayList<FileData> project = fs.findFilesByName("project.pdf");

      assertEquals(2, project.size());
      assertEquals(3, nameMySample.size());
      assertEquals(1, nameHomework.size());
      assertEquals(0, notInMap.size());

   }

   // Test if find by name returns all the FileData objects with the same date
   @Test
   public void testFindByDate() {

      ArrayList<FileData> dateMarchThird = fs.findFilesByDate("03/03/2021");
      ArrayList<FileData> dateFebFirst = fs.findFilesByDate("02/01/2021");

      assertEquals(1, dateMarchThird.size());
      assertEquals(2, dateFebFirst.size());
   }

   // Test if a file is removed by name
   @Test
   public void testRemoveByName() {
      assertEquals(null, fs.findFile("remove.txt", "/home"));

      fs.add("remove.txt", "/home", "01/01/2021");
      assertNotEquals(null, fs.findFile("remove.txt", "/home"));

      fs.removeByName("remove.txt");
      assertEquals(null, fs.findFile("remove.txt", "/home"));
   }

   // Test if a file is removed with specified name and directory
   @Test
   public void testRemoveFile() {
      assertEquals(null, fs.findFile("test.txt", "/home"));

      fs.add("test.txt", "/home", "01/01/2021");
      assertNotEquals(null, fs.findFile("test.txt", "/home"));

      fs.removeFile("test.txt", "/home");
      assertEquals(null, fs.findFile("test.txt", "/home"));
   }

   // Test find in different directories
   @Test
   public void testDifferentDir() {
      ArrayList<FileData> ls = fs.findFilesInMultDir("02/01/2021");

      // There are two files with the same and last modified date of 02/01/2021 but in
      // different directories
      assertEquals(2, ls.size());
   }

   // Test if there are empty keys present
   @Test
   public void testEmptyKeys() {
      ArrayList<String> expectedKeys = new ArrayList<>();
      expectedKeys.add("mySample.txt");
      expectedKeys.add("notes.txt");
      expectedKeys.add("homework.pdf");
      expectedKeys.add("project.pdf");

      // Should become empty key
      fs.removeByName("important.pdf");

      // mySample.txt is not empty yet
      fs.removeFile("mySample.txt", "/home");

      ArrayList<String> ls = fs.findAllFilesName();
      Collections.sort(ls);
      Collections.sort(expectedKeys);

      assertArrayEquals(expectedKeys.toArray(), ls.toArray());
   }

   // Test adding and removing a null FileData
   @Test
   public void testNullFileData() {

      assertEquals(true, fs.add(null, null, null));
      assertEquals(false, fs.findFilesByName("").isEmpty());
      assertEquals(false, fs.removeByName(null));

   }
}
