package Phase3.Data3D;
import java.util.*;
import java.io.File;  // Import the File class

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * The MyData class contains constants and methods for reading serialized lists from files.
 */
public class MyData {

        // Constants defining the dimensions of the 3D space
        public static final int depth = 33;
        public static final int width = 5;
        public static final int height = 8;

        // List of indices representing the best order for pentominos
        public static List<Integer> indecesPent = read("Phase3/Data3D/BestAnswer.bin");
        // List of indices representing the best order for parcels
        public static List<Integer> indecesParc = read("Phase3/Data3D/ListIntegerObjectPar2.txt");
        
        /**
        * Reads a serialized list of integers from a file.
        *
        * @param file The name of the file to read.
        * @return A list of integers read from the file.
        */
        private static List<Integer> read(String file){
                try {
                        // Open a FileInputStream for the specified file
                        FileInputStream fi = new FileInputStream(new File(file));
                        // Create an ObjectInputStream to read objects from the file
			ObjectInputStream oi = new ObjectInputStream(fi);
                
                // Read the list of integers from the file
                List<Integer> indeces =  (List<Integer>) oi.readObject();
                return indeces;

        } catch (Exception e) {
                // Print the stack trace if an exception occurs
                e.printStackTrace();
        }
        return null;
        }
}