/**
 * OOP 2018
 * 
 * @author Udara Samaratunge  Department of Software Engineering, SLIIT 
 * 
 * @version 1.0
 * Copyright: SLIIT, All rights reserved
 * 
 */
package com.oop.model;

/**
 * This is the Employee model class
 * 
 * @author Udara Samaratunge, SLIIT
 * @version 1.0
 */
public class Customer {

	private String Nic;
	
	private String Name;

	private String Email;

	private String Password;


	/**
	 * @return the nic
	 */
	public String getNic() {
		return Nic;
	}



	/**
	 * @param nic the nic to set
	 */
	public void setNic(String nic) {
		Nic = nic;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}
	
	@Override
	public String toString() {
		
		return "Customer ID = " + Nic + "\n" + "Customer Name = " + Name + "\n" + "Email = " + Email + "\n"
				+ "Password = " + Password ;
	}



}
