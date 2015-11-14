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
import com.utopia84.model.Itemandmenutype;
import com.utopia84.service.ItemAndMenutypeService;
import com.utopia84.service.MenutypeService;

public class ItemAndMenutypeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public ItemAndMenutypeService itemAndMenutypeService;
	public MenutypeService menutypeService;
	private PrintWriter out;

	/**
	 * 构造函数，获取页面参数
	 */
	public ItemAndMenutypeAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	public String add() {
		int menutypeid = Integer.parseInt(request.getParameter("menutypeid"));
		System.out.println(menutypeid);
		String[] val = request.getParameter("val").split(",");
		try {
			itemAndMenutypeService.delete(menutypeid);
			for (int i = 1; i < val.length; i++) {
				itemAndMenutypeService.save(new Itemandmenutype(
						Integer.parseInt(val[i]), menutypeid));
			}
			menutypeService.update(request.getParameter("menutypeid"),val.length-1);
		} catch (Exception e) {
			System.out.println("error");
		}
		return "Success";
	}

	public void list() {
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getIiteandMenutype();
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}

	public ItemAndMenutypeService getItemAndMenutypeService() {
		return itemAndMenutypeService;
	}

	@Resource(name = "ItemAndMenutypeService")
	public void setItemAndTagService(ItemAndMenutypeService itemAndMenutypeService) {
		this.itemAndMenutypeService = itemAndMenutypeService;
	}

	public MenutypeService getMenutypeService() {
		return menutypeService;
	}

	@Resource(name = "menutypeService")
	public void setMenutypeService(MenutypeService menutypeService) {
		this.menutypeService = menutypeService;
	}
}
