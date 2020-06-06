package com.prograd.controller;

import java.util.List;
import java.util.Scanner;

import com.prograd.model.LoginUser;
import com.prograd.model.SignUpUser;
import com.prograd.model.Staff;
import com.prograd.model.Student;
import com.prograd.model.Subject;
import com.prograd.service.AdminService;

public class AdminController {

	private Scanner inputReader = new Scanner(System.in);
	private AdminService adminService = new AdminService();

	public void showOperations(LoginUser admin) {

		System.out.println("\n----------------\nAdmin Operations\n----------------");
		System.out.print(
				"1.View Student Details\n2.View Staff Details\n3.View My Details\n4.View Requests\n5.Add New Subject\n6.Remove\n7.Add New Branch\n8.View Branchs\n9.update Attendance"
				+ "\n10.Exit\nPlease Enter Your Choice:");
		int choice = inputReader.nextInt();
		switch (choice) {
		case 1:
			viewStudentsDetails();
			this.showOperations(admin);
			break;
		case 2:
			viewStaffDetails();
			this.showOperations(admin);
			break;
		case 3:
			viewMyDetails(admin);
			this.showOperations(admin);
			break;
		case 4:
			adminService.getRequests();
			this.showOperations(admin);
			break;
		case 5:
			addSubject();
			this.showOperations(admin);
			break;
		case 6:
			remove(admin);
			this.showOperations(admin);
			break;
		case 7:
			addDepartment();
			this.showOperations(admin);
			break;
		case 8:
			adminService.getDepartments();
			this.showOperations(admin);
		 break;
		case 9:
			updateAttendance();
			this.showOperations(admin);
		case 10:
			System.out.println("\n!----Successfully Logged Out From Admin----!");
			System.exit(0);
		default:
			System.out.println("Invalid Option");
			this.showOperations(admin);
			break;
		}
	}

	public void updateAttendance() {
		boolean flag =false;
		List<Student> studentsList = adminService.getStudentDetails();
		if(studentsList.size()>0) {
			inputReader.nextLine();
		for(Student student:studentsList) {
			if(adminService.check(student.getRollNo())==false) {
			System.out.print("\nEnter attendance % for roll no "+student.getRollNo()+" :");
			String attendance =inputReader.nextLine();
			adminService.updateAttendance(student.getRollNo(),attendance);
			flag=true;
			}
		}
		if(flag==false)
			System.out.println("\n------You have already Updated Attendance for registered students/ no new registrations--------");
		}
		else
			System.out.println("\n------No Registrations Yet or You have Updated Attendance for registered--------");
		
	}
	
	
	public void addDepartment() {
		inputReader.nextLine();
		System.out.print("\nEnter Department Name:");
		String deptName = inputReader.nextLine();
		adminService.addDepartment(deptName.toLowerCase());
	}
	
	
	public void viewMyDetails(LoginUser admin) {
		SignUpUser adminDetails = adminService.getAdminDetails(admin.getMailId());
		System.out.print("\nYour Details:");
		System.out.println("\n------------------------------------------------------------");
		System.out.printf("%10s %10s %15s %15s", "FULLNAME", "MAILID", "PHONE", "PASSWORD");
		System.out.println("\n------------------------------------------------------------");
		System.out.format("%s %20s %10s %s", adminDetails.getFullName().trim(), adminDetails.getMailId().trim(),
				adminDetails.getPhoneNumber(), adminDetails.getPassword());
		System.out.println("\n-------------------------------------------------------------");
	}

	public void viewStudentsDetails() {
		List<Student> studentsList = adminService.getStudentDetails();
		if (studentsList.size() > 0) {
			System.out.print("\nRegistered Student details");
			System.out.println("\n-----------------------------------------------------------------------------");
			System.out.printf("%10s %20s %10s %15s %10s", "ROLL NO", "MAILID", "BRANCH", "YEAR", "SEMESTER");
			System.out.println("\n-----------------------------------------------------------------------------");
			for (Student student : studentsList) {
				System.out.format("%10s %22s %24s %d %d", student.getRollNo(), student.getMailId(),
						student.getBranchName(), student.getYear(), student.getSemester());
				System.out.println();
			}
			System.out.println("-----------------------------------------------------------------------------");

		} else
			System.out.println("\n--------NO REGISTERATIONS YET-------");
	}

	public void viewStaffDetails() {
		List<Staff> staffList = adminService.getStaffDetails();
		if (staffList.size() > 0) {
			System.out.print("\nRegistered Staff details");
			System.out.println("\n--------------------------------------------------------------------------------");
			System.out.printf("%s %15s %22s %10s %20s", "STAFF ID", "MAILID", "QUALIFICATION", "BRANCH",
					"TEACHING_SUBJECT");
			System.out.println("\n--------------------------------------------------------------------------------");
			for (Staff staff : staffList) {
				System.out.format("%s %25s %10s %30s %s", staff.getStaffId(), staff.getMailId(),
						staff.getQualification(), staff.getDepartmentName(), staff.getTeachingSubject());
				System.out.println();
			}
			System.out.println("--------------------------------------------------------------------------------");
		} else
			System.out.println("\n---------NO REGISTERATIONS YET---------");
	}

	public void addSubject() {
		try {
		Subject subject = new Subject();
		System.out.print("\nEnter which year subject belongs(1 to 4):");
		int year = inputReader.nextInt();
		if(year>=1&&year<=4)
		subject.setYear(year);
		else {
			System.out.println("\n---------Please enter in range of 1 to 4 only!------");
			addSubject();
		}
	
		System.out.print("Enter which semester does(1 to 8):");
		int semester = inputReader.nextInt();		
		if(semester>=1&&semester<=4)
			subject.setSemester(semester);
			else {
				System.out.println("\n---------Please enter in range of 1 to 8 only!------");
				addSubject();
			}
		inputReader.nextLine();
		System.out.print("Enter subjectName:");
		String subjectName = inputReader.nextLine();
		subject.setSubjectName(subjectName);
		
		System.out.print("Enter branch/Department Name:");
		String department = inputReader.nextLine();
		subject.setDepartment(department);

		adminService.insertSubject(subject);
	  }catch(Exception e) {
		  System.out.println("\n--------Invalid Input------");
		  addSubject();
	  }
		
	}

	public void remove(LoginUser admin) {

	
		try {
		String subject = null, rollNo = null;
		System.out.print(
				"\nDelete\n---------------\n1.Delete Staff\n2.Delete Student\nPlease Enter Your choice(-1 to return):");
		int choice = inputReader.nextInt();
		if (choice == -1) {
			this.showOperations(admin);
		}
		inputReader.nextLine();

		switch (choice) {
		case 1:
			List<Staff> staffList = adminService.getStaffDetails();
			if (staffList.size() > 0) {
				System.out.print("\nPlease Enter Staff  MailId:");
				String mailId = inputReader.nextLine();
				for (Staff staff : staffList) {
					if (staff.getMailId().equalsIgnoreCase(mailId)) {
						subject = staff.getTeachingSubject();
						break;
					}
				}
				if (subject != null)
					adminService.removeStaff(mailId, subject);
				else
					System.out.println("\nEmailId n't Exists");
			} else
				System.out.println("\nNo Data to Remove");
			break;
		case 2:
			List<Student> studentsList = adminService.getStudentDetails();
			if (studentsList.size() > 0) {
				System.out.print("\nPlease Enter Student MailId:");
				String mailId = inputReader.nextLine();
				for (Student student : studentsList) {
					if (student.getMailId().equalsIgnoreCase(mailId)) {
						rollNo = student.getRollNo();
						break;
					}
				}

				if (rollNo != null)
					adminService.removeStudent(mailId, rollNo);
				else
					System.out.println("\nEmailId doesn't exists");
			} else
				System.out.println("\nNo Data to Remove");

			break;
		}
		}catch(Exception e) {
			System.out.println("\n--------Invalid Input------");
			remove(admin);
			
		}
	}
	
	
	
}
