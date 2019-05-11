package com.github.cordmata.advent.day2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Main {

  public static void main(String[] args) {
    System.out.println(rudimentaryChecksum());
    System.out.println(getCommonChars());
  }

  private static long rudimentaryChecksum() {
    var with2 = getBoxIds().filter(bid -> bid.hasRepeatedChars(2)).count();
    var with3 = getBoxIds().filter(bid -> bid.hasRepeatedChars(3)).count();
    return with2 * with3;
  }

  private static Stream<BoxId> getBoxIds() {
    var inputStream = Main.class.getResourceAsStream("/day2-boxIds.txt");
    var reader = new BufferedReader(new InputStreamReader(inputStream));
    return reader.lines().map(BoxId::new);
  }

  private static String getCommonChars() {
    var ids = getBoxIds().map(BoxId::getId).map(id -> Set.of(id.split(""))).collect(toList());
    var offByOne = new ArrayList<String>();
    ids.forEach(idset -> {
      ids.forEach(other -> {
        idset.removeAll(other);
        if (idset.size() == 1) {
          System.out.println(idset.toString());
        }
      });
    });
    return "";
  }

  static class BoxId {
    private String id;
    private Map<String, Long> charCounts;

    public BoxId(String id) {
      this.id = id;
      this.charCounts = Arrays.stream(id.split("")).collect(groupingBy(c -> c, counting()));
    }

    public String getId() {
      return id;
    }

    public Map<String, Long> getCharCounts() {
      return charCounts;
    }

    public boolean hasRepeatedChars(int timesRepeated) {
      return charCounts.entrySet().stream().anyMatch(e -> e.getValue() == timesRepeated);
    }

  }
}
