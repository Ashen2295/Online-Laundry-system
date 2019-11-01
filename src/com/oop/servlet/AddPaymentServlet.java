package com.oop.servlet;

import java.io.IOException;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.oop.model.Payment;
import com.oop.service.PaymentServiceImpl;
import com.oop.service.IPaymentService;

/**
 * Servlet implementation class LoginServlet
 */
public class AddPaymentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddPaymentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3006/onereddak", "root", "root");
		Statement st=(Statement) conn.createStatement();
		
		Payment payment = new Payment();
		
		payment.setpid(request.getParameter("pid"));
		payment.setName(request.getParameter("name"));
		payment.setCardNumber(request.getParameter("cardNumber"));
		payment.setDate(request.getParameter("date"));
		payment.setYear(request.getParameter("year"));
		payment.setCvc(request.getParameter("cvc"));

		int i=st.executeUpdate("insert into payment(pid,name,cardNumber,date,year,cvc)values('"+request.getParameter("pid")+"','"+request.getParameter("name")+"','"+request.getParameter("cardNumber")+"','"+request.getParameter("date")+"','"+request.getParameter("year")+"','"+request.getParameter("cvc")+"')");
		
		}
		catch(Exception e)
		{
		System.out.print(e);
		e.printStackTrace();
		}
		

		
		/*
		response.setContentType("text/html");

		Payment payment = new Payment();
		
		payment.setpid(request.getParameter("pid"));
		payment.setName(request.getParameter("name"));
		payment.setCardNumber(request.getParameter("cardNumber"));
		payment.setDate(request.getParameter("date"));
		payment.setYear(request.getParameter("year"));
		payment.setCvc(request.getParameter("cvc"));

		IPaymentService iPaymentService = new PaymentServiceImpl();
		iPaymentService.addPayment(payment);

		request.setAttribute("payment", payment);
	*/	
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editRemovePayment.jsp");
		dispatcher.forward(request, response);

    
	}

}
