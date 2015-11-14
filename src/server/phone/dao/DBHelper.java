package server.phone.dao;

/*
 * 数据库操作类，进行数据库底层操作
 * 配置信息在Config.properties文件中
 * Made By:utopia
 */

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;

public class DBHelper {
	private static DBHelper manager = null; // 静态成员变量，支持单态模式
	private PropertyResourceBundle bundle; // 配置资源文件
	private static String jdbcDrive = null; // JDBC驱动类型
	private String DBhost = ""; // 数据库主机地址
	private String DBuser = ""; // 数据库用户名
	private String DBpasswd = ""; // 数据库密码

	private Connection conn = null; // 连接对象
	private PreparedStatement pstm = null;
	private CallableStatement cstm = null;

	/**
	 * 私有构造函数,不可实例化
	 * 
	 * @throws IOException
	 */
	private DBHelper() throws IOException {
		// 读取配置文件
		bundle = new PropertyResourceBundle(
				DBHelper.class.getResourceAsStream("Config.properties"));
		this.DBhost = getString("DBhost"); // 读取主机名
		this.DBuser = getString("DBuser"); // 读取用户
		this.DBpasswd = getString("DBpassword"); // 读取密码
		// 设置mysql数据库的驱动程序和连接字符
		jdbcDrive = "com.mysql.jdbc.Driver";
	}

	/**
	 * 读取配置文件中的值
	 * 
	 * @param key
	 *            配置文件的key
	 * @return key对应的值
	 */
	private String getString(String key) {
		return this.bundle.getString(key);
	}

	/**
	 * 单态模式获取实例
	 * 
	 * @return SqlManager对象
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static DBHelper createInstance() throws IOException,
			ClassNotFoundException {
		if (manager == null) {
			manager = new DBHelper();
			manager.initDB();
		}
		return manager;
	}

	/**
	 * 初始化连接参数，由指定的DBType生成
	 * 
	 * @throws ClassNotFoundException
	 */
	public void initDB() throws ClassNotFoundException {
		Class.forName(jdbcDrive);
	}

	/**
	 * 连接数据库
	 * 
	 * @throws SQLException
	 */
	public void connectDB() throws SQLException {
		conn = DriverManager.getConnection(DBhost, DBuser, DBpasswd); // 获取连接
		conn.setAutoCommit(false); // 设置自动提交为false
	}

	/**
	 * 断开数据库
	 * 
	 * @throws SQLException
	 */
	public void closeDB() {
		try {
			if (pstm != null)
				pstm.close();
			if (cstm != null)
				cstm.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置PrepareStatement对象中Sql语句中的参数
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @throws SQLException
	 */
	private void setPrepareStatementParams(String sql, Object[] params)
			throws SQLException {
		pstm = conn.prepareStatement(sql); // 获取对象
		if (params != null) {
			for (int i = 0; i < params.length; i++) // 遍历参数列表填充参数
			{
				pstm.setObject(i + 1, params[i]);
			}
		}
	}

	/**
	 * 执行查询
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @return 返回ResultSet类型的查询结果
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql, Object[] params)
			throws SQLException { // 执行查询数据库接口
		ResultSet rs = null;
		manager.setPrepareStatementParams(sql, params); // 填充参数
		rs = pstm.executeQuery(); // 执行查询操作
		// closeDB();
		return rs;
	}

	/**
	 * 更新数据库操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @return 执行操作的结果
	 * @throws SQLException
	 */
	public boolean executeUpdate(String sql, Object[] params)// 执行无返回数据的数据查询，返回值是被改变的书库的数据库项数
	{
		int i = 0;
		try {
			manager.setPrepareStatementParams(sql, params);
			i = pstm.executeUpdate(); // 执行更新
			conn.commit();// 提交信息到数据库
		} catch (SQLException e) {
			try {
				conn.rollback();// 回滚JDBC事务
				i = 0;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} // 填充参数

		return i > 0;
	}

	/**
	 * 批量更新数据库操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @return 执行操作的结果
	 * @throws SQLException
	 */
	public int executeBatchInsert(String sql, Object[][] params)// 执行无返回数据的数据查询，返回值是被改变的书库的数据库项数
	{
		int result = 1;
		try {
			conn.setAutoCommit(false);

			PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				for (int j = 0; j < params[i].length; j++) {
					if (j == 4 || j == 6) {
						pst.setInt(j + 1, (Integer) params[i][j]);
					} else if (j == 5 || j == 15) {
						pst.setFloat(j + 1, (Float) params[i][j]);
					} else {
						pst.setString(j + 1, (String) params[i][j]);
					}
				}
				// 把一个SQL命令加入命令列表
				pst.addBatch();
			}
			// 执行批量更新
			pst.executeBatch();
			// 语句执行完毕，提交本事务
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();// 回滚JDBC事务
				result = 0;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;
	}

	public int insertBill(String sql, Object[][] params) {
		int result = 1;
		try {
			conn.setAutoCommit(false);

			PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement(sql);	conn.rollback();// 回滚JDBC事务
					
			for (int i = 0; i < params.length; i++) {
				for (int j = 0; j < params[i].length; j++) {
					if (j == 2 || j == 3 || j == 4 || j == 6 || j == 7) {
						pst.setFloat(j + 1, (Float) params[i][j]);
					} else {
						pst.setString(j + 1, (String) params[i][j]);
					}
				}
				// 把一个SQL命令加入命令列表
				pst.addBatch();
			}
			// 执行批量更新
			pst.executeBatch();
			// 语句执行完毕，提交本事务
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();// 回滚JDBC事务
				result = 0;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;
	}

	
	public boolean chshierInsert(String sql, Object[] params) {
		boolean flag = true;
		try {
			conn.setAutoCommit(false);
		 	PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement(sql);
			pst.setString(1, (String) params[0]);
			pst.setString(2, (String) params[1]);
			pst.setString(3, (String) params[2]);
			pst.setString(4, (String) params[3]);
			pst.setString(5, (String) params[4]);
			pst.addBatch();
			pst.executeBatch();
			// 语句执行完毕，提交本事务
			conn.commit();
			conn.setAutoCommit(true);
		
		} catch (SQLException e) {
			try {
				conn.rollback();
				flag = false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}// 回滚JDBC事务
		
			e.printStackTrace();
		}

		return flag;
	}

}
