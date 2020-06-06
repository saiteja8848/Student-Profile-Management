package com.prograd.model;

public class SignUpUser {

	private String fullName;
	private String mailId;
	private String phoneNumber;
	private String role;
	private String password;

	public String getFullName() {
		return fullName;
	}

	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return "User [fullName=" + fullName + ", mailId=" + mailId + ", phoneNumber=" + phoneNumber + ", role=" + role
				+ ", password=" + password + "]";
	}


}
