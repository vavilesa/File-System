/**
 * Creates and manages a file system using hash maps for date and name
 */

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystem {

   MyHashMap<String, ArrayList<FileData>> nameMap;
   MyHashMap<String, ArrayList<FileData>> dateMap;

   /**
    * Constructs an empty FileSystem object
    */
   public FileSystem() {
      this.nameMap = new MyHashMap<String, ArrayList<FileData>>();
      this.dateMap = new MyHashMap<String, ArrayList<FileData>>();
   }

   /**
    * Constructs a FileSystem object with data from a file
    * 
    * @param inputFile the path of the file
    */
   public FileSystem(String inputFile) {
      this();

      try {
         File f = new File(inputFile);
         Scanner sc = new Scanner(f);
         while (sc.hasNextLine()) {
            String[] data = sc.nextLine().split(", ");
            this.add(data[0], data[1], data[2]);
         }
         sc.close();
      }
      catch (FileNotFoundException e) {
         System.out.println(e);
      }
   }

   /**
    * Adds a FileData object with the specified arguments to the map. FileData objects
    * cannot have the same name and directory
    * 
    * @param fileName     The name of the file
    * @param directory    The relative path of the file
    * @param modifiedDate The last modified date
    * @return true if successfully added to the list
    */
   public boolean add(String fileName, String directory, String modifiedDate) {

      // Cannot add file with the same name and directory
      if (this.findFile(fileName, directory) != null) {
         return false;
      }

      FileData fileData = new FileData(fileName, directory, modifiedDate);

      // Create separate arrays to avoid referencing both maps with same ArrayList
      ArrayList<FileData> fileArrayName = new ArrayList<FileData>();
      ArrayList<FileData> filedArrayDate = new ArrayList<FileData>();
      fileArrayName.add(fileData);
      filedArrayDate.add(fileData);

      // Attempts to add a new (key, value) to each map.
      boolean addedName = this.nameMap.put(fileData.name, fileArrayName);
      boolean addedDate = this.dateMap.put(fileData.lastModifiedDate, filedArrayDate);

      // If put returns false, then map has an existing key and FileData will be added
      // to the key's ArrayList
      if (!addedName) {
         this.nameMap.get(fileData.name).add(fileData);
         addedName = true;
      }

      if (!addedDate) {
         this.dateMap.get(fileData.lastModifiedDate).add(fileData);
         addedDate = true;
      }
      return (addedName && addedDate);
   }

   /**
    * Finds a file with the specified name and directory
    * 
    * @param name      The name of the file
    * @param directory The directory of the file
    * @return The FileData object, null if not found
    */
   public FileData findFile(String name, String directory) {
      if (name == null || directory == null) {
         return null;
      }

      if (this.nameMap.containsKey(name)) {
         for (FileData f : this.nameMap.get(name)) {
            if (f.dir.equals(directory)) {
               return f;
            }
         }
      }

      return null;
   }

   /**
    * Returns an ArrayList with all the name keys in the map
    * 
    * @return Array List with the keys
    */
   public ArrayList<String> findAllFilesName() {
      return (ArrayList<String>) nameMap.keys();
   }

   /**
    * Finds all files with the same name
    * 
    * @param name The name to be searched
    * @return ArrayList with FileData object for each file
    */
   @SuppressWarnings("unchecked")
   public ArrayList<FileData> findFilesByName(String name) {
      if (this.nameMap.containsKey(name)) {
         return (ArrayList<FileData>) this.nameMap.get(name).clone();
      }

      return new ArrayList<FileData>();
   }

   /**
    * Finds all files with the same last modified date
    * 
    * @param date The date to be searched
    * @return ArrayList with FileData object for each file
    */
   @SuppressWarnings("unchecked")
   public ArrayList<FileData> findFilesByDate(String modifiedDate) {
      if (this.dateMap.containsKey(modifiedDate)) {
         return (ArrayList<FileData>) this.dateMap.get(modifiedDate).clone();
      }

      return new ArrayList<FileData>();
   }

   /**
    * Returns a list of FileData with the specified date if there is at least another one
    * in a different directory
    * 
    * @param modifiedDate The date to be searched
    * @return A list of FileData objects with the same name and date
    */
   public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
      ArrayList<FileData> files = new ArrayList<FileData>();

      if (this.findFilesByDate(modifiedDate) != null) {
         for (FileData f : this.findFilesByDate(modifiedDate)) {
            if (this.findFilesByName(f.name) != null
                  && this.findFilesByName(f.name).size() > 1) {
               files.add(f);
            }
         }
      }

      return files;
   }

   /**
    * Removes a file by name
    * 
    * @param name The name of the file to be removed
    * @return true if the file was successfully removed
    */
   public boolean removeByName(String name) {
      if (name == null || !this.nameMap.containsKey(name)) {
         return false;
      }

      // Search and remove files in the dateMap
      for (FileData f : this.nameMap.get(name)) {
         this.dateMap.get(f.lastModifiedDate).remove(f);
         this.checkEmptyKeys(name, f.lastModifiedDate);
      }

      // Remove key from nameMap (removes all values)
      this.nameMap.remove(name);

      this.checkEmptyKeys(name, null);
      return true;
   }

   /**
    * Removes a file by name and directory
    * 
    * @param name      The name of the file to remove
    * @param directory The directory of the file to remove
    * @return true if the file was successful removed
    */
   public boolean removeFile(String name, String directory) {

      if (name == null || directory == null || !this.nameMap.containsKey(name)) {
         return false;
      }

      FileData fileToRemove = this.findFile(name, directory);
      this.nameMap.get(name).remove(fileToRemove);
      this.dateMap.get(fileToRemove.lastModifiedDate).remove(fileToRemove);

      this.checkEmptyKeys(name, fileToRemove.lastModifiedDate);
      return true;
   }

   /**
    * Removes keys whose value (ArraList) is empty
    * 
    * @param name The name key to check
    * @param date The date Key to check
    */
   private void checkEmptyKeys(String name, String date) {

      if (name != null && this.nameMap.get(name) != null
            && this.nameMap.get(name).isEmpty()) {
         this.nameMap.remove(name);
      }
      if (date != null && this.dateMap.get(date) != null
            && this.dateMap.get(date).isEmpty()) {
         this.dateMap.remove(date);
      }

   }
}
