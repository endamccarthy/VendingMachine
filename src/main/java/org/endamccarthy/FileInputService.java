package org.endamccarthy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * FileInputService
 * <p>
 * A Utility class used to read in text content from a file.
 */
public final class FileInputService {

  /**
   * Default Constructor
   * <p>
   * As this is a utility class which cannot be instantiated, the constructor is private and throws
   * an UnsupportedOperationException.
   */
  private FileInputService() {
    throw new UnsupportedOperationException();
  }

  /**
   * readFromFile
   *
   * This will return null if the file could not be read.
   * @param filename This is the name of the file that is being attempted to be read.
   * @return An ArrayList of String arrays (each String array is a line in the file)
   */
  public static ArrayList<String[]> readFromFile(String filename) {
    // try to read in from file
    try {
      ArrayList<String[]> input = new ArrayList<>();
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String row;
      while ((row = br.readLine()) != null) {
        input.add(row.split(","));
      }
      br.close();
      return input;
    } catch (IOException e) {
      return null;
    }
  }

}