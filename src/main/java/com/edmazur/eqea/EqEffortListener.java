package com.edmazur.eqea;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.edmazur.eqlp.EqLogEvent;
import com.edmazur.eqlp.EqLogListener;

public class EqEffortListener implements EqLogListener {

  private Map<String, EqEffort> efforts;

  public EqEffortListener() {
    this.efforts = new HashMap<>();
  }

  @Override
  public void onEvent(EqLogEvent eqLogEvent) {
    Optional<EqEffort> maybeEqEffort = EqEffortParser.getEqEffort(eqLogEvent);
    if (maybeEqEffort.isEmpty()) {
      return;
    }
    EqEffort newEqEffort = maybeEqEffort.get();
    EqEffort existingEqEffort = efforts.get(newEqEffort.getName());
    if (existingEqEffort == null) {
      efforts.put(newEqEffort.getName(), newEqEffort);
    } else {
      existingEqEffort.add(newEqEffort);
    }
  }

  public Collection<EqEffort> getEfforts() {
    return efforts.values();
  }

}
