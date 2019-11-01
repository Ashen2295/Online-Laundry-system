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

import com.oop.model.Services;
import com.oop.util.CommonConstants;
import com.oop.util.CommonUtil;
import com.oop.util.DBConnectionUtil;
import com.oop.util.QueryUtil;

public class ServiceServiceImpl implements IServiceService {
	

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(ServiceServiceImpl.class.getName());

	private static Connection connection;

	private static Statement statement;

	static{
		//create table or drop if exist
		createServicesTable();
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
	public static void createServicesTable() {

		try {
			connection = DBConnectionUtil.getDBConnection();
			statement = connection.createStatement();
			// Drop table if already exists and as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_DROP_TABLE));
			// Create new services table as per SQL query available in
			// Query.xml
			statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_CREATE_TABLE_S));

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
	public void addServices(Services services) {

		String nic = CommonUtil.generateIDs(getServicesIDs());
		
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Query is available in Query.xml file and use
			 * insert_services key to extract value of it
			 */
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_INSERT_SERVICE));
			connection.setAutoCommit(false);
			
			//Generate employee IDs
			services.setNic(nic);
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, services.getNic());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, services.getName());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, services.getServiceName());
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, services.getAddedDate());


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
	public Services getServicesByID(String nic) {

		return actionOnServices(nic).get(0);
	}
	
	/**
	 * Get all list of services
	 * 
	 * @return ArrayList<Services> 
	 * 						- Array of employee list will be return
	 * 
	 * @see #actionOnServices()
	 */
	
	public ArrayList<Services> getServices() {
		
		return actionOnServices(null);
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
	public void removeServices(String nic) {

		// Before deleting check whether employee ID is available
		
			/*
			 * Remove employee query will be retrieved from EmployeeQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_REMOVE_SERVICE));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, nic);
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
	


	private ArrayList<Services> actionOnServices(String nic) {

		ArrayList<Services> servicesList = new ArrayList<Services>();
		try {
			connection = DBConnectionUtil.getDBConnection();
			/*
			 * Before fetching employee it checks whether employee ID is
			 * available
			 */
			if (nic != null && !nic.isEmpty()) {
				/*
				 * Get services by ID query will be retrieved from
				 * Query.xml
				 */
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_SERVICE));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, nic);
			}
			/*
			 * If employee ID is not provided for get employee option it display
			 * all employees
			 */
			else {
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_ALL_SERVICE));
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Services services = new Services();
				services.setNic(resultSet.getString(CommonConstants.COLUMN_INDEX_ONE));
				services.setName(resultSet.getString(CommonConstants.COLUMN_INDEX_TWO));
				services.setServiceName(resultSet.getString(CommonConstants.COLUMN_INDEX_THREE));
				services.setAddedDate(resultSet.getString(CommonConstants.COLUMN_INDEX_FOUR));
				
				servicesList.add(services);
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
		return servicesList;
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
	public Services updateServices(String nic, Services services) {

		/*
		 * Before fetching employee it checks whether employee ID is available
		 */
		if (nic != null && !nic.isEmpty()) {
			/*
			 * Update employee query will be retrieved from EmployeeQuery.xml
			 */
			try {
				connection = DBConnectionUtil.getDBConnection();
				preparedStatement = connection
						.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_UPDATE_SERVICE));
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, services.getNic());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, services.getName());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, services.getServiceName());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, services.getAddedDate());
				

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
		return getServicesByID(nic);
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
	private ArrayList<String> getServicesIDs(){
		
		ArrayList<String> arrayList = new ArrayList<String>();
		/*
		 * List of employee IDs will be retrieved from EmployeeQuery.xml
		 */
		try {
			connection = DBConnectionUtil.getDBConnection();
			preparedStatement = connection
					.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_SERVICE_IDS));
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
