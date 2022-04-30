import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaHelloWorldJUnit5Test {

  @Test
  void getHello() {
    assertEquals("Hello World", new JavaHelloWorld().getHello());
  }
}