package com.bharath.junit;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/coupon")
public class CouponServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("SUPERSALE");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String coupon = request.getParameter("coupon");
		request.setAttribute("discount", "Discount for coupon " + coupon + " is 50%");
		request.getRequestDispatcher("response.jsp").forward(request, response);
	}
}
