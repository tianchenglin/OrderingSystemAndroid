package com.utopia84.actioin;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import server.phone.dbhelper.JsonFromDataBase;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Itemandtags;
import com.utopia84.service.ItemAndTagService;
import com.utopia84.service.TagsService;

public class ItemAndTagAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public ItemAndTagService itemAndTagService;
	public TagsService tagsService;
	private PrintWriter out;

	/**
	 * 构造函数，获取页面参数
	 */
	public ItemAndTagAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	public String add() {
		int tagsid = Integer.parseInt(request.getParameter("tagsid"));
		String[] val = request.getParameter("val").split(",");
		try {
			itemAndTagService.delete(tagsid);
			for (int i = 1; i < val.length; i++) {
				itemAndTagService.save(new Itemandtags(
						Integer.parseInt(val[i]), tagsid));
			}
			tagsService.update(tagsid,val.length-1);
		} catch (Exception e) {
			System.out.println("error");
		}
		return "Success";
	}

	public void list() {
		String tagsid = request.getParameter("tagsid");
		JsonFromDataBase jfdb = new JsonFromDataBase();
		String result = "";
		try {
			result = jfdb.getIiteandTags(tagsid);
			out = response.getWriter();
		} catch (Exception e) {
			System.out.println("error");
		}

		out.println("success_jsonpCallback(" + result + ")");
		out.flush();
		out.close();
	}

	public ItemAndTagService getItemAndTagService() {
		return itemAndTagService;
	}

	@Resource(name = "ItemAndTagService")
	public void setItemAndTagService(ItemAndTagService itemAndTagService) {
		this.itemAndTagService = itemAndTagService;
	}

	public TagsService getTagsService() {
		return tagsService;
	}

	@Resource(name = "tagsService")
	public void setTagsService(TagsService tagsService) {
		this.tagsService = tagsService;
	}
}
