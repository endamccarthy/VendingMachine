package org.endamccarthy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileInputService {

  private static ArrayList<String[]> input;

  public static ArrayList<String[]> readFromFile(String filename) {
    try {
      input = new ArrayList<>();
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