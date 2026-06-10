package com.student.dao;

import java.util.Scanner;

public class SystemActions extends StudentDao{
	Scanner sc = new Scanner(System.in);
	public void addCourses() {
		System.out.println("how many course do you want to add ? ");
		int i = sc.nextInt();
		addCourseUsingBatch(i);
	}
	public void addStudent() {
		System.out.println("how many Students do you want to add ? ");
		int i = sc.nextInt();
		addStudentUsingBatch(i);
	}
	
	
}
