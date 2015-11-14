package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Bill;

public class BillDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public BillDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	public int saveBill(List<Bill> bills) throws SQLException {
		int result = 0;
		sql = "insert into Bill(billId,waiter,subtotal,tax,total,createTime,distant,tip) values(?,?,?,?,?,?,?,?)";
		Object[][] params = new Object[bills.size()][8];
		for (int i = 0; i < bills.size(); i++) {
			params[i][0] = bills.get(i).getBillId();
			params[i][1] = bills.get(i).getWaiter();
			params[i][2] = bills.get(i).getSubtotal();
			params[i][3] = bills.get(i).getTax();
			params[i][4] = bills.get(i).getTotal();
			params[i][5] = bills.get(i).getCreateTime();
			params[i][6] = bills.get(i).getDistant();
			params[i][7] = bills.get(i).getTip();
		}
		manager.connectDB();
		result = manager.insertBill(sql, params);
		manager.closeDB();
		return result;
	}

	public List<Bill> getBill() {
		List<Bill> bills = new ArrayList<Bill>();
		Bill bill = new Bill();
		sql = "select *from Bill";
		Object[] params = new Object[] {};
		try {
			manager.connectDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs = manager.executeQuery(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				bill = new Bill(rs.getString("billId"),rs.getString("createTime"),rs.getFloat("distant"),
								rs.getFloat("subtotal"),rs.getFloat("tax"),rs.getFloat("tip"),rs.getFloat("total"),
								rs.getString("waiter"),rs.getInt("salerecordId"),
								rs.getFloat("rebate"),rs.getFloat("initTotal"),rs.getString("tipPayment"),
								rs.getString("payment"),rs.getInt("cashierId"));
				bills.add(bill);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.closeDB();
		return bills;
	}
}
