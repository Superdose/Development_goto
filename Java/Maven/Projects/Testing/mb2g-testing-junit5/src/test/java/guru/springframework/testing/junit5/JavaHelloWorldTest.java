package guru.springframework.testing.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaHelloWorldTest {

  @Test
  public void getHelloTest() {
    assertEquals("Hello World", new JavaHelloWorld().getHello());
  }

}