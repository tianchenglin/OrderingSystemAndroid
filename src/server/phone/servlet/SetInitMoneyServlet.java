package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import server.phone.dao.CashierDAO;

public class SetInitMoneyServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SetInitMoneyServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	private boolean flag = false;

	public void doPost() throws  IOException {
		response.setContentType("text/html;charset=UTF-8");

		String initMoney = request.getParameter("initmoney");
		String createTime = request.getParameter("createtime");
		String userCode  = request.getParameter("usercode");
		String cashierId = request.getParameter("cashierid");
		String status = request.getParameter("status");
		
		CashierDAO cd;
		try {
			cd = new CashierDAO();
			flag = cd.saveInitMoney(initMoney,createTime,userCode,cashierId,status);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		JSONObject jObject = new JSONObject();
		try {
			if (flag)
				jObject.put("ret", "success");
		} catch (Exception e) {
			jObject.put("ret", "error");
		}

		PrintWriter out = response.getWriter();
		out.println(jObject);
		out.flush();
		out.close();

	}
}
