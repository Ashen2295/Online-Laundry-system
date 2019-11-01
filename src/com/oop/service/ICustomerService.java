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

import com.oop.model.Customer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * Contract for the implementation of Customer Service .
 * 
 * @author Udara Samaratunge, SLIIT
 * @version 1.0
 */
public interface ICustomerService {

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(ICustomerService.class.getName());


	/**
	 * Add customers for customer table
	 * @param customer
	 */
	public void addCustomer(Customer customer);

	/**
	 * Get a particular Customer
	 * 
	 * @param empoyeeID
	 * @return Customer
	 */
	public Customer getCustomerByID(String empoyeeID);
	
	/**
	 * Get all list of customers
	 * 
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> getCustomer();
	
	/**
	 * Update existing customer
	 * @param customerID
	 * @param customer
	 * 
	 * @return
	 */
	public Customer updateCustomer(String Nic, Customer customer);

	/**
	 * Remove existing customer
	 * 
	 * @param customerID
	 */
	public void removeCustomer(String Nic);

}
