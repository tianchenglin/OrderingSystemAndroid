package server.phone.dbhelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	private static Connection conn;
	private static PreparedStatement pstmt;

	private static String driverClass = "";
	private static String driverUrl = "";
	private static String username = "";
	private static String password = "";

	public static Connection getConnForMySql() {

		new DBUtil().init();

		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(driverUrl, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void init() {
		Properties pro = new Properties();
		try {
			pro.load(this.getClass().getClassLoader()
					.getResourceAsStream("paramsConfig.properties"));
			driverClass = pro.getProperty("driverClass");
			driverUrl = pro.getProperty("dbUrl");
			username = pro.getProperty("username");
			password = pro.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PreparedStatement getPreparedStatemnt(Connection conn,
			String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static PreparedStatement getPreparedStatemnt(Connection conn,
			String sql, String params[]) {
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setString(i + 1, params[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static void CloseResources() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
