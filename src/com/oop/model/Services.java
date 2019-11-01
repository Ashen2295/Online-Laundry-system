package com.oop.model;

public class Services {
	


	private String nic;
	
	private String name;
	
	private String serviceName;
	
	private String addedDate;


	/**
	 * @return the nic
	 */
	public String getNic() {
		return nic;
	}




	/**
	 * @param nic the nic to set
	 */
	public void setNic(String nic) {
		this.nic = nic;
	}




	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}




	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}




	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}




	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}




	/**
	 * @return the addedDate
	 */
	public String getAddedDate() {
		return addedDate;
	}




	/**
	 * @param addedDate the addedDate to set
	 */
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}



	
	@Override
	public String toString() {
		
		return "ID = " + nic + "\n" + "Name = " + name + "\n" + "Service Name = " + serviceName + "\n"
				+ "Added Date = " + addedDate;
	}

}
