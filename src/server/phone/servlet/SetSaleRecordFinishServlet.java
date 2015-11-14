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
import server.phone.dao.DeskDAO;
import server.phone.dao.SaleRecordDAO;

public class SetSaleRecordFinishServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SetSaleRecordFinishServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	private boolean flag = false;

	 
	public void doPost()	throws  IOException {
		String billid = request.getParameter("billid");
		String pdtcode = request.getParameter("pdtcode");
		String deskname = request.getParameter("deskname");
		String closeTime = request.getParameter("closetime");

		try {
			SaleRecordDAO srd = new SaleRecordDAO();
			DeskDAO dd = new DeskDAO();
			flag = srd.updateSaleRecord(billid, pdtcode, closeTime,"Finish");
			if (flag) {
				flag = dd.updateDeskSub(deskname);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
