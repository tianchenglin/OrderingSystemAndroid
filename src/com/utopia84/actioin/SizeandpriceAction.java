package com.utopia84.actioin;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import server.phone.dbhelper.JsonFromDataBase;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Sizeandprice;
import com.utopia84.service.SizeandpriceService;

public class SizeandpriceAction extends ActionSupport {

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
	public SizeandpriceAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	public void addsp(){
		String id = request.getParameter("id");
		String spdesc = request.getParameter("spdesc");
		String spsize = request.getParameter("spsize");
		Sizeandprice sp = new Sizeandprice(spdesc,Float.parseFloat(spsize),Integer.parseInt(id));
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			System.out.println(sp.toString());
			spService.save(0,sp);
			result = jfdb.getSP(id);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public void subsp(){
		String spid = request.getParameter("spid");
		String itemid = request.getParameter("itemid");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			spService.spDelete(Integer.parseInt(spid));
			result = jfdb.getSP(itemid);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public void initsp(){
		String id = request.getParameter("id");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getSP(id);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public SizeandpriceService getSpService() {
		return spService;
	}
	@Resource(name = "SizeandpriceService")
	public void setSpService(SizeandpriceService spService) {
		this.spService = spService;
	}
}
