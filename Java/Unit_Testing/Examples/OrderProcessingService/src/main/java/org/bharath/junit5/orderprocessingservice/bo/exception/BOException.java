package org.bharath.junit5.orderprocessingservice.bo.exception;

import java.io.Serial;
import java.sql.SQLException;

public class BOException extends Exception {

  @Serial
  private static final long serialVersionUID = -7437629874054527224L;

  public BOException(SQLException e) {

    super(e);
  }

}
