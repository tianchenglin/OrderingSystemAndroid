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
import com.utopia84.model.Itemandtags;
import com.utopia84.model.Itemandtax;
import com.utopia84.service.ItemAndTaxService;
import com.utopia84.service.TaxesService;

public class ItemAndTaxAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public ItemAndTaxService itemAndTaxService;
	public TaxesService taxesService;
	private PrintWriter out;
	/**
	 * 构造函数，获取页面参数
	 */
	public ItemAndTaxAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	public String add() {
		int taxesid = Integer.parseInt(request.getParameter("taxesid"));
		String[] val = request.getParameter("val").split(",");
		try {
			itemAndTaxService.delete(taxesid);
			for (int i = 1; i < val.length; i++) {
				itemAndTaxService.save(new Itemandtax(
						Integer.parseInt(val[i]), taxesid));
			}
			taxesService.update(taxesid,val.length-1);
		} catch (Exception e) {
			System.out.println("error");
		}
		return "Success";
	}

	public void list() {
		String taxesid = request.getParameter("taxesid");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getIiteandTaxes(taxesid);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}
	
	public void addItemandtax(){
		String itemId = request.getParameter("itemId");
		String taxId = request.getParameter("taxId");
		Itemandtax iat = new Itemandtax(Integer.parseInt(itemId), Integer.parseInt(taxId));
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			itemAndTaxService.save(iat);
			result = jfdb.getIAT(itemId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public void subIAT(){
		String itemId = request.getParameter("itemId");
		String Id = request.getParameter("Id");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			itemAndTaxService.delete(Integer.parseInt(Id));
			result = jfdb.getIAT(itemId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public void initiat(){
		String itemId = request.getParameter("itemId");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getIAT(itemId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback("+result+")");
		out.flush();
		out.close();
	}
	
	public ItemAndTaxService getItemAndTaxService() {
		return itemAndTaxService;
	}
	@Resource(name = "ItemAndTaxService")
	public void setItemAndTaxService(ItemAndTaxService itemAndTaxService) {
		this.itemAndTaxService = itemAndTaxService;
	}
	
	
}
