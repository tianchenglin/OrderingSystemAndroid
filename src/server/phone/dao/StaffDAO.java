package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Staff;

/**
 * @author utopia
 * 
 */
public class StaffDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public StaffDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	/**
	 * 返回用户名信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Staff getUserInfo(String s_account) throws SQLException {
		Staff staff = new Staff();
		sql = " select s.SAccount , s.SPwd,s.SName,st.typeName,st.priority from Staff as s left join Stafftype as st on s.SType=st.priority where s.SAccount=? and s.delmark=0";
		Object[] params = new Object[] { s_account };
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		if (rs.next()) {
			staff = new Staff(rs.getString("SAccount"), 
					rs.getString("SName"), rs.getString("typeName"),
					rs.getInt("priority"));
		}
		manager.closeDB();
		return staff;
	}

	public boolean addUserInfo(Staff staff) throws SQLException {
		boolean flag = false;
		
		sql = "select * from Staff where s_account=? and delmark=0";
		Object[] params = new Object[] { staff.getSAccount()};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		if (rs.next()) {
			return false;
		}
		sql = " insert into Staff(s_account,s_pwd,s_name,s_type) values(?,?,?,?)";
		Object[] params1 = new Object[] { staff.getSAccount(),
				staff.getSPwd(), staff.getSName(), staff.getSType() };
		flag = manager.executeUpdate(sql, params1);
		manager.closeDB();
		return flag;
	}

	/**
	 * 验证用户名和密码是否正确
	 * 
	 * @param s_account员工编号
	 * @return 处理结果
	 * @throws SQLException
	 */
	public String validate(String s_account) throws SQLException {
		String result = "failure";
		sql = "select * from Staff where SAccount=? and delmark=0";
		Object[] params = new Object[] { s_account };
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		if (rs.next()) {
			if (rs.getInt("SOnline") == 1) {
				result = "online";
			} else {
				result = "success";
				//manager.executeUpdate("update staff set s_online='1' where s_account=?",
				//		params);
			}
		}
		manager.closeDB();
		return result;
	}

	/**
	 * 验证用户名和密码是否正确
	 * 
	 * @param s_account员工编号
	 * @return 处理结果
	 * @throws SQLException
	 */
	public boolean logout(String s_account) throws SQLException {
		sql = "update Staff set s_online='0' where s_account=?";
		Object[] params = new Object[] { s_account };
		manager.connectDB();
		manager.executeUpdate(sql, params);
		manager.closeDB();
		return true;
	}
	
	/**
	 * 返回所有餐桌区域信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Staff> getAllStaff() throws SQLException {
		List<Staff> staffs = new ArrayList<Staff>();
		Staff staff = new Staff();
		sql = " select s.SAccount , s.SPwd,s.SName,st.typeName,st.priority from Staff as s left join Stafftype as st on s.SType=st.priority where s.delmark=0; ";
		Object[] params = new Object[] {};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			staff = new Staff(rs.getString("SAccount"), rs.getString("SPwd"),
					rs.getString("SName"), rs.getString("typeName"),
					rs.getInt("priority"));
			staffs.add(staff);
		}
		manager.closeDB();
		return staffs;
	}

}
