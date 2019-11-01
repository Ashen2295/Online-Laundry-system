/**
 * OOP 2018
 * 
 * @author Udara Samaratunge  Department of Software Engineering, SLIIT 
 * 
 * @version 1.0
 * Copyright: SLIIT, All rights reserved
 * 
 */
package com.oop.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.oop.model.Admin;
import com.oop.util.CommonConstants;
import com.oop.util.CommonUtil;
import com.oop.util.DBConnectionUtil;
import com.oop.util.QueryUtil;

/**
 * Contract for the implementation of Admin Service .
 * 
 * @author Udara Samaratunge, SLIIT
 * @version 1.0
 */
public class AdminServiceImpl implements IAdminService {
	

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(AdminServiceImpl.class.getName());

	private static Connection connection;

	private static Statement statement;

	static{
		//create table or drop if exist
		createAdminTable();
	}

	private PreparedStatement preparedStatement;

	/**
	 * This method initially drop existing Admins table in the database and
	 * recreate table structure to insert admin entries
	 * 
	 * @throws SQLException
	 *             - Thrown when database access error occurs or this method is
	 *             called on a closed connection
	 * @throws ClassNotFoundException
	 *             - Thrown when an application tries to load in a class through
	 *             its string name using
	 * @throws SAXException
	 *             - Encapsulate a general SAX error or warning
	 * @throws IOException
	 *             - Exception produced by failed or interrupted I/O operations.
	 * @throws ParserConfigurationException
	 *             - Indicates a serious configuration error
	 * @throws NullPointerException
	 *             - Service is not available
	 * 
	 */
	public static void createAdminTable() {

		try {
			connection = DBConnectionUtil.getDBConnection();
			statement = connection.createStatement();
			// Drop table if already exists and as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_DROP_TABLE));
			// Create new admins table as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_CREATE_TABLE));

		} catch (SQLException | SAXException | IOException | ParserConfigurationException | ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Add set of admins for as a batch from the selected admin List
	 * 
	 * @throws SQLException
	 *             - Thrown when database access error occurs or this method is
	 *             called on a closed connection
	 * @throws SAXException
	 *             - Encapsulate a general SAX error or warning
	 * @throws IOException
	 *             - Exception produced by failed or interrupted I/O operations.
	 * @throws ParserConfigurationException
	 *             - Indicates a serious configuration error.
	 * 
	 */
	@Override
	public void addAdmin(Admin admin) {

		String name = CommonUtil.generateIDs(getAdminIDs());
		
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Query is available in AdminQuery.xml file and use
			 * insert_admin key to extract value of it
			 */
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_INSERT_ADMIN));
			connection.setAutoCommit(false);
			
			//Generate admin IDs
			admin.setName(name);
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, admin.getName());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, admin.getType());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, admin.getDescription());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, admin.getPrice());


			// Add admin
			preparedStatement.execute();
			connection.commit();

		} catch (SQLException | SAXException | IOException | ParserConfigurationException | ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
		} finally {
			/*
			 * Close prepared statement and database connectivity at the end of
			 * transaction
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
	}

	/**
	 * Admin details can be retrieved based on the provided admin ID
	 * 
	 * @param adminID
	 *            - Admin details are filtered based on the ID
	 * 
	 * @see #actionOnAdmin()
	 */
	@Override
	public Admin getAdminByID(String name) {

		return actionOnAdmin(name).get(0);
	}
	
	/**
	 * Get all list of admins
	 * 
	 * @return ArrayList<Admin> 
	 * 						- Array of admin list will be return
	 * 
	 * @see #actionOnAdmin()
	 */
	@Override
	public ArrayList<Admin> getAdmin() {
		
		return actionOnAdmin(null);
	}

	/**
	 * This method delete an admin based on the provided ID
	 * 
	 * @param adminID
	 *            - Delete admin according to the filtered admin details
	 * @throws SQLException
	 *             - Thrown when database access error occurs or this method is
	 *             called on a closed connection
	 * @throws ClassNotFoundException
	 *             - Thrown when an application tries to load in a class through
	 *             its string name using
	 * @throws SAXException
	 *             - Encapsulate a general SAX error or warning
	 * @throws IOException
	 *             - Exception produced by failed or interrupted I/O operations.
	 * @throws ParserConfigurationException
	 *             - Indicates a serious configuration error.
	 * @throws NullPointerException
	 *             - Service is not available
	 */
	@Override
	public void removeAdmin(String name) {

		// Before deleting check whether admin ID is available
		if (name != null && !name.isEmpty()) {
			/*
			 * Remove admin query will be retrieved from AdminQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_REMOVE_ADMIN));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, name);
				preparedStatement.executeUpdate();
			} catch (SQLException | SAXException | IOException | ParserConfigurationException
					| ClassNotFoundException e) {
				log.log(Level.SEVERE, e.getMessage());
			} finally {
				/*
				 * Close prepared statement and database connectivity at the end
				 * of transaction
				 */
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					log.log(Level.SEVERE, e.getMessage());
				}
			}
		}
	}

	/**
	 * This performs GET admin by ID and Display all admins
	 * 
	 * @param adminID
	 *            ID of the admin to remove or select from the list

	 * @return ArrayList<Admin> Array of admin list will be return
	 * 
	 * @throws SQLException
	 *             - Thrown when database access error occurs or this method is
	 *             called on a closed connection
	 * @throws ClassNotFoundException
	 *             - Thrown when an application tries to load in a class through
	 *             its string name using
	 * @throws SAXException
	 *             - Encapsulate a general SAX error or warning
	 * @throws IOException
	 *             - Exception produced by failed or interrupted I/O operations.
	 * @throws ParserConfigurationException
	 *             - Indicates a serious configuration error.
	 * @throws NullPointerException
	 *             - Service is not available
	 * 
	 * @see #getAdmins()
	 * @see #getAdminByID(String)
	 */
	private ArrayList<Admin> actionOnAdmin(String name) {

		ArrayList<Admin> adminList = new ArrayList<Admin>();
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Before fetching admin it checks whether admin ID is
			 * available
			 */
			if (name != null && !name.isEmpty()) {
				/*
				 * Get admin by ID query will be retrieved from
				 * AdminQuery.xml
				 */
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_ADMIN));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, name);
			}
			/*
			 * If admin ID is not provided for get admin option it display
			 * all admins
			 */
			else {
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_ALL_ADMIN));
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Admin admin = new Admin();
				admin.setName(resultSet.getString(CommonConstants.COLUMN_INDEX_ONE));
				admin.setType(resultSet.getString(CommonConstants.COLUMN_INDEX_TWO));
				admin.setDescription(resultSet.getString(CommonConstants.COLUMN_INDEX_THREE));
				admin.setPrice(resultSet.getString(CommonConstants.COLUMN_INDEX_FOUR));
				adminList.add(admin);
			}

		} catch (SQLException | SAXException | IOException | ParserConfigurationException | ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
		} finally {
			/*
			 * Close prepared statement and database connectivity at the end of
			 * transaction
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
		return adminList;
	}

	/**
	 * Get the updated admin
	 * 
	 * @param adminID
	 *            ID of the admin to remove or select from the list
	 * 
	 * @return return the Admin object
	 * 
	 */
	@Override
	public Admin updateAdmin(String name, Admin admin) {

		/*
		 * Before fetching admin it checks whether admin ID is available
		 */
		if (name != null && !name.isEmpty()) {
			/*
			 * Update admin query will be retrieved from AdminQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_UPDATE_ADMIN));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, admin.getName());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, admin.getType());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, admin.getDescription());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, admin.getPrice());

				preparedStatement.executeUpdate();

			} catch (SQLException | SAXException | IOException | ParserConfigurationException
					| ClassNotFoundException e) {
				log.log(Level.SEVERE, e.getMessage());
			} finally {
				/*
				 * Close prepared statement and database connectivity at the end
				 * of transaction
				 */
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					log.log(Level.SEVERE, e.getMessage());
				}
			}
		}
		// Get the updated admin
		return getAdminByID(name);
	}
	
	/**
	 *
	 * @return ArrayList<String> Array of admin id list will be return
	 * 
	 * @throws SQLException
	 *             - Thrown when database access error occurs or this method is
	 *             called on a closed connection
	 * @throws ClassNotFoundException
	 *             - Thrown when an application tries to load in a class through
	 *             its string name using
	 * @throws SAXException
	 *             - Encapsulate a general SAX error or warning
	 * @throws IOException
	 *             - Exception produced by failed or interrupted I/O operations.
	 * @throws ParserConfigurationException
	 *             - Indicates a serious configuration error.
	 * @throws NullPointerException
	 *             - Service is not available
	 */
	private ArrayList<String> getAdminIDs(){
		
		ArrayList<String> arrayList = new ArrayList<String>();
		/*
		 * List of admin IDs will be retrieved from AdminQuery.xml
		 */
		try {
			connection = DBConnectionUtil.getDBConnection();
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_ADMIN_IDS));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				arrayList.add(resultSet.getString(CommonConstants.COLUMN_INDEX_ONE));
			}
		} catch (SQLException | SAXException | IOException | ParserConfigurationException
				| ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
		} finally {
			/*
			 * Close prepared statement and database connectivity at the end of
			 * transaction
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
		return arrayList;
	}
}
