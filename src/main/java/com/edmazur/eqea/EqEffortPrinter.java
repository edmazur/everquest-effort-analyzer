package com.edmazur.eqea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class EqEffortPrinter {

  public static void print(Collection<EqEffort> eqEfforts) {
    Map<String, List<EqEffort>> classesToEqEfforts = new TreeMap<>();
    EqClassLookup eqClassLookup = new EqClassLookup();

    // Group per EqClass.
    for (EqEffort eqEffort : eqEfforts) {
      Optional<String> maybeEqClass =
          eqClassLookup.getEqClass(eqEffort.getName());
      String eqClass = maybeEqClass.isEmpty() ? "UNKNOWN" : maybeEqClass.get();

      List<EqEffort> eqEffortsForClass = classesToEqEfforts.get(eqClass);
      if (eqEffortsForClass == null) {
        eqEffortsForClass = new ArrayList<>();
        eqEffortsForClass.add(eqEffort);
        classesToEqEfforts.put(eqClass, eqEffortsForClass);
      } else {
        eqEffortsForClass.add(eqEffort);
      }
    }

    // Iterate over EqClass.
    for (Map.Entry<String, List<EqEffort>> mapEntry :
        classesToEqEfforts.entrySet()) {
      String eqClass = mapEntry.getKey();
      List<EqEffort> eqEffortsForClass = mapEntry.getValue();
      printForClass(eqClass, eqEffortsForClass);
      System.out.println();
    }
  }

  private static void printForClass(String eqClass, List<EqEffort> eqEfforts) {
    System.out.println(eqClass.toUpperCase());
    Collections.sort(eqEfforts, new Comparator<EqEffort>() {
      @Override
      public int compare(EqEffort a, EqEffort b) {
        return b.getSpellCasts() - a.getSpellCasts();
      }
    });
    for (EqEffort eqEffort : eqEfforts) {
      System.out.println(String.format(
          "%d - %s", eqEffort.getSpellCasts(), eqEffort.getName()));
    }
  }

}
