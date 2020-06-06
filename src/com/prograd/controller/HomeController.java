package com.prograd.controller;

import com.prograd.model.LoginUser;
import com.prograd.model.SignUpUser;
import com.prograd.service.Validation;
import com.prograd.view.Application;

public class HomeController {
	
	private Validation loginAndsignUpDetailsValidation=null;
	private StaffController staffController=null;
	private StudentController studentController =null;
    private AdminController adminController = null;	
	
	public void callLoginController(LoginUser loginUser) {
		loginAndsignUpDetailsValidation = new Validation();
		if(loginAndsignUpDetailsValidation.validateLoginDetails(loginUser)) {
			switch(loginUser.getRole().toString()) {
			case "admin":
				adminController = new AdminController();
				adminController.showOperations(loginUser);
			break;
			case "student":
				studentController = new StudentController();
			    studentController.showOperations(loginUser);  
			break;
			case "staff":
				staffController = new StaffController();
				staffController.showOperations(loginUser);
			break;	
			}

		}else
			{System.out.print("!----------Failed To Login In-----------!\n");
			Application.main(null);
			}
	}

	public void callSignUpController(SignUpUser user) {
		loginAndsignUpDetailsValidation = new Validation();
		if(loginAndsignUpDetailsValidation.validate(user)==true) {
			System.out.println("Successfully Registered\n");
			switch(user.getRole().toString()) {
			case "admin":
				System.out.println("ADMIN");
			break;
			case "student":
				studentController = new StudentController();
				studentController.addStudentDetails(user);
			break;
			case "staff":
				staffController = new StaffController();
				staffController.addStaffDetails(user);
			break;	
			}			
		}else
			System.out.print("!-------------Failed to Register----------!\n");	
		Application.main(null);
	}

}
