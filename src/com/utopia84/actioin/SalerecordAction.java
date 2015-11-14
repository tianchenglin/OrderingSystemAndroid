package com.utopia84.actioin;

 
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import server.phone.dbhelper.JsonFromDataBase;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Staff;
import com.utopia84.service.LoginService;
import com.utopia84.service.SizeandpriceService;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jxl.*;
import jxl.write.*;

public class SalerecordAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public SizeandpriceService spService;
	private PrintWriter out;
	/**
	 * 构造函数，获取页面参数
	 */
	public SalerecordAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}
	
	
	/**
	 * 根据条件得到交易记录
	 */	
	public void GetTransactionsBySql() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String sql;
		String result = "";
		sql=request.getParameter("sql");
		try {
			result = jfdb.GetTransactionsBySql(sql);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/**
	 * 得到消费菜单
	 */
	public void GetPdtByBillid(){
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String salerecordId=request.getParameter("salerecordId");
		String result = "";
		try {
			result = jfdb.GetPdtByBillid(salerecordId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/**
	 * 得到消费所有支付方式
	 */
	public void GetAllPayment() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.GetAllPayment();
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/**
	 * 得到消费所有隶属部门
	 */
	public void GetAllDept() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.GetAllDept();
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	
	/**
	 * 根据billid得到交易记录
	 */
	public void	GetTransactionByItemNo() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String itemNo=request.getParameter("itemNo");
		
		String [] itemArr= itemNo.split("@");
		String result = "";
		
		try {
			result = jfdb.ExportExcel(itemArr);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/**
	 *  得到最近七天的交易记录
	 */
	public void GetSevenTransactions(){
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";

		try {
			result = jfdb.GetSevenTransactions(getStatetime());
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/**
	 * 得到近七天的日期
	 */
	public String[] getStatetime(){	  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Calendar c = Calendar.getInstance();  
		String[] date=new String[7];
		c.add(Calendar.DATE, 0);
		java.util.Date m = c.getTime();
		String dateToday = sdf.format(m);
		date[0]=dateToday;
		for(int i=1;i<7;i++){
			c.add(Calendar.DATE, - 1);  
			java.util.Date monday = c.getTime();
			String preMonday = sdf.format(monday);
			date[i]=preMonday;
		}
		/*System.out.println(date[0]+"  "+date[1]+"   "+date[2]+"   "+date[3]);*/
		return date;
	}
	
	/**
	 * 得到刷卡和现金的比值
	 */
	public void GetBZ(){	  
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		
		try {
			result = jfdb.getBZ(getStatetime());
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
}
