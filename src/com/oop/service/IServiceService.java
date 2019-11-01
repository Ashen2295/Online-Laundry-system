package com.oop.service;

import com.oop.model.Services;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public interface IServiceService {

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(IServiceService.class.getName());


	/**
	 * Add employees for employee table
	 * @param employee
	 */
	public void addServices(Services services);

	/**
	 * Get a particular Employee
	 * 
	 * @param empoyeeID
	 * @return Employee
	 */
	public Services getServicesByID(String pid);
	
	/**
	 * Get all list of employees
	 * 
	 * @return ArrayList<Employee>
	 */
	public ArrayList<Services> getServices();
	
	/**
	 * Update existing employee
	 * @param employeeID
	 * @param employee
	 * 
	 * @return
	 */
	public Services updateServices(String pid, Services services);

	/**
	 * Remove existing employee
	 * 
	 * @param employeeID
	 */
	public void removeServices(String pid);

}
