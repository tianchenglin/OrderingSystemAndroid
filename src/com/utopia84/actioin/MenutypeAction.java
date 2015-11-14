package com.utopia84.actioin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Menutype;
import com.utopia84.model.Product;
import com.utopia84.service.LibraryService;
import com.utopia84.service.MenutypeService;

public class MenutypeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public MenutypeService menutypeService;
	private Menutype menutype;
	public LibraryService libraryService;
	/**
	 * 构造函数，获取页面参数
	 */
	public MenutypeAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	public String addMenutype() {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		menutype = new Menutype(name);
		menutypeService.menutypeAdd(id,menutype);
		return "AddSuccess";
	}

	/**
	 * get 首页数据
	 */
	@Override
	public String execute() {
		try {
			return InitPage();
		} catch (Exception e) {
			e.printStackTrace();
		}// 初始化数据
		return "empty";
	}

	public String InitPage() throws Exception {
		List<Menutype> menutypeList =  menutypeService.getAllMenutype();
		List<Product> productList =  libraryService.getAllProduct();
		
		request.setAttribute("menutypeList", menutypeList);
		request.setAttribute("productList", productList);
		return "InitPage";
	}
	public void getassign(){
		OutputStream os ;
		try {
			os = response.getOutputStream();
			os.write("error,id,id".getBytes());
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * delete
	 * 
	 * @return
	 */
	public String menutypeDelete() {
		System.out.println(request.getParameter("id"));
		int id =Integer.parseInt(request.getParameter("id"));
		menutypeService.menutypeDelete(request.getParameter("id"));
		return "deleteSuccess";
	}

	public MenutypeService getMenutypeService() {
		return menutypeService;
	}
	@Resource(name = "tagsService")
	public void setMenutypeService(MenutypeService menutypeService) {
		this.menutypeService = menutypeService;
	}

	public Menutype getMenutype() {
		return menutype;
	}

	public void setMenutype(Menutype menutype) {
		this.menutype = menutype;
	}

	public LibraryService getLibraryService() {
		return libraryService;
	}
	@Resource(name = "LibraryService")
	public void setLibraryService(LibraryService libraryService) {
		this.libraryService = libraryService;
	}
	
}
