package com.prograd.service;

import com.prograd.dao.LoginAndSignUpDao;
import com.prograd.model.LoginUser;
import com.prograd.model.SignUpUser;

public class Validation {

	private LoginAndSignUpDao loginAndSignUpDao = new LoginAndSignUpDao();
	
	public boolean validateName(String name) {
		return name.matches("^[a-zA-Z]*$");
	}

	public boolean validateEmailId(String emailId) {
		return emailId.matches("^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$");
	}

	public boolean validatePassword(String password) {
		return password.matches("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
	}

	public boolean validatePhoneNumber(String phoneNumber) {
		return phoneNumber.matches("(0/91)?[7-9][0-9]{9}");
	}

	public boolean checkForExistenceOFSameFields(SignUpUser user) {
		return !loginAndSignUpDao.checkForExistence(user.getMailId());
	}

	//FOR SIGNUP
	public boolean validate(SignUpUser user) {
       boolean name = validateName(user.getFullName());
       boolean mailId = validateEmailId(user.getMailId());
       boolean phoneNumber = validatePhoneNumber(user.getPhoneNumber());
       boolean password = validatePassword(user.getPassword());
       boolean sameFields =checkForExistenceOFSameFields(user);
       
       if(name&&mailId&&phoneNumber&&password&&sameFields) {
    	   loginAndSignUpDao.save(user);
    	   return true;
       }else if(mailId==false&&phoneNumber==false) {
    	   System.out.println("\n----------Every Detail You Provided is Not Valid----------");
    	   return false;
       }
       else if(name==false) {
    	 System.out.println("\n-----------Name Should Contain Only Alphabets----------");
    	 return false;
       }
       else if(mailId==false) {
    	   System.out.println("\n----------Provide  Valid EmailId!-------------");
    	   return false;
       }
       else if(phoneNumber==false) {
    	   System.out.println("\n-----------Provide  Valid MobileNumber!------------");
    	   return false;
       }
       else if(sameFields==false) {
    	   System.out.println("\n--------Some One Registered With the Same MailId!----------");
    	   return false;
       }
       else
    	   return false;
       
	}
	
	//FOR LOGIN 
	public boolean validateLoginDetails(LoginUser loginUser) {
		return loginAndSignUpDao.checkForLoginDetails(loginUser);
	}
	
	
}
