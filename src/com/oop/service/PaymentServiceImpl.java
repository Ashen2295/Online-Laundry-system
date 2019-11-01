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

import com.oop.model.Payment;
import com.oop.util.CommonConstants;
import com.oop.util.CommonUtil;
import com.oop.util.DBConnectionUtil;
import com.oop.util.QueryUtil;

public class PaymentServiceImpl implements IPaymentService {
	

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(PaymentServiceImpl.class.getName());

	private static Connection connection;

	private static Statement statement;

	static{
		//create table or drop if exist
		createPaymentTable();
	}

	private PreparedStatement preparedStatement;

	/**
	 * This method initially drop existing Employees table in the database and
	 * recreate table structure to insert employee entries
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
	public static void createPaymentTable() {

		try {
			connection = DBConnectionUtil.getDBConnection();
			statement = connection.createStatement();
			// Drop table if already exists and as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_DROP_TABLE));
			// Create new payment table as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_CREATE_TABLE));

		} catch (SQLException | SAXException | IOException | ParserConfigurationException | ClassNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Add set of employees for as a batch from the selected employee List
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
	public void addPayment(Payment payment) {

		String pid = CommonUtil.generateIDs(getPaymentIDs());
		
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Query is available in Query.xml file and use
			 * insert_payment key to extract value of it
			 */
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_INSERT_PAYMENT));
			connection.setAutoCommit(false);
			
			//Generate employee IDs
			payment.setpid(pid);
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, payment.getpid());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, payment.getName());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, payment.getCardNumber());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, payment.getDate());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_FIVE, payment.getYear());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_SIX, payment.getCvc());

			// Add employee
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
	 * Employee details can be retrieved based on the provided employee ID
	 * 
	 * @param employeeID
	 *            - Employee details are filtered based on the ID
	 * 
	 * @see #actionOnEmployee()
	 */
	@Override
	public Payment getPaymentByID(String pid) {

		return actionOnPayment(pid).get(0);
	}
	
	/**
	 * Get all list of payment
	 * 
	 * @return ArrayList<Payment> 
	 * 						- Array of employee list will be return
	 * 
	 * @see #actionOnPayment()
	 */
	
	public ArrayList<Payment> getPayment() {
		
		return actionOnPayment(null);
	}

	/**
	 * This method delete an employee based on the provided ID
	 * 
	 * @param employeeID
	 *            - Delete employee according to the filtered employee details
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
	public void removePayment(String pid) {

		// Before deleting check whether employee ID is available
		
			/*
			 * Remove employee query will be retrieved from EmployeeQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_REMOVE_PAYMENT));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, pid);
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
	


	private ArrayList<Payment> actionOnPayment(String pid) {

		ArrayList<Payment> paymentList = new ArrayList<Payment>();
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Before fetching employee it checks whether employee ID is
			 * available
			 */
			if (pid != null && !pid.isEmpty()) {
				/*
				 * Get payment by ID query will be retrieved from
				 * Query.xml
				 */
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_PAYMENT));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, pid);
			}
			/*
			 * If employee ID is not provided for get employee option it display
			 * all employees
			 */
			else {
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_ALL_PAYMENT));
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Payment payment = new Payment();
				payment.setpid(resultSet.getString(CommonConstants.COLUMN_INDEX_ONE));
				payment.setName(resultSet.getString(CommonConstants.COLUMN_INDEX_TWO));
				payment.setCardNumber(resultSet.getString(CommonConstants.COLUMN_INDEX_THREE));
				payment.setDate(resultSet.getString(CommonConstants.COLUMN_INDEX_FOUR));
				payment.setYear(resultSet.getString(CommonConstants.COLUMN_INDEX_FIVE));
				payment.setCvc(resultSet.getString(CommonConstants.COLUMN_INDEX_SIX));
	
				paymentList.add(payment);
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
		return paymentList;
	}

	/**
	 * Get the updated employee
	 * 
	 * @param employeeID
	 *            ID of the employee to remove or select from the list
	 * 
	 * @return return the Employee object
	 * 
	 */
	public Payment updatePayment(String pid, Payment payment) {

		/*
		 * Before fetching employee it checks whether employee ID is available
		 */
		if (pid != null && !pid.isEmpty()) {
			/*
			 * Update employee query will be retrieved from EmployeeQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_UPDATE_PAYMENT));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, payment.getpid());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, payment.getName());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, payment.getCardNumber());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, payment.getDate());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_FIVE, payment.getYear());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_SIX, payment.getCvc());

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
		// Get the updated employee
		return getPaymentByID(pid);
	}
	
	/**
	 *
	 * @return ArrayList<String> Array of employee id list will be return
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
	private ArrayList<String> getPaymentIDs(){
		
		ArrayList<String> arrayList = new ArrayList<String>();
		/*
		 * List of employee IDs will be retrieved from EmployeeQuery.xml
		 */
		try {
			connection = DBConnectionUtil.getDBConnection();
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_PAYMENT_IDS));
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
