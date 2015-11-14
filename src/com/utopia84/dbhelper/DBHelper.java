package com.utopia84.dbhelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
	private Connection con = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	private static String driverClass = "";
	private static String driverUrl = "";
	private static String username = "";
	private static String password = "";

	public DBHelper() {
		try {
			init();
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			System.out.println("数据库驱动加载失败");
		}
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

	/** 连接数据库 */
	public Connection getCon() {
		try {
			con = DriverManager.getConnection(driverUrl, username, password);
		} catch (SQLException e) {
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 执行带参数的SQL语句
	 * 
	 * @param sql
	 * @param params
	 */
	public ResultSet doPstm(String sql, Object[] params) {
		if (sql != null && !sql.equals("")) {
			if (params == null)
				params = new Object[0];

			getCon();
			if (con != null) {
				try {
					pstm = con.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					for (int i = 0; i < params.length; i++) {// 加入参数
						pstm.setObject(i + 1, params[i]);
					}

					pstm.execute();
					rs = pstm.getResultSet();
				} catch (SQLException e) {
					System.out
							.println("数据库查询语句执行错误,错误地址：DBHelper.doPstm(String sql, Object[] params)");
					e.printStackTrace();
				}
			}
		}
		return rs;
		/** 返回结果集 */
	}

	/** 得到查询结果影响的行数 */
	public int getCount() throws SQLException {
		return pstm.getUpdateCount();
	}

	/** 释放资源 */
	public void closed() {
		try {
			if (pstm != null)
				pstm.close();
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
