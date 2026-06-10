package com.student.services;

import java.util.Scanner;

import com.student.dao.CourseDao;
import com.student.dao.StudentDao;
import com.student.main.StudentDriver;

public class StudentService {
	private static Scanner sc = new Scanner(System.in);
	static StudentDao std = new StudentDao();
//	static CourseDao course = new CourseDao();
	public static void StudentMenu() {
		System.out.println("---------Student menu ----------");
		System.out.println("1. Register Student ");
		System.out.println("2. Update Student Profile ");
		System.out.println("3. Enroll Course");
		System.out.println("4. View All courses ");
		System.out.println("5. View Grade ");
		System.out.println("6. View Student Profile");
		System.out.println("7. Go to Main Menu ");
		
		System.out.println("enter your choice : ");
		int opt = sc.nextInt();
		
		switch (opt) {
		case 1:
				std.RegisterStudent();
			
			break;
		case 2:
				std.updateProfile();
			break;
		case 3:
				std.getCourse();
			break;
		case 4:
				std.showCourses();
			break;
		case 5:
			std.checkGrade();
			break;
		case 6:
			std.viewProfile();
			break;
		case 7:
			StudentDriver.options();
			break;

		default:
			System.out.println("invalid option . ");
			break;
		}
		
		System.out.println("go to previous page : y/n");
		char charAt = sc.next().charAt(0);
		if(charAt=='y' || charAt=='Y') {
			StudentMenu();
		}
		else {
			StudentDriver.options();
		}
	}
}
