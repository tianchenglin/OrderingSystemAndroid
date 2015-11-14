package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Menus;
import com.utopia84.model.Menutype;

/**
 * @author utopia
 * 
 */
public class MenuDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public MenuDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	/**
	 * 返回菜单类型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Menutype> getMenutype() throws SQLException {
		List<Menutype> menuTypes = new ArrayList<Menutype>();
		Menutype mt = new Menutype();
		sql = " select * from Menutype";
		Object[] params = new Object[] {};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			mt = new Menutype(rs.getString("TypeId"),rs.getString("TypeName"),
					rs.getString("TypeParentId"));
			menuTypes.add(mt);
		}
		manager.closeDB();
		return menuTypes;
	}

	/**
	 * 返回菜单类型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Menus> getMenus() throws SQLException {
		List<Menus> menus = new ArrayList<Menus>();
		Menus menu = new Menus();
		sql = " select * from Product";
		Object[] params = new Object[] {};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			menu = new Menus(rs.getString("departId"),
					rs.getString("pdtAccType"), rs.getInt("pdtAutoInc"),
					rs.getShort("pdtCanUsed"), rs.getShort("pdtCanZk"),
					rs.getShort("pdtChangePrice"), rs.getString("pdtCode"),
					rs.getString("pdtGg"), rs.getString("pdtId"),
					rs.getShort("pdtInMix"), rs.getString("pdtInPrice"),
					rs.getString("pdtMCode"), rs.getShort("pdtMakeTime"),
					rs.getString("pdtName"), rs.getShort("pdtNoShow"),
					rs.getFloat("pdtPayType"), rs.getString("pdtPy"),
					rs.getString("pdtSalePrice1"), rs.getString("pdtSalePrice2"),
					rs.getString("pdtUnit"), rs.getShort("pdtisSet"),
					rs.getInt("typeId"), rs.getInt("minrebate"),
					rs.getShort("notout"), rs.getShort("notshow"),
					rs.getShort("notshowonbill"), rs.getShort("pdtchgNumber"),
					rs.getString("pdtdownprice1"), rs.getString("pdtdownprice2"));
			menus.add(menu);
			
		}
		manager.closeDB();
		return menus;
	}
}
