package com.CodeStorage.test;

import java.util.Scanner;

public class InputValidator {
	
	public static boolean ValidProjectName(String name) {
		return name.matches("[A-Za-z0-9 ]+");
	}
	
	public static boolean ValidPackageName(String name) {
		return name.matches("^(?=.*[A-Za-z])[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)*$");
	}
	
	public static boolean ValidJavaFileName(String name) {
		return name.matches("^[A-Z][A-Za-z0-9]*$");
	}
	
	public static int getInt(Scanner sc, String message) {

	    while (true) {

	        System.out.print(message);

	        if (sc.hasNextInt()) {

	            int value = sc.nextInt();
	            sc.nextLine(); 
	            return value;

	        } else {

	            System.out.println("Invalid input! Please enter a valid numeric ID.");
	            sc.nextLine();

	        }

	    }

	}

}
