package com.edmazur.eqea;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.edmazur.eqlp.EqLogEvent;

public class EqEffortParser {

  private static final Pattern OTHER_CAST = Pattern.compile(
      "(?<name>\\p{Upper}\\p{Lower}+) begins to cast a spell\\.");

  private static final Pattern SELF_CAST = Pattern.compile(
      "You begin casting .+\\.");

  public static Optional<EqEffort> getEqEffort(EqLogEvent eqLogEvent) {
    Matcher matcher = OTHER_CAST.matcher(eqLogEvent.getPayload());
    if (matcher.matches()) {
      String name = matcher.group("name");
      return Optional.of(new EqEffort(name, 1));
    }

    matcher = SELF_CAST.matcher(eqLogEvent.getPayload());
    if (matcher.matches()) {
      // TODO: Get this dynamically from the name of the file being parsed.
      String name = "Stanvern";
      return Optional.of(new EqEffort(name, 1));
    }

    // If you got this far, nothing matched - no effort.
    return Optional.empty();
  }

}