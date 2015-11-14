package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Desk;

/**
 * @author utopia
 * 
 */
public class DeskDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public DeskDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	/**
	 * 返回所有餐桌状态
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Desk> getAllDesk(String id, String area) throws SQLException {
		List<Desk> desks = new ArrayList<Desk>();
		Desk desk = new Desk();
		Object[] params = new Object[] { area };
		if (area.equals("")) {
			sql = "select * from Desk where delmark=0";
			params = null;
		} else {
			sql = "select * from Desk as d join Area as a on a.areaId = d.typeId where d.delmark=0 and a.areaName=?";
		}
		
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			desk = new Desk(rs.getString("typeId"),
					rs.getString("state"), rs.getString("SAccount"),
					rs.getString("deskName"), rs.getInt("statetime"),
					rs.getString("starttime"), rs.getInt("peopleNum"),
					rs.getInt("row"), rs.getInt("col"), rs.getInt("delmark"),
					rs.getInt("message"));
			desks.add(desk);
		}
		manager.closeDB();
		return desks;
	}

	/**
	 * 设置餐桌状态
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean setDesk(Desk desk) throws SQLException {
		boolean flag = false;
		sql = "update  Desk set state=?,SAccount=?,starttime=?,peopleNum=?,message=?  where deskName=? and delmark=0";
		Object[] params = new Object[] { desk.getState(), desk.getSAccount(),
				desk.getStarttime(), desk.getPeopleNum(),desk.getMessage(), desk.getDeskName() };
		manager.connectDB();
		flag = manager.executeUpdate(sql, params);
		manager.closeDB();
		return flag;
	}

	/**
	 * 返回单个餐桌状态
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Desk getDesk(String id) throws SQLException {
		Desk desk = new Desk();
		sql = "select * from Desk where id=? delmark=0";
		Object[] params = new Object[] { id };
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			desk = new Desk(rs.getString("typeId"),
					rs.getString("state"), rs.getString("SAccount"),
					rs.getString("deskName"), rs.getInt("statetime"),
					rs.getString("starttime"), rs.getInt("peopleNum"),
					rs.getInt("row"), rs.getInt("col"), rs.getInt("delmark"),
					rs.getInt("message"));
		}
		manager.closeDB();
		return desk;
	}

	/**
	 * 更新餐桌状态
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean updateDeskAdd(String desk_name) throws SQLException {
		boolean flag = false;
		sql = "update  Desk set message=message+1  where deskName=? and delmark=0";
		Object[] params = new Object[] { desk_name };
		manager.connectDB();
		flag = manager.executeUpdate(sql, params);
		manager.closeDB();
		return flag;
	}

	public boolean updateDeskSub(String deskname) throws SQLException {
		boolean flag = false;
		sql = "update  Desk set message=message-1  where deskName=? and delmark=0";
		Object[] params = new Object[] { deskname };
		manager.connectDB();
		flag = manager.executeUpdate(sql, params);
		manager.closeDB();
		return flag;
	}

}
