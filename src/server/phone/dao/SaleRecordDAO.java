package server.phone.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utopia84.model.Salerecord;
import com.utopia84.utils.MyDate;

/**
 * @author utopia
 * 
 */
public class SaleRecordDAO {
	DBHelper manager;
	String sql = "";
	ResultSet rs;

	public SaleRecordDAO() throws IOException, ClassNotFoundException,
			SQLException {
		manager = DBHelper.createInstance();
	}

	/**
	 * 返回所有餐桌区域信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Salerecord> getAllSaleRecord(String createTime)
			throws SQLException {
		List<Salerecord> sales = new ArrayList<Salerecord>();
		Salerecord sale = new Salerecord();
		// sql = "f";
		sql = "select * from Salerecord where createTime > ? order by createTime limit 0,100";
		Object[] params = new Object[] { createTime };
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			sale = new Salerecord(rs.getInt("itemNo"),
					rs.getString("closeTime"), rs.getString("createTime"),
					rs.getString("deskName"),rs.getString("otherSpec"),
					rs.getString("OtherSpecNo1"), rs.getString("OtherSpecNo2"),
					rs.getString("status"),rs.getString("Waiter"),rs.getString("dept"),
					(float)(rs.getDouble("subTotal")),(float)(rs.getDouble("tipTotal")),
					(float)(rs.getDouble("total")),(float)(rs.getDouble("initTotal")),
					(float)(rs.getDouble("rebateTotal")),(float)(rs.getDouble("taxTotal")));
			sales.add(sale);
		}
		manager.closeDB();
		return sales;
	}

	/**
	 * 返回所有餐桌区域信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	/**
	 * 插入打印机信息
	 * 
	 * @return
	 * @throws SQLException
	 */

	public int saveSaleRecord(List<Salerecord> records) throws SQLException {
		int result = 0;
		final int size = records.size();
		sql = "insert into Salerecord(itemNo,closeTime,createTime,deskName,otherSpec,otherSpecNo1,otherSpecNo2,status,"
				+ "waiter,dept,subTotal,tipTotal,total,initTotal,rebateTotal,taxTotal) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[][] params = new Object[size][16];
		for (int i = 0; i < records.size(); i++) {
			params[i][0] = records.get(i).getItemNo();
			params[i][1] = MyDate.getDateEN();
			params[i][2] = MyDate.getDateEN();
			params[i][3] = records.get(i).getDeskName();
			params[i][4] = records.get(i).getOtherSpec();
			params[i][5] = records.get(i).getOtherSpecNo1();
			params[i][6] = records.get(i).getOtherSpecNo2();
			params[i][7] = records.get(i).getStatus();
			params[i][8] = records.get(i).getWaiter();
			params[i][9] = records.get(i).getDept();
			params[i][10] = records.get(i).getSubTotal();
			params[i][11] = records.get(i).getTipTotal();
			params[i][12] = records.get(i).getTotal();
			params[i][13] = records.get(i).getInitTotal();
			params[i][14] = records.get(i).getRebateTotal();
			params[i][15] = records.get(i).getTaxTotal();
		}
		manager.connectDB();
		result = manager.executeBatchInsert(sql, params);
		manager.closeDB();
		return result;
	}

	public boolean updateSaleRecord(String bILLID, String pdtCODE,
			String closeTime, String status) {
		boolean result = false;
		String sql = "update  Salerecord set status=?,closeTime=? where billid=? and pdtCode=?";
		try {
			manager.connectDB();
			result = manager.executeUpdate(sql, new Object[] { status,closeTime,
					bILLID, pdtCODE });
			manager.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public List<Salerecord> getDoneSaleRecord(String deskName)
			throws SQLException {

		List<Salerecord> sales = new ArrayList<Salerecord>();
		Salerecord sale = new Salerecord();
		sql = "select * from Salerecord where deskName=? and status='Done'";
		Object[] params = new Object[] { deskName };
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while (rs.next()) {
			sale = new Salerecord();
			sale.setItemNo(rs.getInt("itemNo"));
			sale.setStatus(rs.getString("status"));
			sale.setDeskName(rs.getString("deskName"));
			sales.add(sale);
		}
		manager.closeDB();
		return sales;

	}
	
	public boolean isDrink(String ptdCode)
			throws SQLException {
		String result = "";
		sql = "Select TypeName from Product as p join Menutype as mt on mt.TypeId=p.typeId where p.pdtCode=?";
		Object[] params = new Object[] { ptdCode };
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		if (rs.next()) {
			result = rs.getString(1);
		}
		manager.closeDB();
		return result.equals("Drink");

	}
	

}