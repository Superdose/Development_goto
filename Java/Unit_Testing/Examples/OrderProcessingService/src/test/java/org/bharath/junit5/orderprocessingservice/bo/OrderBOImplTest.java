package org.bharath.junit5.orderprocessingservice.bo;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.bharath.junit5.orderprocessingservice.bo.exception.BOException;
import org.bharath.junit5.orderprocessingservice.dao.OrderDAO;
import org.bharath.junit5.orderprocessingservice.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
class OrderBOImplTest {

  public static final int ORDER_ID = 123;

  // @Mock marks the field to be mocked
  @Mock
  private OrderDAO dao;

  private OrderBOImpl orderBO;

  // @BeforeEach marks this method to be called before each test method once
  @BeforeEach
  public void setup() {

    // MockitoAnnotations.openMocks(this); enables the Mockito specific Annotations inside
    // this class
    MockitoAnnotations.openMocks(this);

    this.orderBO = new OrderBOImpl();
    this.orderBO.setDao(this.dao);
  }

  // @Test marks the method as a test method
  @Test
  public void placeOrderShouldCreateAnOrder() throws SQLException, BOException {
    Order order = new Order();

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.dao.create(any(Order.class))).thenReturn(1);
    //when(this.dao.create(order)).thenReturn(Integer.valueOf(1));

    boolean result = this.orderBO.placeOrder(order);

    // checks whether the value is true
    assertTrue(result);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao, atLeast(1)).create(order);
    // verify(this.dao, times(1)).create(order);
    // verify(this.dao, atLeast(1)).create(order);
  }

  // @Test marks the method as a test method
  @Test
  public void placeOrderShouldNotCreateAnOrder() throws SQLException, BOException {
    Order order = new Order();

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.dao.create(order)).thenReturn(Integer.valueOf(0));

    boolean result = this.orderBO.placeOrder(order);

    // checks whether the value is false
    assertFalse(result);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).create(order);
  }
  // @Test marks the method as a test method
  @Test
  public void placeOrderShouldThrowAnBOException() throws SQLException {
    Order order = new Order();

    // Mocks the given method
    // and returns for the corresponding call the specified exception
    when(this.dao.create(order)).thenThrow(SQLException.class);

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(BOException.class, () -> {
      this.orderBO.placeOrder(order);
    });

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).create(order);
  }

  // @Test marks the method as a test method
  @Test
  public void cancelOrderShouldCancelTheOrder() throws SQLException, BOException {
    Order order = new Order();

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.dao.read(ORDER_ID)).thenReturn(order);
    when(this.dao.update(order)).thenReturn(1);

    boolean result = this.orderBO.cancelOrder(ORDER_ID);

    // checks whether the value is true
    assertTrue(result);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).read(ORDER_ID);
    verify(this.dao).update(order);
  }

  // @Test marks the method as a test method
  @Test
  public void cancelOrderShouldFailToUpdate() throws SQLException, BOException {
    Order order = new Order();

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.dao.read(ORDER_ID)).thenReturn(order);
    when(this.dao.update(order)).thenReturn(0);

    boolean result = this.orderBO.cancelOrder(ORDER_ID);

    // checks whether the value is false
    assertFalse(result);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).read(ORDER_ID);
    verify(this.dao).update(order);
  }

  // @Test marks the method as a test method
  @Test
  public void cancelOrderShouldThrowAnBOExceptionOnRead() throws SQLException {

    // Mocks the given method
    // and returns for the corresponding call the specified exception
    when(this.dao.read(anyInt())).thenThrow(SQLException.class);
    //when(this.dao.read(ORDER_ID)).thenThrow(SQLException.class);

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(BOException.class, () -> {
      this.orderBO.cancelOrder(ORDER_ID);
    });

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).read(anyInt());
    //verify(this.dao).read(ORDER_ID);

  }

  // @Test marks the method as a test method
  @Test
  public void cancelOrderShouldThrowAnBOExceptionOnUpdate() throws SQLException {
    Order order = new Order();

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.dao.read(ORDER_ID)).thenReturn(order);

    // Mocks the given method
    // and returns for the corresponding call the specified exception
    when(this.dao.update(order)).thenThrow(SQLException.class);

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(BOException.class, () -> {
      this.orderBO.cancelOrder(ORDER_ID);
    });

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).read(ORDER_ID);
    verify(this.dao).update(order);
  }

  // @Test marks the method as a test method
  @Test
  public void deleteOrderShouldDeleteTheOrder() throws SQLException, BOException {

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.dao.delete(ORDER_ID)).thenReturn(1);

    boolean result = this.orderBO.deleteOrder(ORDER_ID);

    // checks whether the value is true
    assertTrue(result);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).delete(ORDER_ID);
  }

  // @Test marks the method as a test method
  @Test
  public void deleteOrderShouldNotDeleteTheOrder() throws SQLException, BOException {

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    when(this.dao.delete(ORDER_ID)).thenReturn(0);

    boolean result = this.orderBO.deleteOrder(ORDER_ID);

    // checks whether the value is false
    assertFalse(result);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).delete(ORDER_ID);
  }

  // @Test marks the method as a test method
  @Test
  public void deleteOrderShouldThrowAnBOException() throws SQLException {

    // Mocks the given method
    // and returns for the corresponding call the specified exception
    when(this.dao.delete(ORDER_ID)).thenThrow(SQLException.class);

    // Calls the to be tested method in the to be tested class and will assert, whether
    // the specified exception is thrown or not
    assertThrows(BOException.class, () -> {
      this.orderBO.deleteOrder(ORDER_ID);
    });

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    verify(this.dao).delete(ORDER_ID);
  }

}