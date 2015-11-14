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
import server.phone.dao.DeskDAO;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Desk;

public class GetDeskServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public GetDeskServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doPost()	throws  IOException {

		response.setContentType("text/html;charset=UTF-8");
		String id = request.getParameter("id");
		String area = request.getParameter("area");

		DeskDAO dd;
		Desk desk = null;
		List<Desk> desks = null;
		JSONObject jObject = new JSONObject();
		JSONObject jData = new JSONObject();

		if (id == null || id.equals("")) {
			try {
				dd = new DeskDAO();
				desks = dd.getAllDesk(id, area);
				jData.put("Desks", desks);

				jObject.put("ret", "success");
				jObject.put("data", jData);

			} catch (Exception e) {
				jObject.put("ret1", "error");
				jObject.put("data", "");
			}

		} else {
			try {
				dd = new DeskDAO();
				desk = dd.getDesk(id);
				jData.put("Desk", desk);

				jObject.put("ret1", "success");
				jObject.put("data", jData);

			} catch (Exception e) {
				jObject.put("ret", "error");
				jObject.put("data", "");
			}
		}
		PrintWriter out = response.getWriter();
		System.out.println(jObject.toString());
		out.println(jObject);
		out.flush();
		out.close();

	}

}
