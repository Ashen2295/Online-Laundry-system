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


import com.oop.model.Admin;
import com.oop.service.AdminServiceImpl;
import com.oop.service.IAdminService;

/**
 * Servlet implementation class LoginServlet
 */
public class AddAdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAdminServlet() {
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
		
		Admin admin = new Admin();
		
		admin.setName(request.getParameter("name"));
		admin.setType(request.getParameter("type"));
		admin.setDescription(request.getParameter("desc"));
		admin.setPrice(request.getParameter("price"));
		

		int i=st.executeUpdate("insert into admin(name,type,description,price)values('"+request.getParameter("name")+"','"+request.getParameter("type")+"','"+request.getParameter("desc")+"','"+request.getParameter("price")+"')");
		
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
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/ListAdmin.jsp");
		dispatcher.forward(request, response);
	}

}
