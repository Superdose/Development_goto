package com.bharath.trainings.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
// !! JUNIT4 !!
public class GreetingImplTest {

  private Greeting greeting;

  // @Before marks this method to be called before each test method once
  @Before
  public void setup() {
    System.out.println("Setup");

    this.greeting = new GreetingImpl();
  }

  // @Test marks the method as a test method
  @Test
  public void greetShouldReturnAValidOutput() {
    System.out.println("greetShouldReturnAValidOutput");
    String result = this.greeting.greet("JUnit");

    // checks whether the value is null or not
    assertNotNull(result);

    // checks whether the expected and given value are the "same"
    assertEquals("Hello JUnit", result);
  }

  // @Test marks the method as a test method
  // The "expected" parameter defines the exception-class
  // of the exception that is expected to be thrown in the test method
  @Test(expected = IllegalArgumentException.class)
  public void greetShouldThrowAnException_For_NameIsNull() {
    System.out.println("greetShouldThrowAnException_For_NameIsNull");
    this.greeting.greet(null);
  }

  // @Test marks the method as a test method
  // The "expected" parameter defines the exception-class
  // of the exception that is expected to be thrown in the test method
  @Test(expected = IllegalArgumentException.class)
  public void greetShouldThrowAnException_For_NameIsBlank() {
    System.out.println("greetShouldThrowAnException_For_NameIsBlank");
    this.greeting.greet("");
  }

  // @Before marks this method to be called after each test method once
  @After
  public void teardown() {
    System.out.println("Teardown");
    this.greeting = null;
  }

}