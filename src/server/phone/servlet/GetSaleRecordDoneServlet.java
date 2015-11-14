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

public class GetSaleRecordDoneServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public GetSaleRecordDoneServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doGet()	throws  IOException {
		String deskName = request.getParameter("deskName");
		List<Salerecord> saleRecord = null;
		JSONObject jObject = new JSONObject();
		JSONObject jData = new JSONObject();
		try {
			SaleRecordDAO srd = new SaleRecordDAO();
			saleRecord = srd.getDoneSaleRecord(deskName);
			jData.put("srs", saleRecord);

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
