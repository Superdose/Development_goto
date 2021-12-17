package org.bharath.mockito.spy;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
public class ListTest {

  // @Mock marks the field to be mocked
  @Mock
  private List<String> myList = new ArrayList<>();

  // @BeforeEach marks this method to be called before each test method once
  @BeforeEach
  public void init() {

    // MockitoAnnotations.openMocks(this); enables the Mockito specific Annotations inside
    // this class
    MockitoAnnotations.openMocks(this);
  }

  // @Test marks the method as a test method
  @Test
  public void test() {
    this.myList.add("Bharath");
    this.myList.add("Sarath");

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    Mockito.when(this.myList.get(0)).thenReturn("Rambo");

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    Mockito.when(this.myList.size()).thenReturn(3);
    //Mockito.doReturn(4).when(this.myList).size();

    //Mockito.when(this.myList.size()).thenCallRealMethod();

    // Checks whether the expected and given value are the "same" (context: ==)
    assertSame(3, this.myList.size());
  }
}
