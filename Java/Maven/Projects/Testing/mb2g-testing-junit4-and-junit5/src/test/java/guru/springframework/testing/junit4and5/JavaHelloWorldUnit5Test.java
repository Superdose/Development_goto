package guru.springframework.testing.junit4and5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaHelloWorldUnit5Test {

  @Test
  void getHello() {
    assertEquals("Hello World", new JavaHelloWorld().getHello());
  }
}