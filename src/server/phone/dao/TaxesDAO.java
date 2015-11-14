package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Taxes;

/**
 * @author utopia
 * 
 */
public class TaxesDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public TaxesDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	/**
	 * 返回所有餐桌区域信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Taxes> getAllTaxes() throws SQLException {
		List<Taxes> taxs = new ArrayList<Taxes>();
		Taxes tax = new Taxes();
		sql = "select * from Taxes";
		Object[] params = new Object[] {};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			tax = new Taxes(rs.getInt("id"),rs.getString("taxeName"),
					rs.getFloat("rate"));
			taxs.add(tax);
		}
		manager.closeDB();
		return taxs;
	}
}
