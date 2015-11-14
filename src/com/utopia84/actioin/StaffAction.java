package com.utopia84.actioin;

 
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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


public class StaffAction extends ActionSupport {
	
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
	public StaffAction() {
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
	public void GetAllStaff() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		try {
			result = jfdb.GetAllStaff();
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	/**
	 * 根据ID得到所有员工的详细信息
	 */
	public void GetStaffById() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		String id=request.getParameter("id");
		try {
			result = jfdb.GetStaffById(id);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	//修改员工信息
	public void UpdateStaffMessage() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		Staff staff=new Staff();
		String result="";
		try {
			staff.setId(Integer.parseInt(request.getParameter("id")));
			staff.setSName(new String(request.getParameter("SName").getBytes("iso-8859-1"),"utf-8"));
			staff.setSSex(Integer.parseInt(request.getParameter("SSex")));
			staff.setSPhone(request.getParameter("SPhone"));
			staff.setSAddr(new String(request.getParameter("SAddr").getBytes("iso-8859-1"),"utf-8"));
			staff.setSAccount(request.getParameter("SAccount"));
			staff.setSType(Integer.parseInt(request.getParameter("SType")));
			staff.setSIdent(request.getParameter("SIdent"));
			try {
				result = jfdb.UpdateStaffMessage(staff);
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
	
	//删除员工信息DeleteStaffById
	public void DeleteStaffById() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		String SAccount=request.getParameter("SAccount");
		try {
			result = jfdb.DeleteStaffById(SAccount);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}
		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	//添加员工信息
	public void AddStaffMessage() {
		Staff staff=new Staff();
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result="";
		try {
			staff.setSAccount(request.getParameter("SAccount"));
			staff.setSName(new String(request.getParameter("SName").getBytes("iso-8859-1"),"utf-8"));
			staff.setSPhone(request.getParameter("SPhone"));
			staff.setSSex(Integer.parseInt(request.getParameter("SSex")));
			staff.setSAddr(new String(request.getParameter("SAddr").getBytes("iso-8859-1"),"utf-8"));
			staff.setSType(Integer.parseInt(request.getParameter("SType")));
			staff.setSIdent(request.getParameter("SIdent"));
			
			try {
				result = jfdb.AddStaffMessage(staff);
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

