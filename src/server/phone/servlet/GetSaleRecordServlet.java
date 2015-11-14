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
import com.utopia84.model.Salerecord;

import net.sf.json.JSONObject;
import server.phone.dao.SaleRecordDAO;

public class GetSaleRecordServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public GetSaleRecordServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doPost()	throws  IOException {
		String createTime = request.getParameter("createTime");
		System.out.println(createTime);
		SaleRecordDAO srd;
		List<Salerecord> saleRecord = null;
		JSONObject jObject = new JSONObject();
		JSONObject jData = new JSONObject();
		try {
			srd = new SaleRecordDAO();
			saleRecord = srd.getAllSaleRecord(createTime);
			jData.put("saleRecord", saleRecord);

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
