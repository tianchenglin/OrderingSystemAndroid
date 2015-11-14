package com.utopia84.actioin;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import server.phone.dbhelper.JsonFromDataBase;

import com.utopia84.model.Contact;
import com.utopia84.model.Staff;
import com.utopia84.service.SizeandpriceService;

public class ContactAction {
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
	public ContactAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}
	
	/**
	 * 得到所有员工的列表
	 */
	public void GetAllCustomers() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		try {
			result = jfdb.GetAllCustomers();
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/*
	 * 根据ID得到顾客的详细信息
	 */
	public void GetCustomersById() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		int id=Integer.parseInt(request.getParameter("id"));
		try {
			result = jfdb.GetCustomersById(id);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/*
	 * 修改顾客信息
	 */
	public void UpdateCustomersMessage() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		Contact customers=new Contact();
		String result="";
		try {
			customers.setId(Integer.parseInt(request.getParameter("id")));
			customers.setName(new String(request.getParameter("Name").getBytes("iso-8859-1"),"utf-8"));
			customers.setPhone(request.getParameter("Phone"));
			customers.setAdd_Street(request.getParameter("Add_Street"));
			customers.setAdd_Apt(request.getParameter("Add_Apt"));
			customers.setAdd_City(request.getParameter("setAdd_City"));
			
			try {
				result = jfdb.UpdateCustomersMessage(customers);
				out = response.getWriter();
			} catch (Exception e) {
				System.out.println("error");
			}
			out.println("success_jsonpCallback(" + result + ")");
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/*
	 * 删除顾客信息
	 */
	public void DeleteCustomersById() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		String id=request.getParameter("id");
		try {
			result = jfdb.DeleteCustomersById(id);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/*
	 * 添加顾客信息
	 */
	public void AddCustomersMessage() {
		Contact customers=new Contact();
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		try {
			customers.setName(new String(request.getParameter("Name").getBytes("iso-8859-1"),"utf-8"));
			customers.setPhone(request.getParameter("Phone"));
			customers.setAdd_Street(request.getParameter("Add_Street"));
			customers.setAdd_Apt(request.getParameter("Add_Apt"));
			customers.setAdd_City(request.getParameter("setAdd_City"));
			
			try {
				result = jfdb.AddCustomersMessage(customers);
				out = response.getWriter();
			} catch (Exception e) {
				System.out.println("error");
			}
			out.println("success_jsonpCallback(" + result + ")");
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
