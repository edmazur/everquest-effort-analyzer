package com.edmazur.eqea;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EqPlayerLookup {

  private static Map<String, String> NAMES_TO_EQ_CLASSES;

  public EqPlayerLookup() {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Path.of(
          // TODO: Read this as a local resource.
          "/home/mazur/git/everquest-effort-analyzer/src/main/resources/players.txt"));
    } catch (IOException e) {
      // TODO: Handle more gracefully.
      e.printStackTrace();
      System.exit(-1);
    }

    NAMES_TO_EQ_CLASSES = new HashMap<>(lines.size());
    for (String line : lines) {
      String[] parts = line.split(",");
      NAMES_TO_EQ_CLASSES.put(parts[0], parts[1]);
    }
  }

  public boolean isKnownPlayer(String name) {
    return NAMES_TO_EQ_CLASSES.keySet().contains(name);
  }

  public Optional<String> getEqClass(String name) {
    String eqClass = NAMES_TO_EQ_CLASSES.get(name);
    if (eqClass == null) {
      return Optional.empty();
    } else {
      return Optional.of(eqClass);
    }
  }

}
