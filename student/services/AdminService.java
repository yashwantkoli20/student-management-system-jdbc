package com.student.services;

import java.util.Scanner;

import com.student.dao.AdminActions;
import com.student.main.StudentDriver;

public class AdminService {
	static AdminActions admin = new AdminActions();
	public static void AdminMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("-------------Admin menu --------------");
		System.out.println("1. Add/Update/Delete Course");
		System.out.println("2. View all Student.");
		System.out.println("3. View All Courses.");
		System.out.println("4. View All Enrollments.");
		System.out.println("5. Assign Grades. "); // using functions
		System.out.println("6. Add Course , student,Enrollments "); //using store procedures
		System.out.println("7. go to main menu");
		System.out.println("enter your choice : ");
		int opt = sc.nextInt();
		
		switch (opt) {
		case 1:
				admin.crudOperation();
			break;
		case 2:
				admin.getAllStudents();
			break;
		case 3:
				admin.showCourses();
			break;
		case 4:
				admin.getAllEnrollments();
			break;
		case 5:
				admin.updateGrade();
			break;
		case 6:
				admin.allAdd();
			break;
		case 7:
			StudentDriver.options();
			break;

		default:
			System.err.println("invalid input !!!");
		}
		
		System.out.println("go to previous page : y/n");
		char charAt = sc.next().charAt(0);
		if(charAt=='y' || charAt=='Y') {
			AdminMenu();
		}
		else {
			StudentDriver.options();
		}

	}
	
}
