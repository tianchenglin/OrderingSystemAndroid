package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import server.phone.dao.StaffDAO;

import com.utopia84.model.Staff;

public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = -7811568044252827351L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");
		
		String s_account = req.getParameter("account");
		String s_pwd = req.getParameter("password");
		String s_name = req.getParameter("name");
		String s_type = req.getParameter("type");
		
		// new String(content.getBytes("ISO8859_1"), "utf-8").trim();
		
		StaffDAO sdao;
		Staff staff = new Staff(s_account, s_name,
			"", Integer.parseInt(s_type));
		JSONObject jObject = new JSONObject();
		try {
			sdao = new StaffDAO();
			if (sdao.addUserInfo(staff)) {
				jObject.put("ret", "success");
			} else {
				jObject.put("ret", "failer");
			}
		} catch (Exception e) {
			jObject.put("ret", "error");
		}
		System.out.println(jObject.toString());
		PrintWriter out = resp.getWriter();
		out.println(jObject);
		out.flush();
		out.close();

	
	}

	
}
