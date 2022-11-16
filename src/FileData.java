/**
 * File Data class. This represents a file.
 * 
 */

public class FileData {

   public String name;
   public String dir;
   public String lastModifiedDate;

   public final String DEFAULT_NAME = "";
   public final String DEFAULT_DIR = "/";
   public final String DEFAULT_DATE = "01/01/2021";

   /**
    * Creates a FileData object with default values
    */
   public FileData() {
      this.name = this.DEFAULT_NAME;
      this.dir = this.DEFAULT_DIR;
      this.lastModifiedDate = this.DEFAULT_DATE;
   }

   /**
    * Constructs a FileData object with the specified arguments. If arguments are null
    * then they are set with default values
    * 
    * @param name         The name of the file
    * @param directory    The directory where the file is stored
    * @param modifiedDate The date in which the file was last modified
    */
   public FileData(String name, String directory, String modifiedDate) {
      this();

      if (name != null) {
         this.name = name;
      }
      if (directory != null) {
         this.dir = directory;
      }
      if (modifiedDate != null) {
         this.lastModifiedDate = modifiedDate;
      }
   }

   /**
    * Returns the content of a FileData object
    * 
    * @return a String with the name, directory and date of the file
    */
   public String toString() {
      // {Name: file_name, Directory: dir_name, Modified Date: date}
      return "{Name: " + this.name + ", Directory: " + this.dir + ", Modified Date: "
            + this.lastModifiedDate + "}";

   }
}
