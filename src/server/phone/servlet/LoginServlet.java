package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import server.phone.dao.StaffDAO;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Staff;

public class LoginServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public LoginServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doGet()	throws  IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String s_account = request.getParameter("s_account");
		System.out.println(s_account);
		// new String(content.getBytes("ISO8859_1"), "utf-8").trim();
		
		StaffDAO sdao;
		Staff staff ;
		JSONObject jObject = new JSONObject();
		JSONObject jData = new JSONObject();
		String result = "";
		try {
			sdao = new StaffDAO();
			result = sdao.validate(s_account);
			if (result.equals("success")) {
				staff = sdao.getUserInfo(s_account);
				jObject.put("ret", "success");
				jData.put("staff", staff);
				jObject.put("data", jData);
			} else {
				jObject.put("ret", result);
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
