package com.prograd.model;

public class Marks {

	private String rollNo;
	private String subjectName;
	private int marks;

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "Marks [rollNo=" + rollNo + ", subjectName=" + subjectName + ", marks=" + marks + "]";
	}

}
