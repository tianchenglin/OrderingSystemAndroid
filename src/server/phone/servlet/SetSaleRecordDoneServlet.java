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

public class SetSaleRecordDoneServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SetSaleRecordDoneServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	private boolean flag = false;

	 
	public void doPost()	throws  IOException {
		String BILLID = request.getParameter("BILLID");
		String PdtCODE = request.getParameter("PdtCODE");
		String DeskName = request.getParameter("DeskName");
		String closeTime = request.getParameter("closeTime");

		try {
			SaleRecordDAO srd = new SaleRecordDAO();
			DeskDAO dd = new DeskDAO();
			flag = srd.updateSaleRecord(BILLID, PdtCODE, closeTime,"Done");
			if (flag) {
				flag = dd.updateDeskAdd(DeskName);
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
