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

import com.oop.model.Customer;
import com.oop.util.CommonConstants;
import com.oop.util.CommonUtil;
import com.oop.util.DBConnectionUtil;
import com.oop.util.QueryUtil;

/**
 * Contract for the implementation of Customer Service .
 * 
 * @author Udara Samaratunge, SLIIT
 * @version 1.0
 */
public class CustomerServiceImpl implements ICustomerService {
	

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(CustomerServiceImpl.class.getName());

	private static Connection connection;

	private static Statement statement;

	static{
		//create table or drop if exist
		createCustomerTable();
	}

	private PreparedStatement preparedStatement;

	/**
	 * This method initially drop existing Customers table in the database and
	 * recreate table structure to insert customer entries
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
	public static void createCustomerTable() {

		try {
			connection = DBConnectionUtil.getDBConnection();
			statement = connection.createStatement();
			// Drop table if already exists and as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_DROP_TABLE));
			// Create new customers table as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_CREATE_TABLE));

		} catch (SQLException | SAXException | IOException | ParserConfigurationException | ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Add set of customers for as a batch from the selected customer List
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
	public void addCustomer(Customer customer) {

		String Nic = CommonUtil.generateIDs(getCustomerIDs());
		
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Query is available in CustomerQuery.xml file and use
			 * insert_customer key to extract value of it
			 */
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_INSERT_CUSTOMER));
			connection.setAutoCommit(false);
			
			//Generate customer IDs
			customer.setNic(Nic);
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, customer.getNic());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, customer.getName());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, customer.getEmail());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, customer.getPassword());


			// Add customer
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
	 * Customer details can be retrieved based on the provided customer ID
	 * 
	 * @param customerID
	 *            - Customer details are filtered based on the ID
	 * 
	 * @see #actionOnCustomer()
	 */
	@Override
	public Customer getCustomerByID(String Nic) {

		return actionOnCustomer(Nic).get(0);
	}
	
	/**
	 * Get all list of customers
	 * 
	 * @return ArrayList<Customer> 
	 * 						- Array of customer list will be return
	 * 
	 * @see #actionOnCustomer()
	 */
	@Override
	public ArrayList<Customer> getCustomer() {
		
		return actionOnCustomer(null);
	}

	/**
	 * This method delete an customer based on the provided ID
	 * 
	 * @param customerID
	 *            - Delete customer according to the filtered customer details
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
	public void removeCustomer(String Nic) {

		// Before deleting check whether customer ID is available
		if (Nic != null && !Nic.isEmpty()) {
			/*
			 * Remove customer query will be retrieved from CustomerQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_REMOVE_CUSTOMER));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, Nic);
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
	 * This performs GET customer by ID and Display all customers
	 * 
	 * @param customerID
	 *            ID of the customer to remove or select from the list

	 * @return ArrayList<Customer> Array of customer list will be return
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
	 * @see #getCustomers()
	 * @see #getCustomerByID(String)
	 */
	private ArrayList<Customer> actionOnCustomer(String Nic) {

		ArrayList<Customer> customerList = new ArrayList<Customer>();
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Before fetching customer it checks whether customer ID is
			 * available
			 */
			if (Nic != null && !Nic.isEmpty()) {
				/*
				 * Get customer by ID query will be retrieved from
				 * CustomerQuery.xml
				 */
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_CUSTOMER));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, Nic);
			}
			/*
			 * If customer ID is not provided for get customer option it display
			 * all customers
			 */
			else {
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_ALL_CUSTOMER));
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setNic(resultSet.getString(CommonConstants.COLUMN_INDEX_ONE));
				customer.setName(resultSet.getString(CommonConstants.COLUMN_INDEX_TWO));
				customer.setEmail(resultSet.getString(CommonConstants.COLUMN_INDEX_THREE));
				customer.setPassword(resultSet.getString(CommonConstants.COLUMN_INDEX_FOUR));
				customerList.add(customer);
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
		return customerList;
	}

	/**
	 * Get the updated customer
	 * 
	 * @param customerID
	 *            ID of the customer to remove or select from the list
	 * 
	 * @return return the Customer object
	 * 
	 */
	@Override
	public Customer updateCustomer(String Nic, Customer customer) {

		/*
		 * Before fetching customer it checks whether customer ID is available
		 */
		if (Nic != null && !Nic.isEmpty()) {
			/*
			 * Update customer query will be retrieved from CustomerQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_UPDATE_CUSTOMER));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, customer.getNic());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, customer.getName());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, customer.getEmail());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, customer.getPassword());

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
		// Get the updated customer
		return getCustomerByID(Nic);
	}
	
	/**
	 *
	 * @return ArrayList<String> Array of customer id list will be return
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
	private ArrayList<String> getCustomerIDs(){
		
		ArrayList<String> arrayList = new ArrayList<String>();
		/*
		 * List of customer IDs will be retrieved from CustomerQuery.xml
		 */
		try {
			connection = DBConnectionUtil.getDBConnection();
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_CUSTOMER_IDS));
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
