package com.oop.service;

import com.oop.model.Payment;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public interface IPaymentService {

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(IPaymentService.class.getName());


	/**
	 * Add employees for employee table
	 * @param employee
	 */
	public void addPayment(Payment payment);

	/**
	 * Get a particular Employee
	 * 
	 * @param empoyeeID
	 * @return Employee
	 */
	public Payment getPaymentByID(String pid);
	
	/**
	 * Get all list of employees
	 * 
	 * @return ArrayList<Employee>
	 */
	public ArrayList<Payment> getPayment();
	
	/**
	 * Update existing employee
	 * @param employeeID
	 * @param employee
	 * 
	 * @return
	 */
	public Payment updatePayment(String pid, Payment payment);

	/**
	 * Remove existing employee
	 * 
	 * @param employeeID
	 */
	public void removePayment(String pid);

}
