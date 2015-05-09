package com.utopia.utils;
 

import com.utopia.Dao.sql_Area;
import com.utopia.Dao.sql_MenuType;
import com.utopia.Dao.sql_Product;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dao.sql_Staff;
import com.utopia.Dao.sql_Tax;
import com.utopia.Dao.sql_desk;
import com.utopia.Model.d_Area;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Staff;
import com.utopia.Model.d_Tax;

public class InitSql {
	private sql_Area area = null;
	private sql_MenuType menuType = null;
	private sql_desk desk = null;
	private sql_Product menu = null;
	private sql_Staff staff = null;
	private sql_Tax  tax = null;
	private sql_SaleRecord saleRecord = null;

	public InitSql() {
		area = new sql_Area();
		menuType = new sql_MenuType();
		desk = new sql_desk();
		menu = new sql_Product();
		tax = new sql_Tax();
		saleRecord = new sql_SaleRecord();
	}

	public boolean clearnAllData() {
		area.clear_all();
		menuType.clear_all();
		desk.clear_all();
		menu.clear_all();
		tax.clear_all();
		saleRecord.clear_all();
		return true;
	}

	public boolean saveArea(d_Area param) {
		area.save(param);
		return true;
	}
	
	public boolean saveMenuType(d_MenuType param) {
		menuType.save(param);
		return true;
	}
	
	public boolean saveTax(d_Tax param) {
		tax.save(param);
		return true;
	}
	
	public boolean saveStaff(d_Staff param) {
		staff.save(param);
		return true;
	}
	
	public boolean saveDesk(d_Desk param) {
		desk.save(param);;
		return true;
	}
	
	public boolean saveMenu(d_Product param) {
		menu.save(param);
		return true;
	}

	public boolean saveSaleRecords(d_SaleRecord param) {
		saleRecord.saveInit(param);
		return true  ;
	}
}