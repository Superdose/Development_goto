package guru.springframework.testing.junit4and5;

import static org.junit.Assert.*;

import org.junit.Test;

public class JavaHelloWorldUnit4Test {

  @Test
  public void getHello() {
    assertEquals("Hello World", new JavaHelloWorld().getHello());
  }
}