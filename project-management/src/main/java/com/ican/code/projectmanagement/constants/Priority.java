package com.ican.code.projectmanagement.constants;

public enum Priority {
  LOW(3),
  MEDIUM(2),
  HIGH(1);
  public final int key;

  Priority(int key) {
    this.key = key;
  }
}
