package org.endamccarthy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileOutputService
 * <p>
 * A Utility class used to write text out to a file.
 */
public final class FileOutputService {

  /**
   * Default Constructor
   * <p>
   * As this is a utility class which cannot be instantiated, the constructor is private and throws
   * an UnsupportedOperationException.
   */
  private FileOutputService() {
    throw new UnsupportedOperationException();
  }

  /**
   * writeToFile
   * <p>
   * This will overwrite the entire contents of a file with the text passed in here.
   *
   * @param filename This is the name of the file that is being attempted to be written to.
   * @param output   This is an ArrayList of Strings which contains the text to be written.
   * @return A boolean (true if operation was successful, false otherwise).
   */
  public static boolean writeToFile(String filename, ArrayList<String> output) {
    // try to write out to file
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
      PrintWriter pw = new PrintWriter(bw);
      for (String line : output) {
        pw.println(line);
      }
      pw.flush();
      pw.close();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * editSingleLine
   * <p>
   * This will only replace a single line in the file. It is used to update a customers balance. It
   * copies all the contents of the original file to a temporary file. It checks for the line
   * containing the username of the customer and replaces the line with a new line including the
   * updated balance.
   *
   * @param filename This is the name of the file that is being attempted to be written to.
   * @param newLine  This is an ArrayList of Strings which contains the text to be written.
   * @return A boolean (true if operation was successful, false otherwise).
   */
  public static boolean editSingleLine(String filename, ArrayList<String> newLine) {
    String username, balance, password;
    String tempFile = "temp.dat";
    File oldFile = new File(filename);
    File newFile = new File(tempFile);
    // try to write out to file
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile, true));
      PrintWriter pw = new PrintWriter(bw);
      Scanner scanner = new Scanner(new File(filename));
      scanner.useDelimiter("[,\n]");
      while (scanner.hasNext()) {
        username = scanner.next();
        balance = scanner.next();
        password = scanner.next();
        if (username.equals(newLine.get(0)) && newLine.size() == 3) {
          for (int i = 0; i < newLine.size(); i++) {
            pw.print(newLine.get(i));
            if (i == newLine.size() - 1) {
              pw.println();
            } else {
              pw.print(",");
            }
          }
        } else {
          pw.println(username + "," + balance + "," + password);
        }
      }
      scanner.close();
      pw.flush();
      pw.close();
      if (!oldFile.delete()) {
        return false;
      }
      File dump = new File(filename);
      return newFile.renameTo(dump);
    } catch (IOException e) {
      return false;
    }
  }

}
