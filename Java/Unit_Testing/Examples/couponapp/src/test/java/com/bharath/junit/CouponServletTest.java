package com.bharath.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

// @ExtendWith(MockitoExtension.class) enables the @Mock and @InjectMocks annotations
// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
@ExtendWith(MockitoExtension.class)
class CouponServletTest {

  // @Mock marks the field to be mocked
  @Mock
  private HttpServletRequest request;

  // @Mock marks the field to be mocked
  @Mock
  private HttpServletResponse response;

  // @Mock marks the field to be mocked
  @Mock
  private RequestDispatcher requestDispatcher;

  // @Test marks the method as a test method
  @Test
  public void testDoGet() throws ServletException, IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    Mockito.when(response.getWriter()).thenReturn(printWriter);

    new CouponServlet().doGet(request, response);

    // checks whether the expected and given value are the "same"
    assertEquals("SUPERSALE", stringWriter.toString());
  }

  // @Test marks the method as a test method
  @Test
  public void testDoPost() throws ServletException, IOException {

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    Mockito.when(request.getParameter("coupon")).thenReturn("SUPERSALE");

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    Mockito.when(request.getRequestDispatcher("response.jsp")).thenReturn(requestDispatcher);

    new CouponServlet().doPost(request, response);

    // Verify checks whether the specified method was called during the test
    // Usually methods of mocked fields are verified
    Mockito.verify(request).setAttribute("discount", "Discount for coupon SUPERSALE is 50%");
    Mockito.verify(requestDispatcher).forward(request, response);
  }

}