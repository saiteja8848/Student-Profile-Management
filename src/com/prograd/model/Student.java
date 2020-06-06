package com.prograd.model;

public class Student {

	private String mailId;
	private String rollNo;
	private String branchName;
	private int Year;
	private int semester;

	
	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String  getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	
	
	@Override
	public String toString() {
		return "Student [mailId=" + mailId + ", rollNo=" + rollNo + ", branchName=" + branchName + ", Year=" + Year
				+ ", semester=" + semester + "]";
	}
	
	

}
