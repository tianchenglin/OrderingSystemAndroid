package com.utopia.utils;
 

import com.utopia.Dao.sql_Area;
import com.utopia.Dao.sql_Bill;
import com.utopia.Dao.sql_Cashier;
import com.utopia.Dao.sql_Contact;
import com.utopia.Dao.sql_MenuType;
import com.utopia.Dao.sql_Product;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dao.sql_Saleandpdt;
import com.utopia.Dao.sql_Sales;
import com.utopia.Dao.sql_Tax;
import com.utopia.Dao.sql_desk;
import com.utopia.Model.d_Area;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Cashier;
import com.utopia.Model.d_Contact;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_Sale;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Saleandpdt;
import com.utopia.Model.d_Tax;

public class InitSql {
	private sql_Area area = null;
	private sql_MenuType menuType = null;
	private sql_desk desk = null;
	private sql_Product menu = null;
	private sql_Tax  tax = null;
	private sql_Sales saleRecord = null;
	private sql_Saleandpdt saleanddpt=null;
	private sql_Bill bill= null ; 
	private sql_Cashier cashier = null;
	private sql_Contact contact = null;
	
	
	public InitSql() {
		area = new sql_Area();
		menuType = new sql_MenuType();
		desk = new sql_desk();
		menu = new sql_Product();
		tax = new sql_Tax();
		saleRecord = new sql_Sales();
		saleanddpt=new sql_Saleandpdt();
		bill = new sql_Bill();
		cashier = new sql_Cashier();
		contact = new sql_Contact();
	}

	public boolean clearnAllData() {
		area.clear_all();
		menuType.clear_all();
		desk.clear_all();
		menu.clear_all();
		tax.clear_all();
		saleRecord.clear_all();
		saleanddpt.clear_all();
		bill.clear_all();
		cashier.clear_all();
		contact.clear_all();
		return true;
	}
	public boolean clearnAllArea() {
		area.clear_all();
		return true;
	}
	public boolean clearnAllMenutype() {
		menuType.clear_all();
		return true;
	}
	public boolean clearnAllDesk() {
		desk.clear_all();
		return true;
	}
	public boolean clearnAllMenu() {
		menu.clear_all();
		return true;
	}
	public boolean clearnAllTax() {
		tax.clear_all();
		return true;
	}
	public boolean clearnAllSaleRecord() {
		saleRecord.clear_all();
		return true;
	}
	public boolean clearnAllSaleanddpt(){
		saleanddpt.clear_all();
		return true;
	}
	public boolean clearnAllBill() {
		bill.clear_all();
		return true;
	}
	public boolean clearnAllCashier() {
		cashier.clear_all();
		return true;
	}
	public boolean clearnAllContact() {
		contact.clear_all();
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
	
	public boolean saveDesk(d_Desk param) {
		desk.save(param);;
		return true;
	}
	
	public boolean saveMenu(d_Product param) {
		menu.save(param);
		return true;
	}

	public boolean saveSaleRecords(d_Sale param) {
		saleRecord.saveInit(param);
		return true  ;
	}
	
	public boolean saveSaleandpdt(d_Saleandpdt param){
		saleanddpt.saveInit(param);
		return true;
		
	}
	public boolean saveBill(d_Bill param) {
		bill.saveInit(param);
		return true  ;
	}
	
	public boolean saveCashier(d_Cashier param) {
		cashier.saveInit(param);
		return true  ;
	}
	
	public boolean saveContact(d_Contact param) {
		contact.save(param);
		return true  ;
	}
	
}