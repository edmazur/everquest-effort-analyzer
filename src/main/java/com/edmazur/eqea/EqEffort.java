package com.edmazur.eqea;

public class EqEffort {

  private final String name;
  private int spellCasts = 0;
  private int damageGiven = 0;
  private int damageReceived = 0;
  private int chatMessages = 0;

  public EqEffort(
      String name,
      int spellCasts,
      int damageGiven,
      int damageReceived,
      int chatMessages) {
    this.name = name;
    this.spellCasts = spellCasts;
    this.damageGiven = damageGiven;
    this.damageReceived = damageReceived;
    this.chatMessages = chatMessages;
  }

  public String getName() {
    return name;
  }

  public int getSpellCasts() {
    return spellCasts;
  }

  public int getDamageGiven() {
    return damageGiven;
  }

  public int getDamageReceived() {
    return damageReceived;
  }

  public int getChatMessages() {
    return chatMessages;
  }

  public void add(EqEffort eqEffort) {
    spellCasts += eqEffort.getSpellCasts();
    damageGiven += eqEffort.getDamageGiven();
    damageReceived += eqEffort.getDamageReceived();
    chatMessages += eqEffort.getChatMessages();
  }

}
