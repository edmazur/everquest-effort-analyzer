package com.edmazur.eqea;

public class EqEffort {

  private final String name;
  private int spellCasts = 0;

  public EqEffort(String name, int spellCasts) {
    this.name = name;
    this.spellCasts = spellCasts;
  }

  public String getName() {
    return name;
  }

  public void add(EqEffort eqEffort) {
    spellCasts += eqEffort.getSpellCasts();
  }

  public int getSpellCasts() {
    return spellCasts;
  }

}