package com.prograd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.prograd.model.LoginUser;
import com.prograd.model.SignUpUser;
import com.prograd.model.Student;
import com.prograd.service.StudentService;

public class StudentController {
    
	private StudentService studentService= new StudentService();
	private Scanner inputReader = new Scanner(System.in);
	private Student student =null;
	
	
	public int  getRandomNumber() {
		 Random rand = new Random();
		 return rand.nextInt(2000);
	}
	
	
	public void addStudentDetails(SignUpUser user) {
		student = new Student();
		System.out.print("\n-----------PLEASE ENTER THE FOLLOWING DETAILS----------");

		 int rand_int =getRandomNumber(); 
		 String rollNumber = Integer.toString(rand_int);
		 List<String> rollNos = studentService.getStudentsRollNo() ;
		 if(rollNos.contains(rollNumber)==false)
		 student.setRollNo(rollNumber);
		 else
			 student.setRollNo("----");
		
		List<String> deptNames = studentService.getDepartments();
		if(deptNames.size()>0) {
		showDeptNames(deptNames);
		System.out.print("Enter Branch Name from above table :");
		String branchName = inputReader.nextLine();
		if(deptNames.contains(branchName)==true)
		student.setBranchName(branchName);
		else {
			System.out.println("\n---------Choose only from avaliable Options!------");
			addStudentDetails(user);
		}
		System.out.print("Enter Year Of Study(1/2/3/4):");
		int year = inputReader.nextInt();
		if(year>=1&&year<=4)
	    student.setYear(year);
		else
			{
			 System.out.println("\n-------Please Enter only from 1 to 4 only!---------");
			 addStudentDetails(user);
			}
		
		System.out.print("Enter Which Semester(1 to 8) :");
		int semester = inputReader.nextInt();
		if(semester>=1&&semester<=4)
		 student.setSemester(semester);
		else
		 {
		 System.out.println("\n------Please Enter only from 1 to 8 only!----");
		 addStudentDetails(user);
		 }
		student.setMailId(user.getMailId());
		}
		else {	
			student.setBranchName("NA");
			student.setYear(0);
			student.setSemester(0);
			student.setMailId(user.getMailId());
		}
		
		studentService.add(student);
	}

	
	public void showDeptNames(List<String> deptNames) {
		System.out.println("\n\nBranches\n--------");
		for(String branchName:deptNames) {
			System.out.println(branchName);
		}
		System.out.println();
	}
	
	
	
	
	public void showOperations(LoginUser student) {
		Student studentDetails  = studentService.getStudentDetails(student);
		System.out.println("\nStudent Operations\n------------------");
		System.out.print("1.My Attendance\n2.My marks\n3.Leave Request\n4.My Details\n5.Request Status\n6.View My Subjects\n7.Exit\nPlease Enter your choice:");
		int choice = inputReader.nextInt();
		switch(choice) {
		case 1:
			System.out.println("View My Attendance");
			myAttendance(studentDetails);
			showOperations(student);
		break;
		case 2:
			displayMarks(studentDetails);
			showOperations(student);
		break;
		case 3:
			applyForLeave(studentDetails);
			showOperations(student);
		break;	
		case 4:
			displayStudentDetails(studentDetails);
			showOperations(student);
		break;
		case 5:
			requestStatus(studentDetails);
			showOperations(student);
			break;
		case 6:
			displaySubjects(studentDetails);
			showOperations(student);
		    break;
		case 7:
			System.out.println("\n!----Successfully Logged Out From Student----!");
			System.exit(0);
		default:
			System.out.println("Invalid Option");
			showOperations(student);
		break;	
		}
	
	}
	
	
	public void myAttendance(Student student) {
		String attendance = studentService.checkMyAttendance(student.getRollNo());
		if(attendance!=null) {
			System.out.println("\n-------Your Attendance For Current Semester is:"+attendance+"!------------");
		}else
			System.out.println("\n--------Still Not Updated------");
	}
	
	
	
	
	public void displayStudentDetails(Student student) {
		System.out.println("\n------------------------------------------------------------------------");
	    System.out.printf("%10s %15s %15s %10s %10s", "ROLL NO", "MAILID", "BRANCH", "YEAR","SEMESTER");
	    System.out.println("\n-------------------------------------------------------------------------");
		System.out.format("%10s %22s %10s %5d %5d",student.getRollNo(),student.getMailId().trim(),student.getBranchName().trim(),student.getYear(),student.getSemester());
		System.out.println();
		 System.out.println("-------------------------------------------------------------------------");
	}
	
	
	
	public void displayMarks(Student student) {  
		HashMap<String,Integer>marks = studentService.getMarks(student.getRollNo());
        if(marks.size()>0) {
        	 for (String i : marks.keySet()) {
        	      System.out.println("subjectName: " + i + " marks " + marks.get(i));
        	    }
        }else
        	System.out.println("Yet to update");
	}
	
	
	public void applyForLeave(Student student) {
		inputReader.nextLine();
		System.out.println("Please Enter your reason,no of days leave?:");
		String reason = inputReader.nextLine();
		studentService.apply(student.getRollNo(), reason);
	}
	
	public void requestStatus(Student student) {
		studentService.requestStatus(student.getRollNo());
	}
	
	public void displaySubjects(Student student) {
		List<String>subjects = studentService.getSubjects(student);
	   if(subjects.size()>0) {
		System.out.println("\nList Subjects  In your Current Semester\n-----------------------------------------");
		for(String subjectName:subjects) {
			System.out.println(subjectName);
		}}
	   else
		   System.out.println("\nSubjects are Yet to add");
	}
	
}
