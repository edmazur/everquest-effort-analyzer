package com.edmazur.eqea;

import com.edmazur.eqlp.EqLogEvent;
import com.edmazur.eqlp.EqLogListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqEffortListener implements EqLogListener {

  private final EqEffortParser eqEffortParser;
  private final Map<String, EqEffort> efforts;

  public EqEffortListener(EqEffortParser eqEffortParser) {
    this.eqEffortParser = eqEffortParser;
    this.efforts = new HashMap<>();
  }

  @Override
  public void onEvent(EqLogEvent eqLogEvent) {
    List<EqEffort> eqEfforts = eqEffortParser.getEqEfforts(eqLogEvent);
    for (EqEffort newEqEffort : eqEfforts) {
      EqEffort existingEqEffort = efforts.get(newEqEffort.getName());
      if (existingEqEffort == null) {
        efforts.put(newEqEffort.getName(), newEqEffort);
      } else {
        existingEqEffort.add(newEqEffort);
      }
    }
  }

  public Collection<EqEffort> getEfforts() {
    return efforts.values();
  }

}
