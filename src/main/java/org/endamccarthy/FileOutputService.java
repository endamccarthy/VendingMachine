package org.endamccarthy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public final class FileOutputService {

  private FileOutputService() {
    throw new UnsupportedOperationException();
  }

  // write entire file (rewrites over existing file)
  public static boolean writeToFile(String filename, ArrayList<String> output) {
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

  // change a single line in a file
  public static boolean editSingleLine(String filename, ArrayList<String> newLine) {
    String username, balance, password;
    String tempFile = "temp.dat";
    File oldFile = new File(filename);
    File newFile = new File(tempFile);
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
      oldFile.delete();
      File dump = new File(filename);
      newFile.renameTo(dump);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

}
