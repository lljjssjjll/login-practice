package com.practice.login.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncPasswordUtils {

	public static String encPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	public static void main(String[] args) {
		String password = "1234";
		String encrytedPassword = encPassword(password);
		System.out.println("Encryted Password: " + encrytedPassword);
	}
	
}
