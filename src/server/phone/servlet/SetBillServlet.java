package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

public class SetBillServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SetBillServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doPost()	throws  IOException {
		String billid = request.getParameter("billid");
		String waiter = request.getParameter("waiter");
		String subtotal = request.getParameter("subtotal");
		String tax = request.getParameter("tax");
		String total = request.getParameter("total");
		String createtime = request.getParameter("createtime");
		String distant = request.getParameter("distant");
		String tip = request.getParameter("tip");
		
		List<Bill> bs = new ArrayList<Bill>();
		
		Bill b = new Bill();
		b.setBillId(billid);
		b.setCreateTime(createtime);
		b.setDistant(Float.valueOf(distant));
		b.setSubtotal(Float.valueOf(subtotal));
		b.setTax(Float.valueOf(tax));
		b.setTip(Float.valueOf(tip));
		b.setWaiter(waiter);
		b.setTotal(Float.valueOf(total));
		bs.add(b);
		int flag = 0;
		try {
			BillDAO bill = new BillDAO();
			flag = bill.saveBill(bs);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONObject jObject = new JSONObject();
		try {
			if (flag==1)
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
