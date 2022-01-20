package com.edmazur.eqea.printer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiTablePrinter {

  public static void print(List<Table> tables) {
    // Get max column widths.
    List<Integer> maxColumnWidths =
        new ArrayList<>(Collections.nCopies(tables.get(0).getColumnCount(), 0));
    for (int table = 0; table < tables.size(); table++) {
      for (int column = 0; column < tables.get(table).getColumnCount(); column++) {
        maxColumnWidths.set(
            column,
            Math.max(
                maxColumnWidths.get(column),
                tables.get(table).getColumnWidth(column)));
      }
    }

    for (int table = 0; table < tables.size(); table++) {
      tables.get(table).print(maxColumnWidths);
      System.out.println();
    }
  }

}