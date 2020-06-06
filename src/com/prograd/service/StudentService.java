package com.prograd.service;

import java.util.HashMap;
import java.util.List;

import com.prograd.dao.StudentDao;
import com.prograd.model.LoginUser;
import com.prograd.model.Student;

public class StudentService {

	private StudentDao studentDao = new StudentDao();

	public void add(Student student) {
		studentDao.insert(student);
	}
	
	public Student getStudentDetails(LoginUser student) {
		return studentDao.retrieveData(student.getMailId());
	}
	
	public HashMap<String, Integer> getMarks(String rollNo) {
		return studentDao.displaymarks(rollNo);
	}
	
	public void apply(String rollNo,String reason) {
		studentDao.applyForLeaveRequest(rollNo, reason);
	}
	
	public void requestStatus(String rollNo) {
		studentDao.checkRequestStatus(rollNo);
	}
	
	public List<String> getSubjects(Student student) {
		return studentDao.retrieveSubjects(student.getBranchName(), student.getSemester());
	}
	
	public List<String> getDepartments() {
		return studentDao.retrieveDepartments();
	}
	
	
	public List<String> getStudentsRollNo() {
		return studentDao.retrieveStudentsRollNo();
	}
	
	public String checkMyAttendance(String rollNo) {
		return studentDao.retrieveAttendance(rollNo);
	}
	
}
