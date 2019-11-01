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
import com.oop.model.Payment;
import com.oop.service.PaymentServiceImpl;
import com.oop.service.IPaymentService;

/**
 * Update employees servlet
 */
public class UpdatePaymentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdatePaymentServlet() {
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
		Payment payment = new Payment();
		String pid = request.getParameter("pid");	
		payment.setpid(pid);
		payment.setName(request.getParameter("name"));
		payment.setCardNumber(request.getParameter("cardNumber"));
		payment.setDate(request.getParameter("date"));
		payment.setYear(request.getParameter("year"));
		payment.setCvc(request.getParameter("cvc"));

		
		IPaymentService iPaymentService = new PaymentServiceImpl();
		iPaymentService.updatePayment(pid, payment);
*/
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3006/onereddak";
		String user = "root";
		String psw = "root";
		
		String pid = request.getParameter("pid");
		String name=request.getParameter("name");
		String cardNumber=request.getParameter("cardNumber");
		String date=request.getParameter("date");
		String year=request.getParameter("year");
		String cvc=request.getParameter("cvc");
		
		if(pid != null)
		{
		Connection con = null;
		PreparedStatement ps = null;
		int personID = Integer.parseInt(pid);
		try
		{
		Class.forName(driverName);
		con = (Connection) DriverManager.getConnection(url,user,psw);
		String sql="Update payment set pid=?,name=?,cardNumber=?,date=?,year=?,cvc=? where pid="+pid;
		ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1,pid);
		ps.setString(2, name);
		ps.setString(3, cardNumber);
		ps.setString(4, date);
		ps.setString(5, year);
		ps.setString(6, cvc);
		
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
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editRemovePayment.jsp");
		dispatcher.forward(request, response);
	}

}
