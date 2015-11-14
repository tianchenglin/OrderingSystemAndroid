package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import server.phone.dao.StaffDAO;
import server.phone.dao.TestDAO;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Staff;

public class TestServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public TestServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}

	public void doGet()	throws  IOException {
		response.setContentType("text/html;charset=UTF-8");
		TestDAO tDao;
		
		JSONObject jObject = new JSONObject();
		String result = "";
		try {
			tDao = new TestDAO();
			result = tDao.getTest();
			jObject.put("ret", result);
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
