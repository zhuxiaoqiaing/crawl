package Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ConnectionUtil {
public static final String url=PropertyUtil.getPro("url");
public static final String username=PropertyUtil.getPro("username");
public static final String password=PropertyUtil.getPro("password");
public static final String Driver=PropertyUtil.getPro("Driver");
//public static final String url="jdbc:mysql://172.20.61.201:3306/rr";
//public static final String username="322";
//public static final String password="Root-322";
//public static final String Driver="com.mysql.jdbc.Driver";
public static FileUtil fu;
public static int count=0;
public static Connection getConnection(){
	Connection con=null;
	try {
		 Class.forName(Driver);
		try {
			con=DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			System.out.println("can not access to the database");
			throw new RuntimeException("can not access to the database");
		}
	} catch (ClassNotFoundException e) {
		System.out.println("can not find the class");
		throw new RuntimeException("can not find the driver class");
	}
	System.out.println("access to the database successfully");
     return con;	
}
public void close(Connection con) {
	try {
		con.close();
	} catch (SQLException e) {
	throw new RuntimeException("can not close the connection");
	}
}
}
