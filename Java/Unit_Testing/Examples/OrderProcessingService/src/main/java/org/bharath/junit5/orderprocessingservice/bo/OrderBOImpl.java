package org.bharath.junit5.orderprocessingservice.bo;

import java.sql.SQLException;
import lombok.Data;
import org.bharath.junit5.orderprocessingservice.bo.exception.BOException;
import org.bharath.junit5.orderprocessingservice.dao.OrderDAO;
import org.bharath.junit5.orderprocessingservice.dto.Order;

@Data
public class OrderBOImpl implements OrderBO{

  private OrderDAO dao;

  @Override
  public boolean placeOrder(Order order) throws BOException {
    try {

      int result = this.dao.create(order);
      if(result == 0) return false;

    } catch (SQLException e) {
      throw new BOException(e);
    }

    return true;
  }

  @Override
  public boolean cancelOrder(int id) throws BOException {
    try {

      Order order = this.dao.read(id);
      order.setStatus("cancelled");
      int result = this.dao.update(order);

      if(result == 0) return false;

    } catch (SQLException e) {
      throw new BOException(e);
    }

    return true;
  }

  @Override
  public boolean deleteOrder(int id) throws BOException {
    try {

      int result = this.dao.delete(id);
      if (result == 0) return false;

    } catch (SQLException e) {
      throw new BOException(e);
    }

    return true;
  }
}
