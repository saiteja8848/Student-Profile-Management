package com.prograd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.prograd.model.SignUpUser;
import com.prograd.model.Staff;
import com.prograd.model.Student;
import com.prograd.model.Subject;
import com.prograd.utility.ConnectionManager;

public class AdminDao {
	private Connection connection = ConnectionManager.getConnection();
	private Statement statement;
	private PreparedStatement preStatement;
	private ResultSet rs;
	private SignUpUser admin = new SignUpUser();

	
	public boolean checkForExistenceOfSubject(Subject subject) {
		boolean status = false;
		try {
			if (connection != null) {
				statement = connection.createStatement();
		rs = statement.executeQuery("SELECT * FROM SUBJECTS WHERE SUBJECTNAME = "+"'"+subject.getSubjectName()+"'"+" AND DEPARTMENT = "+"'"+subject.getDepartment()+"'");
			status = rs.next();
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	
	
	public void addSubject(Subject subject) {
		boolean status = checkForExistenceOfSubject(subject);
		try {
			if (connection != null&&status==false) {
				preStatement = connection.prepareStatement("INSERT INTO SUBJECTS VALUES(?,?,?,?,?)");
				preStatement.setInt(1, subject.getYear());
				preStatement.setInt(2, subject.getSemester());
				preStatement.setString(3, subject.getSubjectName());
				preStatement.setInt(4, 0);
				preStatement.setString(5, subject.getDepartment());
				preStatement.executeUpdate();
				System.out.println("Subject add to db successfully");
			}else if(status==true){
				System.out.println("\n--------subject already existing-------");
			}
				
			
			else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public SignUpUser retrieveDetails(String mail) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM USERSDATA WHERE MAILID = " + "'" + mail + "'");
				while (rs.next()) {
					admin.setFullName(rs.getString(1));
					admin.setMailId(rs.getString(2));
					admin.setPhoneNumber(rs.getString(3));
					admin.setRole("admin");
					admin.setPassword(rs.getString(5));
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}

	public List<Student> retrieveStudentsData() {
		List<Student> studentsList = new ArrayList<>();
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM STUDENT");
				while (rs.next()) {
					Student student = new Student();
					student.setMailId(rs.getString(1));
					student.setRollNo(rs.getString(2));
					student.setBranchName(rs.getString(3));
					student.setYear(rs.getInt(4));
					student.setSemester(rs.getInt(5));
					studentsList.add(student);
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentsList;
	}

	public List<Staff> retrieveStaffDetails() {
		List<Staff> staffList = new ArrayList<>();
		try {
			if (connection != null) {

				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM STAFF");
				while (rs.next()) {
					Staff staff = new Staff();
					staff.setMailId(rs.getString(1));
					staff.setStaffId(Integer.toString(rs.getInt(2)));
					staff.setQualification(rs.getString(3));
					staff.setDepartmentName(rs.getString(4));
					staff.setYear(rs.getString(5));
					staff.setSemester(rs.getString(6));
					staff.setTeachingSubject(rs.getString(7));
					staffList.add(staff);
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return staffList;
	}

	public void retrieveRequests() {
		boolean flag = false;
		Scanner inputReader = new Scanner(System.in);
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM REQUESTS");
				while (rs.next()) {
					if (rs.getInt(3) == 0) {
						flag = true;
						System.out.println("\n" + rs.getString(1) + "         " + rs.getString(2));
						System.out.print("Do You Want Approve the Leave request(y/n)?:");
						char ch = inputReader.next().charAt(0);
						if (ch == 'y' || ch == 'Y')
							setStatus(rs.getString(1), 1);
						else
							setStatus(rs.getString(1), 2);
					}

				}
				if (flag == false) {
					System.out.println("\n--------No  Leave  Requests!---------");
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setStatus(String rollNo, int status) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				if (status == 1)
					statement.executeUpdate("UPDATE REQUESTS SET STATUS = 1 WHERE ROLLNO = " + "'" + rollNo + "'");
				else if (status == 2)
					statement.executeUpdate("UPDATE REQUESTS SET STATUS = 2 WHERE ROLLNO = " + "'" + rollNo + "'");

			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* 1-->delete staff, 2--->delete student */

	public void deleteStudent(String mailId, String rollNo) {
		String sql1 = "DELETE FROM USERSDATA WHERE MAILID = " + "'" + mailId + "'" + " AND ROLE = 'student'";
		String sql2 = "DELETE FROM STUDENT WHERE MAILID = " + "'" + mailId + "'";
		try {
			if (connection != null) {
				// deleteMarksForStudent(rollNo);
				System.out.println();
				Statement statement2 = connection.createStatement();
				statement2.executeUpdate(sql2);
				statement = connection.createStatement();
				statement.executeUpdate(sql1);
				System.out.println("Student Deleted Successfully");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMarksForStudent(String rollNo) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				System.out.println("-----" + rollNo + "-----");
				rs = statement.executeQuery("SELECT * FROM MARKS WHERE ROLLNO = " + "'" + rollNo + "'");
				if (rs.next() == true)
					statement.executeQuery("DELETE FROM MARKS WHERE ROLLNO = " + "'" + rollNo + "'");
				else
					return;
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteStaff(String mailId, String subject) {
		Statement statement2;
		String sql1 = "DELETE FROM USERSDATA WHERE MAILID = " + "'" + mailId + "'" + " AND ROLE = 'staff'";
		String sql2 = "DELETE FROM STAFF WHERE MAILID = " + "'" + mailId + "'";
		try {
			if (connection != null) {
				statement = connection.createStatement();
				statement2 = connection.createStatement();
				statement2.executeUpdate(sql2);
				statement.executeUpdate(sql1);
				setSubject(subject);
				deleteMarksForSubject(subject);
				System.out.println("\nStaff Deleted Successfully");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMarksForSubject(String subject) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				statement.executeQuery("DELETE FROM MARKS WHERE SUBJECTNAME = " + "'" + subject + "'");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// delete teaching subject also, when teacher is removed
	public void setSubject(String subjectName) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				statement.executeQuery("UPDATE SUBJECTS SET STATUS = 0 WHERE SUBJECTNAME = " + "'" + subjectName + "'");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<String> retrieveDepartments(){
		List<String> departmentList = new ArrayList<>();
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs=statement.executeQuery("SELECT * FROM DEPARTMENTS");
				System.out.println("\nDepartments\n-----------");
				while(rs.next()) {
					System.out.println(rs.getString(1));
					departmentList.add(rs.getString(1));
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departmentList;
	}
	
	
	
	
	public void insertDepartment(String deptName) {
		if(retrieveDepartments().contains(deptName))
			System.out.println("\nDepartment already exists");
		else {
		try {
			if (connection != null) {
				preStatement = connection.prepareStatement("INSERT INTO DEPARTMENTS VALUES(?)");
				preStatement.setString(1, deptName);
				preStatement.execute();
				System.out.println("Department added successfully");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
	
	public void insertAttendance(String rollNo,String attendance) {
		try {
			if (connection != null) {
				preStatement = connection.prepareStatement("INSERT INTO ATTENDANCE VALUES(?,?)");
				preStatement.setString(1, rollNo);
				preStatement.setString(2, attendance);
				preStatement.execute();
				System.out.println("\nAttendance Updated Successfully");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean retrieveAttendance(String rollno) {
		boolean status = false;
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM ATTENDANCE WHERE ROLLNO = "+"'"+rollno.trim()+"'");
			    while(rs.next()) {
			    	status = true;
			    }
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   return status;
	}
	

}
