package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Area;

/**
 * @author utopia
 * 
 */
public class AreaDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public AreaDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	/**
	 * 返回所有餐桌区域信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Area> getAllArea() throws SQLException {
		List<Area> areas = new ArrayList<Area>();
		Area area = new Area();
		sql = "select * from Area";
		Object[] params = new Object[] {};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			area = new Area(rs.getString("areaId"), rs.getString("areaName"));
			areas.add(area);
		}
		manager.closeDB();
		return areas;
	}
}
