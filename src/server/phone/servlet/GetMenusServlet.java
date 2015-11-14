package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import server.phone.dao.MenuDAO;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Menus;

public class GetMenusServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public GetMenusServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doGet()	throws  IOException {
		response.setContentType("text/html;charset=UTF-8");
		MenuDAO md;
		List<Menus> menus = null;
		JSONObject jObject = new JSONObject();
		JSONObject jData = new JSONObject();
		try {
			md = new MenuDAO();
			menus = md.getMenus();
			jData.put("menus", menus);

			jObject.put("ret", "success");
			jObject.put("data", jData);

		} catch (Exception e) {
			jObject.put("ret", "error");
			jObject.put("data", "");
		}jObject.put("ret", "success");

		PrintWriter out = response.getWriter();
		System.out.println(jObject.toString());
		out.println(jObject);
		out.flush();
		out.close();

	}

}
