package com.edmazur.eqea;

import com.edmazur.eqlp.EqLog;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EqEffortAnalyzerMain {

  public static final Boolean INCLUDE_CHAT = false;

  private static final String EQ_LOG_TIMESTAMP_PATTERN =
      "EEE LLL dd HH:mm:ss yyyy";

  public static void main(String[] args) {
    // TODO: Validate input.
    final Path eqInstallDirectory = Paths.get(args[0]);
    final ZoneId timezone = ZoneId.of(args[1]);
    final String server = args[2];
    final String character = args[3];
    DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(EQ_LOG_TIMESTAMP_PATTERN)
            .withZone(timezone);
    final Instant parseStart = dateTimeFormatter.parse(args[4], Instant::from);
    final Instant parseEnd = dateTimeFormatter.parse(args[5], Instant::from);
    final String reportName = args[6];

    EqLog eqLog = new EqLog(
        eqInstallDirectory,
        timezone,
        server,
        character,
        parseStart,
        parseEnd);

    EqPlayerLookup eqPlayerLookup = new EqPlayerLookup();
    EqEffortParser eqEffortParser = new EqEffortParser(eqPlayerLookup, character);
    EqEffortListener eqEffortListener = new EqEffortListener(eqEffortParser);
    eqLog.addListener(eqEffortListener);
    eqLog.run();
    EqEffortPrinter.print(
        reportName,
        parseStart,
        parseEnd,
        timezone,
        character,
        eqEffortListener.getEfforts(),
        eqEffortParser.getUnrecognizedNames());
  }

}
