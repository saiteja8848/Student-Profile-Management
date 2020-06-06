package com.prograd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prograd.model.LoginUser;
import com.prograd.model.Marks;
import com.prograd.model.Staff;
import com.prograd.model.Student;
import com.prograd.model.Subject;
import com.prograd.utility.ConnectionManager;

public class StaffDao {

	private Connection connection = ConnectionManager.getConnection();
	private Statement statement;
	private PreparedStatement preStatement;
	private ResultSet resultSet;

	public void insert(Staff staff) {
		try {
			if (connection != null) {
				String sql = "INSERT INTO STAFF(STAFFID,MAILID,QUALIFICATION,DEPARTMENTNAME,YEAR,SEMESTER,TEACHINGSUBJECT) values (staff_ids.nextval,?,?,?,?,?,?)";
				System.out.println("STAFF DETAILS:"+staff);
				preStatement = connection.prepareStatement(sql);
				preStatement.setString(1, staff.getMailId());
				//preStatement.setString(2,staff_ids.nextval );
				preStatement.setString(2, staff.getQualification());
				preStatement.setString(3, staff.getDepartmentName());
				preStatement.setString(4, staff.getYear());
				preStatement.setString(5, staff.getSemester());
				preStatement.setString(6, staff.getTeachingSubject());
				preStatement.executeUpdate();
				preStatement.close();

				setSubject(staff.getTeachingSubject());
				System.out.println("staff details successfully saved to db");
				connection.close();
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Staff retrieveDetails(String mail) {
		Staff staff = null;
		try {
			if (connection != null) {
				staff = new Staff();
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM STAFF WHERE MAILID = " + "'" + mail + "'");
				while (resultSet.next()) {
					staff.setMailId(resultSet.getString(1));
					staff.setStaffId(Integer.toString(resultSet.getInt(2)));
					staff.setQualification(resultSet.getString(3));
					staff.setDepartmentName(resultSet.getString(4));
					staff.setYear(resultSet.getString(5));
					staff.setSemester(resultSet.getString(6));
					staff.setTeachingSubject(resultSet.getString(7));
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return staff;
	}

	public List<Subject> displaySubjectsRepository(String department) {
		List<Subject> subjectNames = new ArrayList<>();
		
		try {
			if (connection != null) {
				statement = connection.createStatement();
				//System.out.println("-----"+department+"---------");
				resultSet = statement.executeQuery("SELECT * FROM SUBJECTS WHERE DEPARTMENT = " + "'" + department + "'");
					System.out.println("\nSubjects Under Selected Department");
					System.out.println("-----------------------------------");
					System.out.printf("%5s %5s %15s", "Year", "Semester", "SubjectName");
					System.out.println("\n-----------------------------------");
					while (resultSet.next()) {
						if (resultSet.getInt(4) == 0) {
							System.out.printf("%5d %5d %15s", resultSet.getInt(1), resultSet.getInt(2),resultSet.getString(3));
							System.out.println();
						  Subject subject = new Subject();
						  subject.setYear(resultSet.getInt(1));
						 subject.setSemester(resultSet.getInt(2));
						 subject.setSubjectName(resultSet.getString(3));
						 subject.setStatus(resultSet.getInt(4));
						 subject.setDepartment(resultSet.getString(5));
						 subjectNames.add(subject);
						}
					}
					System.out.println("-----------------------------------");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        return subjectNames;
	}

	public void setSubject(String subjectName) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				statement.executeQuery("UPDATE SUBJECTS SET STATUS = 1 WHERE SUBJECTNAME = " + "'" +subjectName.trim()+ "'");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Student> displayRegisteredStudents(LoginUser staff) {
		List<Student> studentsList = new ArrayList<>();
		Staff staffData = retrieveDetails(staff.getMailId());
		String semId = staffData.getSemester();
		int id = Integer.parseInt(semId.trim());
		try {
			if (connection != null) {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM STUDENT WHERE SEMESTER = " + id + " AND "
						+ "BRANCHNAME =" + "'" + staffData.getDepartmentName() + "'");
				while (resultSet.next()) {
					Student student = new Student();
					student.setMailId(resultSet.getString(1).trim());
					student.setRollNo(resultSet.getString(2).trim());
					student.setBranchName(resultSet.getString(3).trim());
					student.setYear(resultSet.getInt(4));
					student.setSemester(resultSet.getInt(5));
					studentsList.add(student);
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentsList;
	}

	public boolean checkForExistence(String rollNo, String teachingSubject) {
		boolean status = false;
		try {
			if (connection != null) {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM MARKS WHERE ROLLNO = " + "'" + rollNo + "'"
						+ " AND SUBJECTNAME = " + "'" + teachingSubject + "'");
				status = resultSet.next();
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public List<Student> getStudentsListWhoseMarksToUpdate(LoginUser loggedstaff) {
		List<Student> studentsList = displayRegisteredStudents(loggedstaff);
		List<Student> studentsList2 = new ArrayList<>();
		Staff staff = retrieveDetails(loggedstaff.getMailId());
		if (studentsList != null && studentsList.size() > 0) {
			for (int i = 0; i < studentsList.size(); i++) {
				if (checkForExistence(studentsList.get(i).getRollNo(), staff.getTeachingSubject()) == false) {
					studentsList2.add(studentsList.get(i));
				}
			}
		}
		return studentsList2;
	}

	public void saveMarksList(List<Marks> marksList) {
		try {
			if (connection != null) {
				for (int i = 0; i < marksList.size(); i++) {
					preStatement = connection.prepareStatement("INSERT INTO MARKS VALUES(?,?,?)");
					preStatement.setString(1, marksList.get(i).getRollNo());
					preStatement.setString(2, marksList.get(i).getSubjectName());
					preStatement.setInt(3, marksList.get(i).getMarks());
					preStatement.executeUpdate();
				}
				System.out.println("marks details successfully saved to db");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public List<String> retrieveDepartments() {
		List<String> departmentNames = new ArrayList<>();
		try {
			if (connection != null) {
		 		statement = connection.createStatement();
		       resultSet = statement.executeQuery("SELECT * FROM DEPARTMENTS");
		       while(resultSet.next()) {
		    	    departmentNames.add(resultSet.getString(1));
		       }
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departmentNames;
	}
	
	public List<Subject> SubjectsList() {	
		List<Subject> subjectNames = new ArrayList<>();
		try {
			
			if (connection != null) {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM SUBJECTS");		
				while (resultSet.next()) {
			                 if (resultSet.getInt(4) == 0) {
			                  Subject subject = new Subject();
			               subject.setYear(resultSet.getInt(1));
			                 subject.setSemester(resultSet.getInt(2));
			              subject.setSubjectName(resultSet.getString(3));
			              subject.setStatus(resultSet.getInt(4));
			             subject.setDepartment(resultSet.getString(5));
			              subjectNames.add(subject);
			}
			                    
		}
		}else
			System.out.println("Check Your Connection");
	} catch (SQLException e) {
		e.printStackTrace();
	}	
	return subjectNames;		
}
	
	
	
	
	
	public List<String> check(List<String> deptNames) {
		List<String> deptNameList = new ArrayList<>();
		List<Subject> subjectNames = SubjectsList();
	     for(int i =0 ;i<deptNames.size();i++) {
		  for(Subject subject:subjectNames) {
			if(subject.getDepartment().equalsIgnoreCase(deptNames.get(i))==true&&subject.getStatus()==0) {
				deptNameList.add(subject.getDepartment());
				break;
			}
		 }
	    }
		return deptNameList;
	}
	
	
	public void updateStaff(String mailId,Subject subject) {
		try {
			if (connection != null) {
		 		statement = connection.createStatement();
		 		String y = Integer.toString(subject.getYear());
		 		String s = Integer.toString(subject.getSemester());
		 		String sub = subject.getSubjectName();
		 		String d = subject.getDepartment();
String sql = "UPDATE  STAFF SET YEAR = "+"'"+y+"'"+","+" SEMESTER = "+"'"+s+"'"+","+" TEACHINGSUBJECT = "+"'"+sub+"'"+","+"DEPARTMENTNAME = "+"'"+d+"'"+" WHERE MAILID = "+"'"+mailId+"'";
		 		//System.out.println(sql);
		 		statement.executeUpdate(sql);
		 		setSubject(subject.getSubjectName());
				System.out.println("Updated Successfully");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
