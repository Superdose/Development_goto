package com.bharath.powermock.useradminservice.util;

public final class IDGenerator {

  private static int i;

  public static final int generateID() {
    return IDGenerator.i++;
  }
}
