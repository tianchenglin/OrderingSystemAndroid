package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import server.phone.dao.BillDAO;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Bill;

public class GetBillServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public GetBillServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	
	 
	public void doGet() throws IOException {

		BillDAO bd;
		List<Bill> bds = null; // 从数据库读取订单记录
		JSONObject jObject = new JSONObject();
		JSONObject jData = new JSONObject();

		try {
			bd = new BillDAO();
			bds = bd.getBill();
			jData.put("Bills", bds);
			jObject.put("ret", "success");
			jObject.put("data", jData);
		} catch (ClassNotFoundException e) {
			jObject.put("ret", "error");
			jObject.put("data", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		System.out.println(jObject.toString());
		out.println(jObject);
		out.flush();
		out.close();

	}

}
