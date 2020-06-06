package com.prograd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.prograd.model.Student;
import com.prograd.utility.ConnectionManager;

public class StudentDao {
	private Connection connection = ConnectionManager.getConnection();
	private Statement statement;
	private PreparedStatement preStatement;
	private ResultSet rs;
	private Student student = new Student();

	public void insert(Student student) {
		try {
			if (connection != null) {
				preStatement = connection.prepareStatement("INSERT INTO STUDENT VALUES(?,?,?,?,?)");
				preStatement.setString(1, student.getMailId());
				preStatement.setString(2, student.getRollNo());
				preStatement.setString(3, student.getBranchName());
				preStatement.setInt(4, student.getYear());
				preStatement.setInt(5, student.getSemester());
				preStatement.executeUpdate();
				preStatement.close();
				connection.close();
				System.out.println("student details successfully saved to db");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Student retrieveData(String mailId) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM STUDENT WHERE MAILID = " + "'" + mailId + "'");
				while (rs.next()) {
					student.setMailId(rs.getString(1));
					student.setRollNo(rs.getString(2));
					student.setBranchName(rs.getString(3));
					student.setYear(rs.getInt(4));
					student.setSemester(rs.getInt(5));
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;

	}

	public HashMap<String, Integer> displaymarks(String rollNo) {
		HashMap<String, Integer> marks = new HashMap<String, Integer>();
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM MARKS WHERE ROLLNO = " + "'" + rollNo + "'");
				while (rs.next()) {
					//System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt(3));
					marks.put(rs.getString(2), rs.getInt(3));
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marks;
	}

	public void applyForLeaveRequest(String rollNo, String reason) {
		try {
			if (connection != null) {
				preStatement = connection.prepareStatement("INSERT INTO REQUESTS VALUES(?,?,?)");
				preStatement.setString(1, rollNo);
				preStatement.setString(2, reason);
				preStatement.setInt(3, 0);
				preStatement.executeUpdate();
				System.out.println("Applied for leave request successfully");
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void checkRequestStatus(String rollNo) {
		boolean flag = false;
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM REQUESTS WHERE ROLLNO = " + "'" + rollNo + "'");
				while (rs.next()) {
					flag = true;
					if (rs.getInt(3) == 0)
						System.out.println("\nYour Request For Leave Pending");
					else if (rs.getInt(3) == 1) {
						setLeaveOff(rs.getString(1), rs.getString(2));
						System.out.println("\nYour Request For Leave Accepted");
					} else if (rs.getInt(3) == 2)
						System.out.println("\nYour Request For Leave Rejected");
				}
				if (flag == false) {
					System.out.println("\nYou have n't applied for any Leave Requests");
				}

			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setLeaveOff(String rollNo, String reason) {
		try {
			if (connection != null) {
				statement = connection.createStatement();
				statement.executeUpdate("UPDATE REQUESTS SET STATUS = 4 WHERE ROLLNO = " + "'" + rollNo + "'"
						+ "AND REASON =" + "'" + reason + "'");

			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> retrieveSubjects(String branchName,int semesterId){
		List<String> subjects = new ArrayList<>();
		System.out.println("branchNAME:"+branchName);
        System.out.println("id:"+semesterId);
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM SUBJECTS WHERE DEPARTMENT = "+"'"+branchName.trim()+"'"+" AND SEMESTER = "+semesterId);
				while(rs.next()) {
				   System.out.println(rs.getString(3));
					subjects.add(rs.getString(3));
			
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjects;
	}
	
	public List<String> retrieveDepartments(){
		List<String> departmentList = new ArrayList<>();
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs=statement.executeQuery("SELECT * FROM DEPARTMENTS");
				while(rs.next()) {
					departmentList.add(rs.getString(1));
				}
				
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departmentList;
	}
	
	public List<String> retrieveStudentsRollNo() {
		List<String> studentsList = new ArrayList<>();
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM STUDENT");
				while (rs.next()) {
					studentsList.add(rs.getString(2));
				}
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentsList;
	}

	
	public String retrieveAttendance(String rollno) {
		String attendance = null;
		try {
			if (connection != null) {
				statement = connection.createStatement();
				rs = statement.executeQuery("SELECT * FROM ATTENDANCE WHERE ROLLNO = "+"'"+rollno.trim()+"'");
			    while(rs.next()) {
			    	attendance = rs.getString(2);
			    }
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendance;
	}
	
	
}
