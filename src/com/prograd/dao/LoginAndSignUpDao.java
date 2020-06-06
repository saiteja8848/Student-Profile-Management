package com.prograd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.prograd.model.LoginUser;
import com.prograd.model.SignUpUser;
import com.prograd.utilites.Notification;
import com.prograd.utility.ConnectionManager;

public class LoginAndSignUpDao {

	private Connection connection = ConnectionManager.getConnection();
	private Statement statement;
	private PreparedStatement preStatement;
	private ResultSet resultSet;

	public boolean checkForExistence(String mailId) {
		boolean status = false;
		try {
			if (connection != null) {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM USERSDATA");
				ArrayList<String> data = new ArrayList<>();
				while (resultSet.next()) {
					data.add(resultSet.getString(2));
				}
				status = data.size() == 0 ? true : data.contains(mailId);
				resultSet.close();
				statement.close();
				resultSet.close();
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}

	public void save(SignUpUser user) {
		try {
			if (connection != null) {
				preStatement = connection.prepareStatement("INSERT INTO USERSDATA VALUES(?,?,?,?,?)");
				preStatement.setString(1, user.getFullName());
				preStatement.setString(2, user.getMailId());
				preStatement.setString(3, user.getPhoneNumber());
				preStatement.setString(4, user.getRole());
				preStatement.setString(5, user.getPassword());
				preStatement.executeUpdate();
				preStatement.close();
				connection.close();
				System.out.println("\n"+ user);
				sendNotification(user.getFullName(),user.getMailId(),user.getPassword(),user.getPhoneNumber());
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void sendNotification(String name, String mailId, String password, String phoneNumber) {
		String message = "Hi " + name + " you are successfully logged in!!! userId:" + mailId + " password:" + password;
		Notification.sendSms(message, phoneNumber);
	}
	
	
	public boolean checkForLoginDetails(LoginUser loginUser) {
		boolean status = false,mail=false,role=false,password=false;
		try {
			if (connection != null) {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM USERSDATA WHERE MAILID = "+"'"+loginUser.getMailId()+"'");
				while (resultSet.next()) {
				    mail = resultSet.getString(2).contentEquals(loginUser.getMailId().toString());
				    role = resultSet.getString(4).trim().toString().contentEquals(loginUser.getRole().toString());
				    password = resultSet.getString(5).contentEquals(loginUser.getPassword().toString());
				    if(mail==true)
					  { if(role==true) {
				        if(password==true) {  
				        	status =true;break;}
				        }
					  }
				}
				
			    if(mail==false) {
					System.out.println("\n--------MailId is  Wrong!------------");
				}
			    else if(mail==false&&password==false) {
					System.out.println("\n-----------MailId and Password Not Registered,Please Check Once----------");
				}
				else
					if(password ==false)
						System.out.println("\n------Password is Wrong!-------");
				resultSet.close();
				statement.close();
				resultSet.close();
			} else
				System.out.println("Check Your Connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
}
