package guru.springframework.testing.pojo;

public class JavaHelloWorldTest {

  public void testgetHello() {
    JavaHelloWorld javaHelloWorld = new JavaHelloWorld();

    assert("Hello World".equals(javaHelloWorld.getHello()));
  }
}