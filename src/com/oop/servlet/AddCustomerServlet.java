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
import com.oop.model.Customer;
import com.oop.service.CustomerServiceImpl;
import com.oop.service.ICustomerService;

/**
 * Servlet implementation class LoginServlet
 */
public class AddCustomerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCustomerServlet() {
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
		
		Customer customer = new Customer();
		
		customer.setNic(request.getParameter("nic"));
		customer.setName(request.getParameter("name"));
		customer.setEmail(request.getParameter("email"));
		customer.setPassword(request.getParameter("password"));


		int i=st.executeUpdate("insert into customer(nic,name,email,password)values('"+request.getParameter("nic")+"','"+request.getParameter("name")+"','"+request.getParameter("email")+"','"+request.getParameter("password")+"')");
		
		}
		catch(Exception e)
		{
		System.out.print(e);
		e.printStackTrace();
		}
		

		
		/*
		response.setContentType("text/html");

		Customer customer = new Customer();
		
		customer.setpid(request.getParameter("pid"));
		customer.setName(request.getParameter("name"));
		customer.setCardNumber(request.getParameter("cardNumber"));
		customer.setDate(request.getParameter("date"));
		customer.setYear(request.getParameter("year"));
		customer.setCvc(request.getParameter("cvc"));

		ICustomerService iCustomerService = new CustomerServiceImpl();
		iCustomerService.addCustomer(customer);

		request.setAttribute("customer", customer);
	*/	
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editCustomer.jsp");
		dispatcher.forward(request, response);

    
	}

}
