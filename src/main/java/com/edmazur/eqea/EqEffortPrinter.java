package com.edmazur.eqea;

import com.edmazur.eqea.printer.Column;
import com.edmazur.eqea.printer.MultiTablePrinter;
import com.edmazur.eqea.printer.Table;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class EqEffortPrinter {

  private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss zz";

  public static void print(
      String reportName,
      Instant parseStart,
      Instant parseEnd,
      ZoneId timezone,
      String character,
      Collection<EqEffort> eqEfforts,
      Set<String> unrecognizedNames) {
    Map<String, List<EqEffort>> classesToEqEfforts = new TreeMap<>();
    EqPlayerLookup eqClassLookup = new EqPlayerLookup();

    // Group per EqClass.
    for (EqEffort eqEffort : eqEfforts) {
      Optional<String> maybeEqClass =
          eqClassLookup.getEqClass(eqEffort.getName());
      // TODO: This is a gruesome hack to put UNKNOWN at the end.
      String eqClass = maybeEqClass.get().equals("UNKNOWN")
          ? "zzzUNKNOWN" : maybeEqClass.get();

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
    List<Table> tables = new ArrayList<>(classesToEqEfforts.size());
    for (Map.Entry<String, List<EqEffort>> mapEntry :
        classesToEqEfforts.entrySet()) {
      String eqClass = mapEntry.getKey();
      List<EqEffort> eqEffortsForClass = mapEntry.getValue();
      tables.add(getTableForClass(eqClass, eqEffortsForClass));
    }

    // Print!
    System.out.println("Effort analysis: " + reportName);
    DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).withZone(timezone);
    System.out.println("    Parse start: " + dateTimeFormatter.format(parseStart));
    System.out.println("      Parse end: " + dateTimeFormatter.format(parseEnd));
    System.out.println("           Logs: " + character);
    System.out.println();
    MultiTablePrinter.print(tables);
    System.out.println("Names not recognized as players: "
        + String.join(", ", unrecognizedNames));
  }

  private static Table getTableForClass(String eqClass, List<EqEffort> eqEfforts) {
    // Make spell casts column.
    Collections.sort(eqEfforts, new Comparator<EqEffort>() {
      @Override
      public int compare(EqEffort a, EqEffort b) {
        if (a.getSpellCasts() != b.getSpellCasts()) {
          return b.getSpellCasts() - a.getSpellCasts();
        } else {
          return a.getName().compareTo(b.getName());
        }
      }
    });
    Column spellCastsColumn = new Column("Spell casts");
    for (EqEffort eqEffort : eqEfforts) {
      spellCastsColumn.addRow(eqEffort.getSpellCasts(), eqEffort.getName());
    }

    // Make damage given column.
    Collections.sort(eqEfforts, new Comparator<EqEffort>() {
      @Override
      public int compare(EqEffort a, EqEffort b) {
        if (a.getDamageGiven() != b.getDamageGiven()) {
          return b.getDamageGiven() - a.getDamageGiven();
        } else {
          return a.getName().compareTo(b.getName());
        }
      }
    });
    Column damageGivenColumn = new Column("Damage given");
    for (EqEffort eqEffort : eqEfforts) {
      damageGivenColumn.addRow(eqEffort.getDamageGiven(), eqEffort.getName());
    }

    // Make damage received column.
    Collections.sort(eqEfforts, new Comparator<EqEffort>() {
      @Override
      public int compare(EqEffort a, EqEffort b) {
        if (a.getDamageReceived() != b.getDamageReceived()) {
          return b.getDamageReceived() - a.getDamageReceived();
        } else {
          return a.getName().compareTo(b.getName());
        }
      }
    });
    Column damageReceivedColumn = new Column("Damage received");
    for (EqEffort eqEffort : eqEfforts) {
      damageReceivedColumn.addRow(eqEffort.getDamageReceived(), eqEffort.getName());
    }

    // Make chat messages column.
    Collections.sort(eqEfforts, new Comparator<EqEffort>() {
      @Override
      public int compare(EqEffort a, EqEffort b) {
        if (a.getChatMessages() != b.getChatMessages()) {
          return b.getChatMessages() - a.getChatMessages();
        } else {
          return a.getName().compareTo(b.getName());
        }
      }
    });
    Column chatMessagesColumn = new Column("Chat messages");
    for (EqEffort eqEffort : eqEfforts) {
      chatMessagesColumn.addRow(eqEffort.getChatMessages(), eqEffort.getName());
    }

    // Print tables.
    Table table = new Table(
        eqClass.equals("zzzUNKNOWN") ? "UNKNOWN" : eqClass.toUpperCase());
    table.addColumn(spellCastsColumn);
    table.addColumn(damageGivenColumn);
    table.addColumn(damageReceivedColumn);
    if (EqEffortAnalyzerMain.INCLUDE_CHAT) {
      table.addColumn(chatMessagesColumn);
    }
    return table;
  }

}
