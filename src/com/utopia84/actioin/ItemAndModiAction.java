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
import com.utopia84.model.Itemandmodi;
import com.utopia84.model.Itemandtags;
import com.utopia84.service.ItemAndModiService;
import com.utopia84.service.ModifierService;

public class ItemAndModiAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public ItemAndModiService itemandmodiService;
	public ModifierService modiService;
	private PrintWriter out;
	/**
	 * 构造函数，获取页面参数
	 */
	public ItemAndModiAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}
	
	public String add() {
		int modiid = Integer.parseInt(request.getParameter("modiid"));
		String[] val = request.getParameter("val").split(",");
		try {
			itemandmodiService.delete(modiid);
			for (int i = 1; i < val.length; i++) {
				itemandmodiService.save(new Itemandmodi(
						Integer.parseInt(val[i]), modiid));
			}
		} catch (Exception e) {
			System.out.println("error");
		}
		return "Success";
	}

	public void list() {
		String modiid = request.getParameter("modiid");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getIiteandModi(modiid);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}

	public void addItemandmodi(){
		String itemId = request.getParameter("itemId");
		String modiId = request.getParameter("modiId");
		Itemandmodi iam = new Itemandmodi(Integer.parseInt(itemId), Integer.parseInt(modiId));
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			itemandmodiService.save(iam);
			result = jfdb.getIAM(itemId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public void subIAM(){
		String itemId = request.getParameter("itemId");
		String id = request.getParameter("id");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			itemandmodiService.delete(Integer.parseInt(id));
			result = jfdb.getIAM(itemId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public void initiam(){
		String itemId = request.getParameter("itemId");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getIAM(itemId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public ItemAndModiService getItemandmodiService() {
		return itemandmodiService;
	}
	@Resource(name = "ItemAndModiService")
	public void setItemandmodiService(ItemAndModiService itemandmodiService) {
		this.itemandmodiService = itemandmodiService;
	}
	
	
}
