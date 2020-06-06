package com.prograd.model;

public class LoginUser {
	private String role;
	private String mailId;
	private String password;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "LoginUser [role=" + role + ", mailId=" + mailId + ", password=" + password + "]";
	}

}
