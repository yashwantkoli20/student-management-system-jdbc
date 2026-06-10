package com.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.student.entity.Student;
import com.student.main.StudentDriver;
import com.student.services.StudentService;

public class StudentDao extends CourseDao{
	private Student std = new Student() ;
	private static Scanner  sc = new Scanner(System.in);

	
	public void RegisterStudent() {
		try {
			System.out.println(" already Registered ? y/n");
			char charAt = sc.next().charAt(0);

			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			if(charAt =='n') {
//				System.out.println("enter id : ");
//				int id = sc.nextInt();
//				
				sc.nextLine();
				System.out.println("enter your name : ");
				String name = sc.nextLine();
				System.out.println("enter your email : ");
				String email = sc.next();
				System.out.println("enter your date of birth : dd/mm/yyyy");
				String dob = sc.next();
				PreparedStatement pst = connection.prepareStatement("insert into student(s_name,s_email,s_dob) values(?,?,?)");
//				pst.setInt(1, id);
//				std.setId(id);
				pst.setString(1, name);
				std.setName(name);
				pst.setString(2, email);
				std.setEmail(email);
				pst.setString(3, dob);
				std.setDob(dob);
				pst.execute();

				System.out.println("register successfull....");
			}else {
				sc.nextLine();
				System.out.println("enter your name : ");
				String name = sc.nextLine();
				System.out.println("enter your email : ");
				String email = sc.next();
				PreparedStatement st = connection.prepareStatement("select * from student where s_name =? and s_email =?");
				st.setString(1, name);
				st.setString(2, email);
				ResultSet rs = st.executeQuery();
				if(rs.next()) {
					std.setId( rs.getInt(1));
					std.setName(rs.getString(2));
					std.setEmail(rs.getString(3));
					std.setDob(rs.getString(4));
					System.out.println("congrats student exist.");
				}else {
					System.err.println("not exist");
				}
				
			}
			connection.close();
			System.out.println("-------------------------");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}	

	public void updateProfile() {
		int id = std.getId();
		if(id ==0) {
			System.err.println("register first");
			return;
		}
		System.out.println(" your id : "+ id);
		System.out.println("enter new email for update : ");
		String email = sc.next();

		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			PreparedStatement pst = connection.prepareStatement("update student set s_email =? where s_id=?");
			pst.setInt(2, id);
			pst.setString(1, email);
			pst.execute();
			std.setEmail(email);
			System.out.println("update successfull....");
			System.out.println("--------------------------");
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void viewProfile() {
		int id = std.getId();
		if(id!=0) {
			try {
				Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
				PreparedStatement pst = connection.prepareStatement("select * from student where s_id = ?");
				pst.setInt(1, id);

				ResultSet rs = pst.executeQuery();

				if(rs.next()) {

					System.out.println("-------------Student Information -------------");
					System.out.println("id : "+std.getId());
					System.out.println("name : "+std.getName());
					System.out.println("email : "+std.getEmail());
					System.out.println("date of birth : "+std.getDob());	
					System.out.println("-----------------------------------------------");
				}else {
					System.err.println("record not found!!");
				}
				connection.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}else {
			System.err.println("register first");
		}
	}

	


	public void getCourse() {
		int id = std.getId();
		if(id ==0) {
			System.err.println("register first");
			return;
		}
		enrollCources(id);
		
	}

	//	public void enrolledCources() {
	//		System.out.println("enter your id : ");
	//		int id = sc.nextInt();
	//		try {
	//			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
	//			PreparedStatement pst = connection.prepareStatement("select * from course where c_id = (select c_id"
	//					+ " from enrollment where s_id = ? )");
	//			pst.setInt(1, id);
	//			
	//			ResultSet rs = pst.executeQuery();
	//			
	//			if(rs.next()) {
	//		
	//			}else {
	//				System.err.println("you are not enrolled in any course!!");
	//			}
	//			connection.close();
	//			opt();
	//		} catch (SQLException e) {
	//		System.out.println(e.getMessage());
	//		}
	//	}
	
	public void checkGrade() {
		int id = std.getId();
		if(id ==0) {
			System.err.println("register first");
			return;
		}
		showGrade(id);
	
	}
	
	// backend for admin 
	
	
	public void getAllStudents() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from student");
			System.out.printf("%-8s %-15s %-20s %-12s","std_id","std_name","std_email","std_dob");
			System.out.println("\n------------------------------------------------------------");
			while(rs.next()) {
				System.out.printf("%-8d %-15s %-20s %-12s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
				System.out.println();
			}
			connection.close();
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
	}
	

	//
	//
	// system service code
	//
	//
	
//	public static void main(String[] args) {
//		addStudentUsingBatch(1);
//	}
	
	public void addStudentUsingBatch(int i){
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			PreparedStatement st = conn.prepareStatement("call add_student(?,?,?)");
			for(int n = 1; n<=i ;n++ ) {
				System.out.println("enter Student " +n+" Name : ");
				String sName = sc.nextLine();
				System.out.println("enter "+sName+"'s email id : ");
				String sEmail = sc.next();
				System.out.println("enter "+sName+"'s date of birth id : ");
				String sDob = sc.next();
								sc.nextLine();
				st.setString(1,sName);
				st.setString(2, sEmail);
				st.setString(3, sDob);
				st.addBatch();
				System.out.println("----------------------------");
			}
		
			st.executeBatch();
			System.out.println("insert students successfully . ");
			System.out.println("----------------------------");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
