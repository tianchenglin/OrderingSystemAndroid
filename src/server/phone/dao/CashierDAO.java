package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Cashier;

public class CashierDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public CashierDAO() throws IOException, ClassNotFoundException, SQLException {
		manager = DBHelper.createInstance();
	}

	public Boolean save(String changeMoney, String createTime, String userCode,
			String cashierId, String status) {
		boolean flag = false;
		sql="insert into Cashier(changeMoney,createTime,userCode,cashierId,status) values(?,?,?,?,?)";
		Object[] params = new Object[5];
		params[0] = changeMoney;
		params[1] = createTime;
		params[2] = userCode;
		params[3] = cashierId;
		params[4] = status;
		
		try {
			manager.connectDB();
			flag = manager.chshierInsert(sql, params);
			manager.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean saveInitMoney(String initMoney, String createTime,
			String userCode, String cashierId, String status) {
		boolean flag = false;
		sql="insert into Cashier(initMoney,createTime,userCode,cashierId,status) values(?,?,?,?,?)";
		Object[] params = new Object[5];
		params[0] = initMoney;
		params[1] = createTime;
		params[2] = userCode;
		params[3] = cashierId;
		params[4] = status;
		
		try {
			manager.connectDB();
			flag = manager.chshierInsert(sql, params);
			manager.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public List<Cashier> getCasher() {
		Cashier cashier = new Cashier();
		List<Cashier> cashiers = new ArrayList<Cashier>(); 
		sql = "select *from Cashier order by createTime limit 0,50";
		try {
			manager.connectDB();
			rs = manager.executeQuery(sql, null);
			while (rs.next()) {
				cashier = new Cashier(rs.getInt("id"),rs.getFloat("currentMoney"),
						rs.getFloat("initMoney"), rs.getFloat("changeMoney"),
						rs.getString("createTime"), rs.getString("userCode"),rs.getString("cashierId"), rs.getString("status"));
				cashiers.add(cashier);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cashiers;
	}
}
