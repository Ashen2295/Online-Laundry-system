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

import com.oop.model.Admin;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * Contract for the implementation of Admin Service .
 * 
 * @author Udara Samaratunge, SLIIT
 * @version 1.0
 */
public interface IAdminService {

	/** Initialize logger */
	public static final Logger log = Logger.getLogger(IAdminService.class.getName());


	/**
	 * Add admins for admin table
	 * @param admin
	 */
	public void addAdmin(Admin admin);

	/**
	 * Get a particular Admin
	 * 
	 * @param empoyeeID
	 * @return Admin
	 */
	public Admin getAdminByID(String name);
	
	/**
	 * Get all list of admins
	 * 
	 * @return ArrayList<Admin>
	 */
	public ArrayList<Admin> getAdmin();
	
	/**
	 * Update existing admin
	 * @param adminID
	 * @param admin
	 * 
	 * @return
	 */
	public Admin updateAdmin(String name, Admin admin);

	/**
	 * Remove existing admin
	 * 
	 * @param adminID
	 */
	public void removeAdmin(String name);

}
