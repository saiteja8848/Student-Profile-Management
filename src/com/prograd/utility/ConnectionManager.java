package com.prograd.utility;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {

	public static Properties loadPropertiesFile() {
		Properties prop = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("resources/jdbc.properties");
			prop = new Properties();
			prop.load(fis);
		} catch (Exception e) {
			System.out.println(e);
		}

		return prop;
	}

	public static Connection getConnection() {
		String url;
		String password;
		String userName;
		Connection con = null;

		try {
			Properties pro = loadPropertiesFile();
			url = pro.getProperty("url");
			userName = pro.getProperty("username");
			password = pro.getProperty("password");
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			System.out.println(e);
		}

		return con;
	}
}
