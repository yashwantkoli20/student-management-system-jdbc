package com.student.services;

import java.util.Scanner;

import com.student.dao.SystemActions;
import com.student.main.StudentDriver;

public class SystemService {
	static SystemActions systemA = new SystemActions();
	public static void SystemMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("-------------System menu --------------");
		System.out.println("1. Batch Execution for Course.");
		System.out.println("2. Batch Execution using stored Procedure."); //for student
		System.out.println("3. go to main menu.");
		System.out.println("enter your choice : ");
		int opt = sc.nextInt();
		
		switch (opt) {
		case 1:
			systemA.addCourses();
			break;
		case 2:
			systemA.addStudent();
			break;
		case 3:
			StudentDriver.options();
			break;

		default:
			System.err.println("enter valid option");
			break;
		}
		
		System.out.println("go to System Menu . y/n");
		char charAt = sc.next().charAt(0);
		if(charAt=='y' || charAt=='Y') {
			SystemMenu();
		}
		else {
			StudentDriver.options();
		}
	}
}
