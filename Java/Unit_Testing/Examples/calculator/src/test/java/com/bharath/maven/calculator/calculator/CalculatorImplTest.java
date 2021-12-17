package com.bharath.maven.calculator.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
class CalculatorImplTest {

  // Preparing this static method to use it for a parameterized test
  // "Arguments" -> You can use a not specified amount of args
  // -> These args can also be of different types
  public static Stream<Arguments> data() {
    return Stream.of(
        Arguments.arguments(-1, 2, 1),
        Arguments.arguments(1, 2, 3),
        Arguments.arguments(6, 7, 13),
        Arguments.arguments(10, 20, 30)
    );
  }

  // @ParameterizedTest marks this method as a ParameterizedTest
  // @MethodSource("data") tells this method to use the static data() method as a source of
  // values for the parameterized test
  // The values are inserted via method arguments
  @ParameterizedTest
  @MethodSource("data")
  public void addShouldReturnAResult(int num1, int num2, int expectedResult) {
    Calculator calc = new CalculatorImpl();

    int result = calc.add(num1, num2);

    // checks whether the expected and given value are the "same"
    assertEquals(expectedResult, result);
  }

}