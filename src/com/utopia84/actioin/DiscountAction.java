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
import com.utopia84.service.DiscountService;

public class DiscountAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public DiscountService discountService;
	private Discounts discount;
	/**
	 * 构造函数，获取页面参数
	 */
	public DiscountAction() {
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
	public String addDiscount() {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String count = request.getParameter("count");
		discount = new Discounts(name,Float.valueOf(count),1);
		discountService.discountAdd(Integer.valueOf(id),discount);
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
		List<Discounts> discountsList =  discountService.getAllDiscount();
		for(int i = 0 ; i< discountsList.size(); i++){
			System.out.println(discountsList.get(i).getDiscountName());
		}
		request.setAttribute("discountsList", discountsList);
		return "InitPage";
	}
	/**
	 * delete
	 * 
	 * @return
	 */
	public String discountDelete() {
		int id =Integer.parseInt(request.getParameter("id"));
		discountService.discountDelete(id);
		return "deleteSuccess";
	}

	public DiscountService getDiscountService() {
		return discountService;
	}
	@Resource(name = "discountService")
	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public Discounts getDiscount() {
		return discount;
	}

	public void setDiscount(Discounts discount) {
		this.discount = discount;
	}
}
