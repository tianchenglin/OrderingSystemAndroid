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
public class TestDAO {
	DBHelper manager;
	ResultSet rs;

	public TestDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	/**
	 * 返回所有餐桌区域信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getTest() {
		String numbers = "";
		String sql[] = { "select count(*) from Product",
				"select count(*) from Desk", "select count(*) from Area",
				"select count(*) from Menutype", "select count(*) from Taxes",
				"select count(*) from Salerecord",
				"select count(*) from Bill", "select count(*) from Cashier","select count(*) from Contact" };
		for (int i = 0; i < sql.length; i++) {
			try {

				manager.connectDB();

				rs = manager.executeQuery(sql[i], null);
				if (rs.next()) {
					numbers += rs.getInt(1) + ",";
				} else {
					numbers += "0,";
				}
				manager.closeDB();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return numbers;
	}
}
