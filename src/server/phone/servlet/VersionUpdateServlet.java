package server.phone.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Context;
import com.utopia84.utils.AnalysisApk;

public class VersionUpdateServlet extends ActionSupport {
	private static final long serialVersionUID = -7811568044252827351L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public VersionUpdateServlet(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	public void doGet()	throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		JSONObject jObject = new JSONObject();
		String path =request.getSession().getServletContext().getRealPath("/")+"//download//Guloop.apk";
		String[] message = AnalysisApk.unZip(path, "/");
		
		try {
			jObject.put("ret", "success");
			jObject.put("versionName", "2.0");
			jObject.put("versionCode", "2");
		
		} catch (Exception e) {
			jObject.put("ret", "error");
		}
		
		System.out.println(jObject.toString());
		PrintWriter out = response.getWriter();
		out.println(jObject);
		out.flush();
		out.close();

	}
}
