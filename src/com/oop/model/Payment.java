package com.oop.model;

public class Payment {
	
	private String pid;
	
	private String name;
	
	private String cardNumber;
	
	private String date;
	
	private String year;
	
	private String cvc;



	/**
	 * @return the nic
	 */
	public String getpid() {
		return pid;
	}

	/**
	 * @param nic the nic to set
	 */
	public void setpid(String pid) {
		this.pid = pid;
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
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the cvc
	 */
	public String getCvc() {
		return cvc;
	}

	/**
	 * @param cvc the cvc to set
	 */
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	
	@Override
	public String toString() {
		
		return "ID = " + pid + "\n" + "Name = " + name + "\n" + "Card Number = " + cardNumber + "\n"
				+ "Exp Date = " + date + "\n" + "Exp Year = " + year + "\n" + "CVC = "
				+ cvc;
	}

}
