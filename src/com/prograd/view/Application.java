package com.prograd.view;

import java.util.Scanner;

import com.prograd.controller.HomeController;
import com.prograd.model.LoginUser;
import com.prograd.model.SignUpUser;

public class Application {
	public static void main(String args[]) {
		String[] options = { "LOGIN", "SIGNUP", "EXIT" };
		Scanner inputReader = new Scanner(System.in);
		HomeController homeController = new HomeController();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\t\t\t\t\t\tWelcome to student Profile Management");
	    System.out.print("-------------------------------------------------------------------------------------------------------------------------------");
		System.out.print("\n1.Login\n2.SignUp\n3.Exit\nPlease Enter Your Choice:");
		try {
			int choice = inputReader.nextInt();
			switch (options[choice - 1]) {
			case "LOGIN":
				homeController.callLoginController(readAndGetLoginDetails(inputReader));
				break;
			case "SIGNUP":
				homeController.callSignUpController(readAndGetSignUpDetails(inputReader));
				break;
			case "EXIT":
				System.out.println("------Thank you for Visting!-------");
				System.exit(0);
				break;
			}
		} catch (Exception e) {
			//System.out.println(e);
			
			System.out.println("--------Invalid Option------");
			Application.main(null);
		}
		inputReader.close();

	}

	public static LoginUser readAndGetLoginDetails(Scanner inputReader) throws Exception {
		LoginUser loginUser = new LoginUser();
		String[] role  = {"admin","staff","student"};
		System.out.print("\nLogin As\n--------\n1.Admin\n2.Staff\n3.Student\nPlease Enter Your Role(-1 to return):");
		int choice  = inputReader.nextInt();
		if(choice==-1) {Application.main(null);}
		loginUser.setRole(role[choice-1]);
		inputReader.nextLine();
		System.out.print("\nPlease Enter MailId:");
		String mailId = inputReader.nextLine();
		loginUser.setMailId(mailId);
		System.out.print("Please Enter password:");
		String password = inputReader.nextLine();
		loginUser.setPassword(password);	
		return loginUser;
	}

	public static SignUpUser readAndGetSignUpDetails(Scanner inputReader) {
		SignUpUser user = new SignUpUser();
		inputReader.nextLine();
		System.out.println("\n--------Please Enter the following Details:-------");
		System.out.print("Please Enter your FullName:");
		String fullName = inputReader.nextLine();
		user.setFullName(fullName);
		System.out.print("Please Enter your MailId:");
		String mailId = inputReader.nextLine();
		user.setMailId(mailId);
		System.out.print("Please Enter your MobileNo:");
		String phoneNumber = inputReader.nextLine();
		user.setPhoneNumber(phoneNumber);
		System.out.print("Please Enter your role(admin/staff/student):");
		String role = inputReader.nextLine();
		user.setRole(role.toLowerCase().trim());
		System.out.print("Enter password(min-length-8,atleast 1 upperCase,LowerCase,SpecialCharacter,a digit):");
		String password = inputReader.nextLine();
		user.setPassword(password);
		return user;
	}

}
