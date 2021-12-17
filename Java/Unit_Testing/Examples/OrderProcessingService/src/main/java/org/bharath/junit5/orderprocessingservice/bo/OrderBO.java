package org.bharath.junit5.orderprocessingservice.bo;

import org.bharath.junit5.orderprocessingservice.bo.exception.BOException;
import org.bharath.junit5.orderprocessingservice.dto.Order;

public interface OrderBO {
  boolean placeOrder(Order order) throws BOException;
  boolean cancelOrder(int id) throws BOException;
  boolean deleteOrder(int id) throws BOException;
}
