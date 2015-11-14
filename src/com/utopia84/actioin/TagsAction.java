package com.utopia84.actioin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.model.Product;
import com.utopia84.model.Tags;
import com.utopia84.service.LibraryService;
import com.utopia84.service.TagsService;

public class TagsAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public TagsService tagsService;
	public LibraryService libraryService;
	private Tags tag;
	/**
	 * 构造函数，获取页面参数
	 */
	public TagsAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		application = session.getServletContext();
		application.setAttribute("start!", "start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text");
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	public String addTags() {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		tag = new Tags(name);
		tagsService.tagsAdd(Integer.valueOf(id),tag);
		return "AddSuccess";
	}

	/**
	 * get 首页数据
	 */
	@Override
	public String execute() {
		try {
			return InitPage();
		} catch (Exception e) {
			e.printStackTrace();
		}// 初始化数据
		return "empty";
	}

	public String InitPage() throws Exception {
		List<Tags> tagsList =  tagsService.getAllTags();
		List<Product> productList =  libraryService.getAllProduct();
		
		request.setAttribute("tagsList", tagsList);
		request.setAttribute("productList", productList);
		return "InitPage";
	}
	public void getassign(){
		OutputStream os ;
		try {
			os = response.getOutputStream();
			os.write("error,id,id".getBytes());
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * delete
	 * 
	 * @return
	 */
	public String tagsDelete() {
		int id =Integer.parseInt(request.getParameter("id"));
		tagsService.tagsDelete(id);
		return "deleteSuccess";
	}

	public TagsService getTagsService() {
		return tagsService;
	}
	@Resource(name = "tagsService")
	public void setTagsService(TagsService tagsService) {
		this.tagsService = tagsService;
	}

	public LibraryService getLibraryService() {
		return libraryService;
	}
	@Resource(name = "LibraryService")
	public void setLibraryService(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	public Tags getTags() {
		return tag;
	}

	public void setTags(Tags tag) {
		this.tag = tag;
	}
}
