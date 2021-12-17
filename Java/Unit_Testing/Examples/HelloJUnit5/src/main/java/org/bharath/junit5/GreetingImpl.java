package org.bharath.junit5;

public class GreetingImpl implements Greeting {

  private GreetingService service;

  @Override
  public String greet(String name) {
    return this.service.greet(name);
  }
}
