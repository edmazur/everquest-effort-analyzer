package com.edmazur.eqea.printer;

import java.util.ArrayList;
import java.util.List;

/**
 * Technically 2 columns, a value and a key, but referred to as just "Column"
 * for simplicity because these will be arranged side by side with other
 * value/key columns.
 */
public class Column {

  private static final Integer SPACES_BETWEEN_KEYS_AND_VALUES = 1;

  private String heading;
  private List<Integer> values;
  private List<String> keys;
  private int maxValueLength;
  private int maxKeyLength;

  public Column(String heading) {
    this.heading = heading;
    this.values = new ArrayList<>();
    this.keys = new ArrayList<>();
  }

  public void addRow(int value, String key) {
    values.add(value);
    keys.add(key);
    maxValueLength = Math.max(maxValueLength, getIntLength(value));
    maxKeyLength = Math.max(maxKeyLength, key.length());
  }

  public int getRowCount() {
    return values.size();
  }

  public int getWidth() {
    return Math.max(
        heading.length(),
        maxValueLength + SPACES_BETWEEN_KEYS_AND_VALUES + maxKeyLength);
  }

  public int getMaxValueWidth() {
    return maxValueLength;
  }

  public void printHeading(int maxColumnWidth) {
    System.out.print(String.format("%-" + maxColumnWidth + "s", heading));
  }

  public void printHeadingRowDivider(int maxColumnWidth) {
    System.out.print("-".repeat(maxColumnWidth));
  }

  public void printRow(int i, int maxColumnWidth) {
    String innerFormatString = "%" + maxValueLength + "d %-" + maxKeyLength + "s";
    String innerString = String.format(innerFormatString, values.get(i), keys.get(i));
    String outerFormatString = "%-" + maxColumnWidth + "s";
    String outerString = String.format(outerFormatString, innerString);
    System.out.print(outerString);
  }

  private int getIntLength(int i) {
    return String.valueOf(i).length();
  }

}
