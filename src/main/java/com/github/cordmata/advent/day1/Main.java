package com.github.cordmata.advent.day1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    var changes = getFrequencyChanges().map(Integer::valueOf).collect(Collectors.toList());
    System.out.println("Initial pass: " + changes.stream().mapToInt(i -> i).sum());
    var frequencies = new HashSet<Integer>();
    Integer firstDuplicate = null;
    var freq = 0;
    while (firstDuplicate == null) {
      for (Integer i : changes) {
        freq += i;
        if (!frequencies.add(freq)) {
          firstDuplicate = freq;
          break;
        }
      }
    }
    System.out.println("First dupe: " + firstDuplicate);
  }

  private static Stream<String> getFrequencyChanges() {
    var inputStream = Main.class.getResourceAsStream("/day1-frequency.txt");
    var reader = new BufferedReader(new InputStreamReader(inputStream));
    return reader.lines();
  }
}
