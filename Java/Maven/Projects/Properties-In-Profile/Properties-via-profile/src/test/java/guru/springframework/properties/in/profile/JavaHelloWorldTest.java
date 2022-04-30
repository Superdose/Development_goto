package guru.springframework.properties.in.profile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaHelloWorldTest {

  @Test
  void getHello() {

    System.out.println("#####################");
    System.out.println(System.getenv("TEST_HOST"));

  }
}