package org.bharath.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// @ExtendWith(MockitoExtension.class) enables the @Mock and @InjectMocks annotations
// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
@ExtendWith(MockitoExtension.class)
public class GreetingImplTest {

  // @Mock marks the field to be mocked
  @Mock
  private GreetingService service;

  // @InjectMocks injects all mocked fields, that exist inside the @InjectMocks-field
  @InjectMocks
  private GreetingImpl greeting;

  // @Test marks the method as a test method
  @Test
  public void greetShouldReturnAValidOutput() {
    System.out.println("greetShouldReturnAValidOutput");

    String name = "JUnit";

    // Mocks the "this.service.greet(name)" method
    // and returns for the corresponding call the String "Hello JUnit"
    when(this.service.greet(name)).thenReturn("Hello JUnit");

    String result = this.greeting.greet(name);

    // checks whether the value is null or not
    assertNotNull(result);

    // checks whether the expected and given value are the "same"
    assertEquals("Hello JUnit", result);
  }

  // @Test marks the method as a test method
  @Test
  public void greetShouldThrowAnException_For_NameIsNull() {
    System.out.println("greetShouldThrowAnException_For_NameIsNull");

    // Throws the specified exception, when the specified method of the mocked field is called
    doThrow(IllegalArgumentException.class).when(this.service).greet(null);
    //when(this.service.greet(null)).thenThrow(IllegalArgumentException.class);

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      this.greeting.greet(null);
    });
  }

  // @Test marks the method as a test method
  @Test
  public void greetShouldThrowAnException_For_NameIsBlank() {
    System.out.println("greetShouldThrowAnException_For_NameIsBlank");

    // Throws the specified exception, when the specified method of the mocked field is called
    doThrow(IllegalArgumentException.class).when(this.service).greet("");
    //when(this.service.greet("")).thenThrow(IllegalArgumentException.class);

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      this.greeting.greet("");
    });
  }

}