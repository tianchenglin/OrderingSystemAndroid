package com.utopia84.actioin;

 
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Staff;
import com.utopia84.service.LoginService;


public class LoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	private LoginService managerService;
	
	private Staff staff;
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	/**
	 * 构造函数
	 * */
	public LoginAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	/**
	 * 登录请求
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkLogin() throws Exception {
		staff = managerService.validate(staff.getSAccount(),staff.getSPwd());
		if (staff!=null)
		{
			// 设置Cookie
			Cookie cookieu=new Cookie("username", staff.getSAccount()); 
			System.out.println("cookie:" + cookieu.getValue());
			cookieu.setMaxAge(24*60*60*7);//7天有效 
			response.addCookie(cookieu); 
			Cookie cookiep=new Cookie("password", staff.getSPwd()); 
			System.out.println("cookie:" + cookiep.getName());
			cookiep.setMaxAge(24*60*60*7);//7天有效 
			response.addCookie(cookiep); 
			return "loginSuccess";// 成功登录
		} else {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie c : cookies) {
					if (c.getName().equals("username") || c.getName().equals("password")) {
						c.setMaxAge(0);
						response.addCookie(c);
					}
				}
			}
			return "loginFailed";// 登录失败
		}
		
	}

	public LoginService getManagerService() {
		return managerService;
	}

	@Resource(name = "loginService")
	public void setManagerService(LoginService managerService) {
		this.managerService = managerService;
	}
}
