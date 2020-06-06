package com.prograd.service;

import java.util.List;

import com.prograd.dao.StaffDao;
import com.prograd.model.LoginUser;
import com.prograd.model.Marks;
import com.prograd.model.Staff;
import com.prograd.model.Student;
import com.prograd.model.Subject;

public class StaffService {

	private StaffDao staffDao = new StaffDao();
	
	public void add(Staff staff) {
         staffDao.insert(staff);
	}
	
	public List<Subject> showSubjects(String departmentName) {
		return staffDao.displaySubjectsRepository(departmentName);
	}
	
	public List<Student> viewRegisteredStudents(LoginUser staff) {
		return staffDao.displayRegisteredStudents(staff);
	}
	
	
	public Staff getStaffDetails(String mailId) {
		return staffDao.retrieveDetails(mailId);
	}
	
	public void saveMarkstoDB(List<Marks> marksList) {
		staffDao.saveMarksList(marksList);
	}
	
	public List<Student> getStudentListandUpdateMarks(LoginUser loggedstaff) {
		return staffDao.getStudentsListWhoseMarksToUpdate(loggedstaff);
	}
	
	public List<String> getDepartments() {
		return staffDao.retrieveDepartments();
	}
	
	public void updateStaffDetails(String mailId, Subject subject) {
		staffDao.updateStaff(mailId,subject);
	}
	
	public List<String> check(List<String> deptNames) {
		return staffDao.check(deptNames);
	}
	
	
}
