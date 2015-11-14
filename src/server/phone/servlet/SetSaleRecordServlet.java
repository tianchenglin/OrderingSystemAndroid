package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import server.phone.dao.SaleRecordDAO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Salerecord;

public class SetSaleRecordServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SetSaleRecordServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}

	 
	public void doPost()	throws  IOException {
		String saleRecords = request.getParameter("saleRecords");
		List<Salerecord> records = new ArrayList<Salerecord>();
		Salerecord record;
		String status = "Sent";
		try {
			SaleRecordDAO srd = new SaleRecordDAO();
			JSONArray array = JSONArray.fromObject(saleRecords);
			for (int i = 0; i < array.size(); i++) {
				JSONObject rs = array.getJSONObject(i);
				System.out.println(rs.toString());
				if (srd.isDrink(rs.getString("PdtCODE"))) {
					status = "Done";
				} else {
					status = "Sent";
				}
				record = new Salerecord(rs.getInt("itemNo"),
						rs.getString("closeTime"), rs.getString("createTime"),
						rs.getString("deskName"),rs.getString("otherSpec"),
						rs.getString("OtherSpecNo1"), rs.getString("OtherSpecNo2"),
						rs.getString("status"),rs.getString("Waiter"),rs.getString("dept"),
						(float)(rs.getDouble("subTotal")),(float)(rs.getDouble("tipTotal")),
						(float)(rs.getDouble("total")),(float)(rs.getDouble("initTotal")),
						(float)(rs.getDouble("rebateTotal")),(float)(rs.getDouble("taxTotal")));
						

				records.add(record);
			}

			srd.saveSaleRecord(records);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject jObject = new JSONObject();
		try {
			jObject.put("ret", "success");
		} catch (Exception e) {
			jObject.put("ret", "error");
		}
		jObject.put("ret", "success");

		PrintWriter out = response.getWriter();
		out.println(jObject);
		out.flush();
		out.close();
	}

}
