package guru.springframework.testing.junit4;

import static org.junit.Assert.*;

import org.junit.Test;

public class JavaHelloWorldTest {

  @Test
  public void getHello() {
    assertEquals("Hello World", new JavaHelloWorld().getHello());
  }

}