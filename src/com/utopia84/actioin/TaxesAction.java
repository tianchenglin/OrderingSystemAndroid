package com.utopia84.actioin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import com.utopia84.model.Product;
import com.utopia84.model.Taxes;

import com.utopia84.service.LibraryService;
import com.utopia84.service.TaxesService;

public class TaxesAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public TaxesService taxesService;
	public LibraryService libraryService;
	private Taxes taxes;
	/**
	 * 构造函数，获取页面参数
	 */
	public TaxesAction() {
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
	public String addTaxes() {
		String id = request.getParameter("id");
		String taxeName = request.getParameter("nameinput");
		String rate = request.getParameter("categoryinput");
		taxes = new Taxes(taxeName,Float.valueOf(rate));
		taxesService.taxesAdd(Integer.valueOf(id),taxes);
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
		List<Taxes> taxesList =  taxesService.getAllTaxes();
		List<Product> productList =  libraryService.getAllProduct();
		for(int i = 0 ; i< taxesList.size(); i++){
			System.out.println(taxesList.get(i).getTaxeName());
		}
		request.setAttribute("taxesList", taxesList);
		request.setAttribute("productList", productList);
		return "InitPage";
	}
	/**
	 * delete
	 * 
	 * @return
	 */
	public String taxesDelete() {
		int id =Integer.parseInt(request.getParameter("id"));
		taxesService.taxesDelete(id);
		return "deleteSuccess";
	}

	public TaxesService getTaxesService() {
		return taxesService;
	}
	@Resource(name = "taxesService")
	public void setTaxesService(TaxesService taxesService) {
		this.taxesService = taxesService;
	}

	public LibraryService getLibraryService() {
		return libraryService;
	}
	@Resource(name="LibraryService")
	public void setLibraryService(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	public Taxes getTaxes() {
		return taxes;
	}

	public void setTaxes(Taxes taxes) {
		this.taxes = taxes;
	}
}
