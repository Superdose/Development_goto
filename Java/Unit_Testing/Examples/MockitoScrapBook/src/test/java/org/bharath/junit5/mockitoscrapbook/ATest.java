package org.bharath.junit5.mockitoscrapbook;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

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
class ATest {

  // @Mock marks the field to be mocked
  @Mock
  private B b;

  // @InjectMocks injects all mocked fields, that exist inside the @InjectMocks-field
  @InjectMocks
  private A a;

  // @Test marks the method as a test method
  @Test
  public void usesVoidMethodShouldCallTheVoidMethod() throws Exception {
    //a.usesVoidMethod();

    // Void methods are by default ignored when testing (concerning mocked dependencies)
    // doNothing() explicitly states to do nothing, when this method of the mocked field is called
    doNothing().when(this.b).voidMethod();

    // Checks whether the expected and given value are the "same" (context: ==)
    assertSame(1, a.usesVoidMethod());

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(b).voidMethod();

  }

  // @Test marks the method as a test method
  @Test
  public void testConsecutiveCalls() throws Exception {

    // Void methods are by default ignored when testing (concerning mocked dependencies)
    // doNothing() explicitly states to do nothing, when this method of the mocked field is called
    // DoThrow throws an exception, if the specified method is called
    // Here two statements regarding are given regarding what to do when the specified method
    // is called
    // In this case, when the method is called first, the first statement (here: doNothing())
    // will be executed and when called the second time, the second statement will be executed
    // (here: doThrow(Exception.class))
    doNothing().doThrow(Exception.class).when(this.b).voidMethod();

    this.a.usesVoidMethod();

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(Exception.class, () -> {
      this.a.usesVoidMethod();
    });

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    // In this case it is also tested, whether the specified method was called 2 times
    verify(this.b, times(2)).voidMethod();
  }
}