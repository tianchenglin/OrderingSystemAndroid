package com.utopia84.dbhelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;


/**
 * 数据库操作类
 * 
 * @author root
 * 
 */
public class OpDB {
	private DBHelper mydb;

	public OpDB() {
		mydb = new DBHelper();
	}

	/**
	 * 返回菜单类型
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public TreeMap<Integer, String> OpGetListBox(String sql, Object[] params) {
		TreeMap<Integer, String> typeMap = new TreeMap<Integer, String>();

		try {
			ResultSet rs = mydb.doPstm(sql, params);
			while (rs.next()) {
				Integer sign = Integer.valueOf(rs.getInt(1));
				String intro = rs.getString(2);
				typeMap.put(sign, intro);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("菜单类型查询错误");
			e.printStackTrace();
		} finally {
			mydb.closed();
		}
		return typeMap;
	}
	/**
	 * 执行更新语句，返回影响行数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int OpUpdate(String sql, Object[] params) {
		int i = -1;
		mydb.doPstm(sql, params);
		try {
			i = mydb.getCount();
		} catch (SQLException e) {
			System.out.println("更新失败");
			e.printStackTrace();
		} finally {
			mydb.closed();
		}
		return i;
	}
}
