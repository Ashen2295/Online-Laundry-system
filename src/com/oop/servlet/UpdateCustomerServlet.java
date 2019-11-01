package com.oop.servlet;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.oop.model.Customer;
import com.oop.service.CustomerServiceImpl;
import com.oop.service.ICustomerService;

/**
 * Update employees servlet
 */
public class UpdateCustomerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCustomerServlet() {
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

		response.setContentType("text/html");
/*
		Customer customer = new Customer();
		String pid = request.getParameter("pid");	
		customer.setpid(pid);
		customer.setName(request.getParameter("name"));
		customer.setCardNumber(request.getParameter("cardNumber"));
		customer.setDate(request.getParameter("date"));
		customer.setYear(request.getParameter("year"));
		customer.setCvc(request.getParameter("cvc"));

		
		ICustomerService iCustomerService = new CustomerServiceImpl();
		iCustomerService.updateCustomer(pid, customer);
*/
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3006/onereddak";
		String user = "root";
		String psw = "root";
		
		String nic = request.getParameter("nic");
		String name= request.getParameter("name");
		String email= request.getParameter("email");
		
		
		if(nic != null)
		{
		Connection con = null;
		PreparedStatement ps = null;
		int CustID = Integer.parseInt(nic);
		try
		{
		Class.forName(driverName);
		con = (Connection) DriverManager.getConnection(url,user,psw);
		String sql = "Update customer set nic=?,name=?,email=? where nic="+request.getParameter("nic");
		ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1,nic);
		ps.setString(2, name);
		ps.setString(3, email);
	
		
		int i = ps.executeUpdate();
		if(i > 0)
		{
		
		}
		else
		{
		
		}
		}
		catch(SQLException sql)
		{
		request.setAttribute("error", sql);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editCustomer.jsp");
		dispatcher.forward(request, response);
	}

}
