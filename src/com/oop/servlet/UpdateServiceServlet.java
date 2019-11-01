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
import com.oop.model.Services;
import com.oop.service.ServiceServiceImpl;
import com.oop.service.IServiceService;

/**
 * Update employees servlet
 */
public class UpdateServiceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateServiceServlet() {
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
		Services services = new Services();
		String nic = request.getParameter("nic");	
		services.setNic(nic);
		services.setName(request.getParameter("name"));
		services.setServiceName(request.getParameter("serviceName"));
		services.setAddedDate(request.getParameter("addedDate"));
		
		
		IServiceService iServiceService = new ServiceServiceImpl();
		iServiceService.updateServices(nic, services);
*/
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3006/onereddak";
		String user = "root";
		String psw = "root";
		
		String nic = request.getParameter("nic");
		String name=request.getParameter("name");
		String serviceName=request.getParameter("serviceName");
		String addedDate=request.getParameter("addedDate");
		
		if(nic != null)
		{
		Connection con = null;
		PreparedStatement ps = null;
		int personID = Integer.parseInt(nic);
		try
		{
		Class.forName(driverName);
		con = (Connection) DriverManager.getConnection(url,user,psw);
		String sql="Update service set nic=?,name=?,serviceName=?,addedDate=? where nic="+nic;
		ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1,nic);
		ps.setString(2, name);
		ps.setString(3, serviceName);
		ps.setString(4, addedDate);
		
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
		
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/ListServices.jsp");
		dispatcher.forward(request, response);
	}

}
