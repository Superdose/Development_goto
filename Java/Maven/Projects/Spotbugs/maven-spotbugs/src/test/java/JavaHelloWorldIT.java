import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaHelloWorldIT {

  @Test
  void getHello() {

    assertEquals("Hello World", new JavaHelloWorld().getHello());
  }
}