package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Menutype;

import net.sf.json.JSONObject;
import server.phone.dao.MenuDAO;

public class GetMenuTypeServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public GetMenuTypeServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doGet()	throws  IOException {
		response.setContentType("text/html;charset=UTF-8");
		MenuDAO md;
		List<Menutype> mts = null;
		JSONObject jObject = new JSONObject();
		JSONObject jData = new JSONObject();
		try {
			md = new MenuDAO();
			mts = md.getMenutype();
			jData.put("menuTypes", mts);
			
			jObject.put("ret", "success");
			jObject.put("data", jData);
			
		} catch (Exception e) {
			jObject.put("ret", "error");
			jObject.put("data", "");
		}
		
		PrintWriter out = response.getWriter();
		System.out.println(jObject.toString());
		out.println(jObject);
		out.flush();
		out.close();

	}

}
