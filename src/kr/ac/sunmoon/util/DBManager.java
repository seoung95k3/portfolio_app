package kr.ac.sunmoon.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBManager {
	private static DataSource datasource;
	static {
		Context context;
		try {
			context = (Context) new InitialContext().lookup("java:comp/env/");
			datasource = (DataSource) context.lookup("jdbc/mysql");		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception {
		return datasource.getConnection();
	}

	public static void close(ResultSet rs, Statement stmt, Connection con) {

		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt, Connection con/* Connection con는 인터페이스 것*/) {
		
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
