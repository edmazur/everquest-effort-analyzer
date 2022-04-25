package com.edmazur.eqea;

import com.edmazur.eqlp.EqLogEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EqEffortParser {

  private static final Pattern OTHER_CAST = Pattern.compile(
      "(?<name>\\p{Upper}\\p{Lower}+) begins to cast a spell\\.");

  private static final Pattern SELF_CAST = Pattern.compile(
      "You begin casting .+\\.");

  private static final Pattern DAMAGE = Pattern.compile(
      "(?<attacker>\\p{Alpha}+) .+ (?<defender>\\p{Alpha}+) "
          + "for (?<damage>\\d+) points of damage\\.");

  private static final Pattern CHAT_OTHER = Pattern.compile(
      "(?<name>\\p{Upper}\\p{Lower}+) "
          + "(?:tells the guild|says out of character|shouts|auctions|says), '.*");

  private static final Pattern CHAT_SELF = Pattern.compile(
      "(?<name>You) (?:say to your guild|say out of character|shout|auction|say), '.*");

  private final EqPlayerLookup eqPlayerLookup;
  private final String character;
  private final Set<String> unrecognizedNames;

  public EqEffortParser(EqPlayerLookup eqPlayerLookup, String character) {
    this.eqPlayerLookup = eqPlayerLookup;
    this.character = character;
    this.unrecognizedNames = new TreeSet<>();
  }

  public List<EqEffort> getEqEfforts(EqLogEvent eqLogEvent) {
    Matcher matcher = OTHER_CAST.matcher(eqLogEvent.getPayload());
    if (matcher.matches()) {
      String name = matcher.group("name");
      if (!isKnownPlayer(name)) {
        return Collections.emptyList();
      }
      return Collections.singletonList(new EqEffort(name, 1, 0, 0, 0));
    }

    matcher = SELF_CAST.matcher(eqLogEvent.getPayload());
    if (matcher.matches()) {
      return Collections.singletonList(new EqEffort(character, 1, 0, 0, 0));
    }

    matcher = DAMAGE.matcher(eqLogEvent.getPayload());
    if (matcher.matches()) {
      List<EqEffort> eqEfforts = new ArrayList<>(2);
      String attacker = matcher.group("attacker");
      boolean hasAttacker = attacker != null;
      String defender = matcher.group("defender");
      boolean hasDefender = defender != null;
      if (hasAttacker && attacker.equals("You")) {
        attacker = character;
      }
      if (hasDefender && defender.equals("YOU")) {
        defender = character;
      }
      int damage = Integer.parseInt(matcher.group("damage"));
      if (hasAttacker && isKnownPlayer(attacker)) {
        eqEfforts.add(new EqEffort(attacker, 0, damage, 0, 0));
      }
      if (hasDefender && isKnownPlayer(defender)) {
        eqEfforts.add(new EqEffort(defender, 0, 0, damage, 0));
      }
      return eqEfforts;
    }

    if (EqEffortAnalyzerMain.INCLUDE_CHAT) {
      matcher = CHAT_OTHER.matcher(eqLogEvent.getPayload());
      if (matcher.matches()) {
        String name = matcher.group("name");
        if (!isKnownPlayer(name)) {
          return Collections.emptyList();
        }
        return Collections.singletonList(new EqEffort(name, 0, 0, 0, 1));
      }

      matcher = CHAT_SELF.matcher(eqLogEvent.getPayload());
      if (matcher.matches()) {
        String name = character;
        return Collections.singletonList(new EqEffort(name, 0, 0, 0, 1));
      }
    }

    // If you got this far, nothing matched - no effort.
    return Collections.emptyList();
  }

  private boolean isKnownPlayer(String name) {
    if (eqPlayerLookup.isKnownPlayer(name)) {
      return true;
    } else {
      unrecognizedNames.add(name);
      return false;
    }
  }

  public Set<String> getUnrecognizedNames() {
    return unrecognizedNames;
  }

}
