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


public class Admin {

	private String Name;
	
	private String Type;

	private String Description;

	private String Price;






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
	 * @return the type
	 */
	public String getType() {
		return Type;
	}




	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}




	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}




	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}




	/**
	 * @return the price
	 */
	public String getPrice() {
		return Price;
	}




	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		Price = price;
	}
	
	
	@Override
	public String toString() {
		
		return "Name = " + Name + "\n" + "Type = " + Type + "\n" + "Description = " + Description + "\n"
				+ "Price = " + Price ;
	}





}
