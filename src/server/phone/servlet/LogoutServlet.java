package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import server.phone.dao.StaffDAO;

public class LogoutServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public LogoutServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}

	public void doGet()	throws  IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String s_account = request.getParameter("s_account");
		// new String(content.getBytes("ISO8859_1"), "utf-8").trim();
		StaffDAO sdao;
		JSONObject jObject = new JSONObject();
		try {
			sdao = new StaffDAO();
			if (sdao.logout(s_account)) {
				jObject.put("ret", "success");
			} else {
				jObject.put("ret", "failure");
			}
		} catch (Exception e) {
			jObject.put("ret", "error");
		}
		System.out.println(jObject.toString());
		PrintWriter out = response.getWriter();
		out.println(jObject);
		out.flush();
		out.close();

	}
}
