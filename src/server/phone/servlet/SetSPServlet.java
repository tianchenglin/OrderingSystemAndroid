package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import server.phone.dao.DeskDAO;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Desk;
import com.utopia84.utils.MyDate;

public class SetSPServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SetSPServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		String spdesc = request.getParameter("spdesc");
		String spsize = request.getParameter("spsize");
		
		
		DeskDAO dd;
		Desk desk = new Desk();
		
		System.out.println(desk.toString());
		JSONObject jObject = new JSONObject();
		try {
			dd = new DeskDAO();
			if(dd.setDesk(desk))
				jObject.put("ret", "success");
			else
				jObject.put("ret", "failer");
		} catch (Exception e) {
			jObject.put("ret", "error");
		}

		PrintWriter out = response.getWriter();
		System.out.println(jObject.toString());
		out.println(jObject);
		out.flush();
		out.close();

	}

}
