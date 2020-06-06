package com.prograd.utilites;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class Notification {

	public static void sendSms(String message, String number) {
		try {
			String apiKey = "0w7F94A6VeIpkZGEgQOzsLyRhjmxqvS5CBXNtnd1TDaiYluHb2Sd1nQtgzwbALmOJeurHRYIxc6oKUlZ";
			String senderId = "FSTSMS";
			message = URLEncoder.encode(message, "UTF-8");
			String language = "english";
			String route = "p";
			String myUrl = "https://www.fast2sms.com/dev/bulk?authorization=" + apiKey + "&sender_id=" + senderId
					+ "&message=" + message + "&language=" + language + "&route=" + route + "&numbers=" + number;
			//Connection
			URL url = new URL(myUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("cache-control", "no-cache");
			// System.out.println("Wait............");
			int code = con.getResponseCode();
			 System.out.println("Response Code: " + code);
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				response.append(line);
			}
			// System.out.println(response);
			if (code == 200) {
				System.out.println("You successfully registered and creditionals are send to registered mobile number");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
