package com.student.main;

import java.util.Scanner;

import com.student.services.AdminService;
import com.student.services.StudentService;
import com.student.services.SystemService;

public class StudentDriver {
	private static Scanner sc = new Scanner(System.in);
	public static void options() {
		System.out.println("----------Student Management System---------");
		System.out.println("1. Student Menu");
		System.out.println("2. Admin Menu");
		System.out.println("3. System Features");
		System.out.println("4. Exit");
		
		System.out.println("enter your choice : ");
		int opt = sc.nextInt();
		
		switch (opt) {
		case 1:
			StudentService.StudentMenu();
			break;
		case 2:
			AdminService.AdminMenu();
			break;
		case 3:
			SystemService.SystemMenu();
			break;
		case 4:
			System.out.println("Exit...");
			break;
		default:
			System.out.println("invalid input!!");
			break;
		}
	}
	public static void main(String[] args) {
		options();
	}
}
