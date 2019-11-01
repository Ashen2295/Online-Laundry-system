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

import com.oop.model.Admin;
import com.oop.service.AdminServiceImpl;
import com.oop.service.IAdminService;

/**
 * Update employees servlet
 */
public class UpdateAdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateAdminServlet() {
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
		/*
		response.setContentType("text/html");

		Admin admin = new Admin();
		String name = request.getParameter("name");	
		admin.setName(name);
		admin.setType(request.getParameter("type"));
		admin.setDescription(request.getParameter("desc"));
		admin.setPrice(request.getParameter("price"));
		
		
		IAdminService iAdminService = new AdminServiceImpl();
		iAdminService.updateAdmin(name, admin);
		
		*/
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3006/onereddak";
		String user = "root";
		String psw = "root";
		
		String name = request.getParameter("name");
		String type=request.getParameter("type");
		String description=request.getParameter("desc");
		String price=request.getParameter("price");
		
		if(name != null)
		{
		Connection con = null;
		PreparedStatement ps = null;
		int personID = Integer.parseInt(name);
		try
		{
		Class.forName(driverName);
		con = (Connection) DriverManager.getConnection(url,user,psw);
		String sql="Update admin set name=?,type=?,description=?,price=? where name="+name;
		ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1,name);
		ps.setString(2, type);
		ps.setString(3, description);
		ps.setString(4, price);
		
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
		
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/ListAdmin.jsp");
		dispatcher.forward(request, response);
	}

}
