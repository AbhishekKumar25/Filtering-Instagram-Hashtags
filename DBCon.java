package com;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class DBCon{
    private static Connection con;
	
public static Connection getCon()throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    con = DriverManager.getConnection("jdbc:mysql://localhost/instagram","root","root");
    return con;
}

public static String register(String[] input)throws Exception{
    String msg="fail";
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select annotator_worker from newuser where annotator_worker='"+input[0]+"'");
    if(rs.next()){
		msg = "Username already exist";
    }else{
		PreparedStatement stat=con.prepareStatement("insert into newuser values(?,?,?,?,?)");
		stat.setString(1,input[0]);
		stat.setString(2,input[1]);
		stat.setString(3,input[2]);
		stat.setString(4,input[3]);
		stat.setString(5,input[4]);
		int i=stat.executeUpdate();
		if(i > 0){
			msg = "Registration process completed";
		}
    }
    return msg;
}
public static String login(String input[])throws Exception{
    String msg="fail";
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select annotator_worker,pass from newuser where annotator_worker='"+input[0]+"' && pass='"+input[1]+"'");
    if(rs.next()){
        msg = "pass";
    }
    return msg;
}
}
