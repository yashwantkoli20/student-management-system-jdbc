package com.student.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EnrollmentDao {
	Scanner sc = new Scanner(System.in);
	public void showGrade(int sid) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			Statement st = connection.createStatement();
			PreparedStatement pst = connection.prepareStatement("select c_name,COALESCE(grade, 'NA') "
																+ "from course c1, enrollment e1 "
																+ "where c1.c_id = e1.c_id and "
																+ "e1.s_id = "+sid);
			ResultSet rs = pst.executeQuery();
			boolean next = rs.next();
			if(next) {
				System.out.printf(" %-18s %-5s","course","grade\n");
				while(next) {
					System.out.printf(" %-18s %-5s",rs.getString(1),rs.getString(2));
					System.out.println();
					next = rs.next();
				}
			}else {
				System.err.println("please enroll the course first");
			}
			System.out.println("------------------------------");
			connection.close();
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
	}
	
	
	
	// code for admin
	//
	//
	//start 
	
	public void fetchEnrollment() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from enrollment order by e_id asc");
			System.out.printf("%-8s %-15s %-20s %-12s","en_id","std_id","c_id","grade");
			System.out.println("\n------------------------------------------------------------");
			while(rs.next()) {
				System.out.printf("%-8d %-15d %-20d %-12s",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4));
				System.out.println();
			}
			System.out.println("------------------------------------------------------------");
			connection.close();
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
	}
	
	public void getAllEnrollments() {
			fetchEnrollment();
			boolean check = true;
			while(check) {
				System.out.println("do you want to check indivisual student enrollment details : y/n");
				char charAt = sc.next().charAt(0);
				if(charAt == 'y' || charAt == 'Y') {
					System.out.println("enter student id to check course enrollments : ");
					int s_id = sc.nextInt();
					getEnrollmentDetails(s_id);
				}else {
					check = false;
				}
			}
	}
	

	public int assignGrade(int sid,int cid,String grade) {
		int i = -1;
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			String sql1 = "select * from student where s_id="+sid;
			boolean next = connection.createStatement().executeQuery(sql1).next();
			if(next) {
				String sql2 = "select * from enrollment where s_id="+sid+" and c_id="+cid;
				boolean next1 = connection.createStatement().executeQuery(sql2).next();
				if(next1) {
					CallableStatement st = connection.prepareCall("select assing_grade(?,?,?)");
					st.setInt(1, sid);
					st.setInt(2, cid);
					st.setString(3, grade);
					ResultSet rs = st.executeQuery();
					rs.next();
					System.out.println(rs.getString(1));	
					return 1;
				}
				else {
					System.err.println("student has not enrolled in given course");
				}
			}else {
				System.err.println("please enter valid student id . ");
			}
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
//		System.out.println(e.toString());
		}
		return i;
	}
	
//	public static void main(String[] args) {
//		assignGrade(3,1,"b+");
//	}


	private void getEnrollmentDetails(int sid) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			Statement st = connection.createStatement();
			//fetch student data
			ResultSet stdData = connection.createStatement().executeQuery("select * from student where s_id="+sid);
			if(stdData.next()) {
//			System.out.println("student id : "+stdData.getInt(1));
			System.out.println("student name : "+stdData.getString(2));
			System.out.println("student email : "+stdData.getString(3));
			}else {
				System.err.println("enter valid student id");
				return;
			}
			//fetch course details : 
			PreparedStatement pst = connection.prepareStatement("select c_name,COALESCE(grade, 'NA') "
																+ "from course c1, enrollment e1 "
																+ "where c1.c_id = e1.c_id and "
																+ "e1.s_id = "+sid);
			ResultSet rs = pst.executeQuery();
			boolean next = rs.next();
			if(next) {
				System.out.println("--------------------------------");
				System.out.printf(" %-18s %-5s","course","grade");
				System.out.println("\n--------------------------------");
				while(next) {
					System.out.printf(" %-18s %-5s",rs.getString(1),rs.getString(2));
					System.out.println();
					next = rs.next();
				}
			}else {
				System.err.println("this student has not enrolled any course. ");
			}
			System.out.println("--------------------------------");
			connection.close();
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		
		
		// add
	}
	
	
}
