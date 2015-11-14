package com.utopia84.actioin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Discounts;
import com.utopia84.model.Modifier;
import com.utopia84.model.Product;
import com.utopia84.service.DiscountService;
import com.utopia84.service.ItemAndModiService;
import com.utopia84.service.LibraryService;
import com.utopia84.service.ModifierService;

public class ModifierAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public ModifierService modifierService;
	private Modifier modifier;
	public LibraryService libraryService;
	/**
	 * 构造函数，获取页面参数
	 */
	public ModifierAction() {
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
	public String addModifier() {
		String id = request.getParameter("id");
		String modiName = request.getParameter("nameinput");
		String choiceType = request.getParameter("choiceType");
		modifier = new Modifier(modiName,choiceType);
		modifierService.modifierAdd(Integer.valueOf(id),modifier);
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
		List<Modifier> modifierList =  modifierService.getAllModifier();
		List<Product> productList =  libraryService.getAllProduct();
		for(int i = 0 ; i< modifierList.size(); i++){
			System.out.println(modifierList.get(i).getModiName());
		}
		request.setAttribute("modifierList", modifierList);
		request.setAttribute("productList", productList);
		return "InitPage";
	}
	/**
	 * delete
	 * 
	 * @return
	 */
	public String modifierDelete() {
		int id =Integer.parseInt(request.getParameter("id"));
		modifierService.modifierDelete(id);
		return "deleteSuccess";
	}

	public ModifierService getmodifierService() {
		return modifierService;
	}
	@Resource(name = "ModifierService")
	public void setModifierService(ModifierService modifierService) {
		this.modifierService = modifierService;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	public LibraryService getLibraryService() {
		return libraryService;
	}
	@Resource(name = "LibraryService")
	public void setLibraryService(LibraryService libraryService) {
		this.libraryService = libraryService;
	}
	
}
