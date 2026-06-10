package com.student.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.student.main.StudentDriver;
import com.student.services.AdminService;

public class AdminActions extends StudentDao{
	private static Scanner sc = new Scanner(System.in);
	public void updateGrade(){
		fetchEnrollment();
		System.out.println("-------- fill the follow details to assign the grade : ------");
		System.out.println("enter student id : ");
		int sid = sc.nextInt();
		System.out.println("enter course id : ");
		int cid = sc.nextInt();
		System.out.println("enter Grade : ");
		String grade= sc.next().toUpperCase();
		if(assignGrade(sid, cid, grade)==-1) {
			updateGrade();
		}
	}
	
	public void crudOperation() {
		System.out.println("-------Actions on Course--------");
		System.out.println("1. Add Course : ");
		System.out.println("2. Update Course : ");
		System.out.println("3. Delete Course : ");
		System.out.println("4. Return : ");
		System.out.println("--------------------------------");
		System.out.println("enter option : ");
		int opt = sc.nextInt();
		
		switch (opt) {
		case 1:
			addNewCourse();
			break;
		case 2:
			updateExistingCourse();
			break;
		case 3:
			deleteExistingCourse();
			break;
		case 4:
			AdminService.AdminMenu();
			break;

		default:
			System.out.println("enter valid option");
			break;
		}
		
		System.out.println("go to previous page : y/n");
		char charAt = sc.next().charAt(0);
		if(charAt=='y' || charAt=='Y') {
			crudOperation();
		}
		else {
			AdminService.AdminMenu();
		}
	}
	
	public void addNewCourse(){
		System.out.println("------------------------");
		sc.nextLine();
		System.out.println("enter new Course Name : ");
		String cName = sc.nextLine();
		System.out.println("enter Credits for "+cName+" course : ");
		double cCredits = sc.nextDouble();
		if(addCourse(cName, cCredits)==-1){
			addNewCourse();
		}
		
	}
	
	public void updateExistingCourse(){
		fetchCourse();
		System.out.println("------------------------");
//		sc.nextLine();
//		System.out.println("enter new Course Name : ");
//		String cName = sc.nextLine();
		System.out.println("enter course id for update : ");
		int cId = sc.nextInt();
		if(updateCourse(cId)==-1) {
			updateExistingCourse();
		}
	}
		public void deleteExistingCourse(){
			fetchCourse();
			System.out.println("------------------------");
//			sc.nextLine();
//			System.out.println("enter new Course Name : ");
//			String cName = sc.nextLine();
			System.out.println("enter course id to delete : ");
			int cId = sc.nextInt();
			if(deleteCourse(cId)==-1) {
				deleteExistingCourse();
			}
			
		
	}
		
		public void allAdd() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
				CallableStatement call = conn.prepareCall("call add_all(?,?,?,?,?,?,?)");
				System.out.println("enter course name : ");
				String cname = sc.nextLine();
				System.out.println("enter course credit : ");
				double ccredits = sc.nextDouble();
				sc.nextLine();
				System.out.println("enter student name : ");
				String sname = sc.nextLine();
				System.out.println("enter student email : ");
				String semail = sc.nextLine();
				System.out.println("enter student date of birth : ");
				String sdob = sc.nextLine();
				System.out.println("-------details for enroll------");
				System.out.println("enter student id : ");
				int sid = sc.nextInt();
				System.out.println("enter course id : ");
				int cid = sc.nextInt();

				call.setString(1, cname);
				call.setDouble(2, ccredits);
				call.setString(3, sname);
				call.setString(4, semail);
				call.setString(5, sdob);
				call.setInt(6, sid);
				call.setInt(7, cid);
				call.execute();
				System.out.println("record added successfully. ");
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	
}
 