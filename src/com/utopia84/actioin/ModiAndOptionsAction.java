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
import com.utopia84.model.Options;
import com.utopia84.service.ItemAndTagService;
import com.utopia84.service.OptionsService;

public class ModiAndOptionsAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public ItemAndTagService itemAndTagService;
	public OptionsService optionsService;
	private PrintWriter out;

	/**
	 * 构造函数，获取页面参数
	 */
	public ModiAndOptionsAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	public void saveOptions() {
		int ModiId = Integer.parseInt(request.getParameter("modiid"));
		String optionName = request.getParameter("optionName");
		float price = Float.parseFloat(request.getParameter("price"));
		Options options = new Options(optionName,price,ModiId);
		try {
			optionsService.save(options);
		} catch (Exception e) {
			System.out.println("error");
		}
		initOptions();
	}

	public void subOption() {
		int OptId = Integer.parseInt(request.getParameter("optid"));
		try {
			optionsService.delete(OptId);
			initOptions();
		} catch (Exception e) {
			System.out.println("error");
		}
	}
	
	public void initOptions() {
		String ModiId = request.getParameter("modiid");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getModiAndOptions(ModiId);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}

	public ItemAndTagService getItemAndTagService() {
		return itemAndTagService;
	}

	@Resource(name = "ItemAndTagService")
	public void setItemAndTagService(ItemAndTagService itemAndTagService) {
		this.itemAndTagService = itemAndTagService;
	}

	public OptionsService getOptionsService() {
		return optionsService;
	}

	@Resource(name = "OptionsService")
	public void setOptionsService(OptionsService optionsService) {
		this.optionsService = optionsService;
	}
}
