package com.oop.servlet;

import java.io.IOException;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.oop.model.Payment;
import com.oop.model.Services;
import com.oop.service.ServiceServiceImpl;
import com.oop.service.IServiceService;

/**
 * Servlet implementation class LoginServlet
 */
public class AddServiceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddServiceServlet() {
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
		
		Services service = new Services();
		
		service.setNic(request.getParameter("nic"));
		service.setName(request.getParameter("name"));
		service.setServiceName(request.getParameter("serviceName"));
		service.setAddedDate(request.getParameter("addedDate"));
		

		int i=st.executeUpdate("insert into service(nic,name,serviceName,addedDate)values('"+request.getParameter("nic")+"','"+request.getParameter("name")+"','"+request.getParameter("serviceName")+"','"+request.getParameter("addedDate")+"')");
		
		}
		catch(Exception e)
		{
		System.out.print(e);
		e.printStackTrace();
		}
		
		
/*
		response.setContentType("text/html");

		Services services = new Services();
		
		services.setNic(request.getParameter("nic"));
		services.setName(request.getParameter("name"));
		services.setServiceName(request.getParameter("serviceName"));
		services.setAddedDate(request.getParameter("addedDate"));
		
		IServiceService iServiceService = new ServiceServiceImpl();
		iServiceService.addServices(services);

		request.setAttribute("services", services);
		*/
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/ListServices.jsp");
		dispatcher.forward(request, response);
	}

}
