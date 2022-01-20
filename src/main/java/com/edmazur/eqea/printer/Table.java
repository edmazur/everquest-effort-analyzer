package com.edmazur.eqea.printer;

import java.util.ArrayList;
import java.util.List;

public class Table {

  private final String heading;
  private final List<Column> columns;

  public Table(String heading) {
    this.heading = heading;
    this.columns = new ArrayList<>();
  }

  public int getColumnCount() {
    return columns.size();
  }

  public int getColumnWidth(int i) {
    return columns.get(i).getWidth();
  }

  public int getMaxValueWidth(int i) {
    return columns.get(i).getMaxValueWidth();
  }

  public void addColumn(Column column) {
    columns.add(column);
  }

  public void print(List<Integer> maxColumnWidths) {
    System.out.println(heading);

    // Print heading for each column.
    for (int column = 0; column < columns.size(); column++) {
      columns.get(column).printHeading(maxColumnWidths.get(column));
      System.out.print("  ");
    }
    System.out.println();

    // Print heading/row divider for each column.
    for (int column = 0; column < columns.size(); column++) {
      columns.get(column).printHeadingRowDivider(maxColumnWidths.get(column));
      System.out.print("  ");
    }
    System.out.println();

    // Print rows for each column.
    for (int row = 0; row < columns.get(0).getRowCount(); row++) {
      for (int column = 0; column < columns.size(); column++) {
        columns.get(column).printRow(row, maxColumnWidths.get(column));
        System.out.print("  ");
      }
      System.out.println();
    }
  }

}
