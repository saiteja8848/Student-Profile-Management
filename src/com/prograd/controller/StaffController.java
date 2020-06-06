package com.prograd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.prograd.model.LoginUser;
import com.prograd.model.Marks;
import com.prograd.model.SignUpUser;
import com.prograd.model.Staff;
import com.prograd.model.Student;
import com.prograd.model.Subject;
import com.prograd.service.StaffService;

public class StaffController {

	private Staff staffDetails = new Staff();
	private StaffService staffService = new StaffService();
	private Scanner inputReader = new Scanner(System.in);

	public void addStaffDetails(SignUpUser user) {
		
		try {
		staffDetails = new Staff();
		
		List<Subject> subjects;

		System.out.println("\n-------Please Enter the following details--------");
		System.out.print("Enter qualification:");
		String qualification = inputReader.nextLine();
		staffDetails.setQualification(qualification);

		List<String> deptNames = staffService.getDepartments();
		deptNames = staffService.check(deptNames);
		if (deptNames.size() > 0) {
			showDepartments(deptNames);
			System.out.print("Enter department name from above table :");
			String departmentName = inputReader.nextLine();
			if (validateDepartment(deptNames, departmentName) == true) {
				staffDetails.setDepartmentName(departmentName.toLowerCase());
				subjects = staffService.showSubjects(departmentName);
				//showSubjects(subjects);
				System.out.print("\nEnter subject name you teach from above table :");
				String subjectName = inputReader.nextLine();
				if (validateSubject(subjects, subjectName) == true) {
					staffDetails.setTeachingSubject(subjectName);
					staffDetails.setMailId(user.getMailId());
					for (Subject subject : subjects) {
						if (subject.getSubjectName().contentEquals(subjectName) == true) {
							staffDetails.setYear(Integer.toString(subject.getYear()));
							staffDetails.setSemester(Integer.toString(subject.getSemester()));
						}
					}

					staffService.add(staffDetails);
				} else {
					System.out.println("\n---------Enter only those subjects under department Avaliable!---------");
					addStaffDetails(user);
				}
			} else {
				System.out.println("\n--------Enter only those Departments Avaliable!----------");
				addStaffDetails(user);
			}
		} else {
			staffDetails.setDepartmentName("not allocated");
			staffDetails.setMailId(user.getMailId());
			staffDetails.setYear("0");
			staffDetails.setSemester("0");
			staffDetails.setTeachingSubject("NA");
			staffService.add(staffDetails);
		}
		
		}catch(Exception e) {
			System.out.println("\n--------Invalid Input-------");
			addStaffDetails(user);
		}
	}

	public boolean validateSubject(List<Subject> subjects, String entered) {
		boolean status = false;
		for (Subject subject : subjects) {
			if (subject.getSubjectName().equalsIgnoreCase(entered)) {
				status = true;
				break;
			}
		}
		return status;
	}

	public boolean validateDepartment(List<String> departmentNames, String entered) {
		return departmentNames.contains(entered);
	}

	public void showDepartments(List<String> departments) {
		System.out.println("\nDepartment Names\n---------------");
		for (String department : departments)
			{System.out.println(department);}
	}

	public void showSubjects(List<Subject> subjects) {
		System.out.println("\nSubjects\n-----------");
		for (Subject subject : subjects)
			System.out.println(subject.getSubjectName());
	}

	public void showOperations(LoginUser loggedStaff) {
		System.out.println("\nStaff Operations\n-----------------");
		System.out.print(
				"1.View Registered Students\n2.Update Marks\n3.View My Details\n4.Choose Subject\n5.Exit\nPlease Enter Your Choice:");
		int choice = inputReader.nextInt();
		switch (choice) {
		case 1:
			viewRegisteredStudents(loggedStaff);
			break;
		case 2:
			updateMarks(loggedStaff);
			break;
		case 3:
			viewMyDetails(loggedStaff);
			break;
		case 4:
			selectSubject(loggedStaff);
			this.showOperations(loggedStaff);
			break;
		case 5:
			System.out.println("\n!----Successfully Logged Out From Staff----!");
			System.exit(0);
			break;
		default:
			System.out.println("\n-------Invalid Option------");
			this.showOperations(loggedStaff);
			break;
		}

	}

	public void selectSubject(LoginUser loggedStaff) {
		Subject subDetails = null;
		Staff staff = staffService.getStaffDetails(loggedStaff.getMailId());
		if (staff.getTeachingSubject().contentEquals("na") || staff.getTeachingSubject().contentEquals("NA")) {
			List<String> deptNames = staffService.getDepartments();
			deptNames = staffService.check(deptNames);
			if (deptNames.size() > 0) {
				System.out.print("\n-----you can select now-----");
				showDepartments(deptNames);
				System.out.print("Enter Dept name:");
				inputReader.nextLine();
				String deptName = inputReader.nextLine();

				if (deptNames.contains(deptName)) {
					// SUBJECT
					System.out.println("Subject starting");
					List<Subject> subjects = staffService.showSubjects(deptName);
					System.out.print("Enter subject name:");
					String subName = inputReader.nextLine();
					for (Subject subject : subjects) {
						if (subject.getSubjectName().equalsIgnoreCase(subName) == true) {
							subDetails = subject;
							break;
						}
					}
					if(subDetails!=null)
					staffService.updateStaffDetails(loggedStaff.getMailId(), subDetails);
					else {
						System.out.println("\n--------Select only From Avaliable Branches-----------");
						selectSubject(loggedStaff);
					}					
					// END OF SUBJECT
				} else {
					System.out.println("\n--------Select only From Avaliable Branches-----------");
					selectSubject(loggedStaff);
				}

			}

		} else
			System.out.println("\n------This option is only avaliable  if not allocated any subject to teach------");
	}

	public void viewRegisteredStudents(LoginUser loggedStaff) {
		List<Student> studentsList = staffService.viewRegisteredStudents(loggedStaff);
		if (studentsList.size() > 0) {
			System.out.print("\nRegistered Student details");
			System.out.println("\n-----------------------------------------------------------------------------");
			System.out.printf("%10s %15s %15s %10s %10s", "ROLL NO", "MAILID", "BRANCH", "YEAR", "SEMESTER");
			System.out.println("\n-----------------------------------------------------------------------------");
			for (Student student : studentsList) {
				System.out.format("%10s %22s %10s %5d %5d", student.getRollNo(), student.getMailId(),
						student.getBranchName(), student.getYear(), student.getSemester());
				System.out.println();
			}
			System.out.println("-----------------------------------------------------------------------------");
		} else
			System.out.println("\nno one registered yet");
		this.showOperations(loggedStaff);
	}

	public void updateMarks(LoginUser loggedStaff) {
		List<Student> studentsList = staffService.getStudentListandUpdateMarks(loggedStaff);
		Staff staff = staffService.getStaffDetails(loggedStaff.getMailId());
		if (studentsList.size() > 0) {
			List<Marks> marksList = new ArrayList<>();
			for (Student student : studentsList) {
				Marks mark = new Marks();
				mark.setRollNo(student.getRollNo());
				mark.setSubjectName(staff.getTeachingSubject());
				System.out.print("Enter marks for this " + student.getRollNo() + ":");
				int m = inputReader.nextInt();
				mark.setMarks(m);
				marksList.add(mark);
			}
			for (Marks mark : marksList)
				System.out.println(mark);
			staffService.saveMarkstoDB(marksList);
		}
		else if(staff.getTeachingSubject().equalsIgnoreCase("na")) {
			System.out.println("\n You have allocated any teaching subject to update marks!");
		}
		else
			System.out.println("\nYou have updated the marks for registered students or no one registered Yet!!!");
		this.showOperations(loggedStaff);
	}

	public void viewMyDetails(LoginUser loggedStaff) {
		Staff staff = staffService.getStaffDetails(loggedStaff.getMailId());
		System.out.print("\nYour Staff details:");
		System.out.println("\n--------------------------------------------------------------------------------");
		System.out.printf("%s %15s %22s %10s %20s", "STAFF ID", "MAILID", "QUALIFICATION", "BRANCH",
				"TEACHING_SUBJECT");
		System.out.println("\n--------------------------------------------------------------------------------");
		System.out.format("%s %25s %10s %30s %s", staff.getStaffId(), staff.getMailId(), staff.getQualification(),
				staff.getDepartmentName(), staff.getTeachingSubject());
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------");
		this.showOperations(loggedStaff);
	}

}
