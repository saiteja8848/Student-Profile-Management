package com.prograd.model;

public class Staff {

	private String mailId;
	private String staffId;
	private String qualification;
	private String departmentName;
	private String year;
	private String semester;
	private String teachingSubject;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTeachingSubject() {
		return teachingSubject;
	}

	public void setTeachingSubject(String teachingSubject) {
		this.teachingSubject = teachingSubject;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	@Override
	public String toString() {
		return "Staff [mailId=" + mailId  + ", qualification=" + qualification
				+ ", departmentName=" + departmentName + ", year=" + year + ", semester=" + semester
				+ ", teachingSubject=" + teachingSubject + "]";
	}

}
