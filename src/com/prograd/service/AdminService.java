package com.prograd.service;

import java.util.List;

import com.prograd.dao.AdminDao;
import com.prograd.model.SignUpUser;
import com.prograd.model.Staff;
import com.prograd.model.Student;
import com.prograd.model.Subject;

public class AdminService {

	private AdminDao adminDao = new AdminDao();
	

	public void updateAttendance(String rollNo, String attendance) {
	adminDao.insertAttendance(rollNo,attendance);	
	}
	
	
	public SignUpUser getAdminDetails(String mail) {
		return adminDao.retrieveDetails(mail);
	}
	
	
	public List<Staff> getStaffDetails() {
		return adminDao.retrieveStaffDetails();
	}
	
	public List<Student> getStudentDetails() {
		return adminDao.retrieveStudentsData();
	}
	
	public void getRequests() {
		adminDao.retrieveRequests();
	}
	
	public void insertSubject(Subject subject) {
		adminDao.addSubject(subject);
	}
	
	public void removeStudent(String mailId,String rollNo) {
	    adminDao.deleteStudent(mailId,rollNo);
	}
	
	public void removeStaff(String mailId,String subject) {
		adminDao.deleteStaff(mailId, subject);
	}
	
	public void addDepartment(String deptName) {
		adminDao.insertDepartment(deptName);
	}
	
	public void getDepartments() {
		adminDao.retrieveDepartments();
	}
	
	public boolean  check(String rollNo) {
		return adminDao.retrieveAttendance(rollNo);
	}
	
	
}
