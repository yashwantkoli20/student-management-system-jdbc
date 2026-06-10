package com.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.student.entity.Course;

public class CourseDao extends EnrollmentDao {
	Course course = new Course();
	private static Scanner cs = new Scanner(System.in);
	public void showCourses() {
		fetchCourse();
		//		StudentDao.opt();

	}
	public void fetchCourse() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from course");
			System.out.printf("%-5s %-18s %-5s","c_id","c_name","c_credit");
			System.out.println("\n----------------------------------------");
			while(rs.next()) {
				System.out.printf("%-5d %-18s %-5.1f",rs.getInt(1),rs.getString(2),rs.getDouble(3));
				System.out.println();
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}


	public void enrollCources(int sid) {
		fetchCourse();
		System.out.println("enter course id , to enroll : ");
		int cid = cs.nextInt();
		System.out.println("Confirm to enroll ? y/n");
		char charAt = cs.next().charAt(0);
		if(charAt == 'y'|| charAt =='Y') {
			try {
				Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
				PreparedStatement st = connection.prepareStatement("insert into enrollment(s_id,c_id) values(?,?)");
				st.setInt(1, sid);
				st.setInt(2,cid);
				st.execute();
				System.out.println("contrats , enrolled success!!");
				connection.close();
				//				StudentDao.opt();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

	}


	// code for admin menu
//	public static void main(String[] args) {
//		addCourse("manual testing",4.4);
//	}
	public int addCourse(String cName,double cCredits) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			String sql = "select * from course where c_name='"+cName+"'";
			boolean fCourse = conn.createStatement().executeQuery(sql).next();
			if(fCourse) {
				System.err.println("course allready exist !!");
			}
			else {
				PreparedStatement st = conn.prepareStatement("insert into course(c_name,c_credits) values(?,?)");
				st.setString(1,cName);
				st.setDouble(2, cCredits);
				st.execute();
				System.out.println("insert successfully . ");
				conn.close();
				return 1;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}
	
	public int updateCourse(int cId) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			String sql = "select * from course where c_id='"+cId+"'";
			boolean fCourse = conn.createStatement().executeQuery(sql).next();
			if(!fCourse) {
				System.err.println("course has not be exist !!");
			}
			else {
				System.out.println("enter new credits of selected course id : ");
				double cCredits = sc.nextDouble();
				PreparedStatement st = conn.prepareStatement("update course set c_credits=? where c_id =?");
				st.setDouble(1, cCredits);
				st.setInt(2, cId);
				st.execute();
				System.out.println("Update successfull..");
				conn.close();
				return 1;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int deleteCourse(int cId) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			String sql = "select * from course where c_id='"+cId+"'";
			boolean fCourse = conn.createStatement().executeQuery(sql).next();
			if(!fCourse) {
				System.err.println("course has not be exist !!");
			}
			else {
				PreparedStatement st = conn.prepareStatement("delete from course where c_id =?");
				st.setInt(1, cId);
				int rs = st.executeUpdate();
				System.out.println("delete "+rs +" record successfully..");
				conn.close();
				return 1;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	//
	//
	// system service code
	//
	//
	
	
	public void addCourseUsingBatch(int i){
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/StudentManagementSystem","postgres","root");
			PreparedStatement st = conn.prepareStatement("insert into course(c_name,c_credits) values(?,?)");
			for(int n = 1; n<=i ;n++ ) {
				System.out.println("enter "+n+" Course Name : ");
				String cName = cs.nextLine();
				System.out.println("enter "+cName+" Course Credit : ");
				double cCredits = cs.nextDouble();
				cs.nextLine();
				st.setString(1,cName);
				st.setDouble(2, cCredits);
				st.addBatch();
				System.out.println("----------------------------");
			}
		
			st.executeBatch();
			System.out.println("insert Course's successfully . ");
			System.out.println("----------------------------");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
