package org.bharath.junit5;

public class GreetingServiceImpl implements GreetingService{

  @Override
  public String greet(String name) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException();
    }

    return "Hello " + name;
  }
}
