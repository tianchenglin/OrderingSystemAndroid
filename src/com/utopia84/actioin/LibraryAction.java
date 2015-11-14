package com.utopia84.actioin;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.utopia84.dbhelper.OpDB;
import com.utopia84.model.Modifier;
import com.utopia84.model.Product;
import com.utopia84.model.Taxes;
import com.utopia84.service.LibraryService;
import com.utopia84.service.ModifierService;
import com.utopia84.service.TaxesService;

public class LibraryAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;
	public LibraryService libraryService;
	public ModifierService modifierService;
	public TaxesService taxesService;
	private Product product;
	public static TreeMap<Integer, String> categoriesMap;
	
	/**
	 * 构造函数，获取页面参数
	 */
	public LibraryAction() {
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
	public String crateLibrary() {
		product = new Product();
		product.setPdtName("New Library");
		product.setPdtGg("#963E3E");
		product.setPdtSalePrice1("0.00");
		libraryService.Create(product);
		return "CrateSuccess";
	}
	
	/**
	 * 添加
	 * 
	 * @return
	 */
	public String addLibrary() {
		String itemId = request.getParameter("itemId");
		String menuName = request.getParameter("menuName");
		String category = request.getParameter("type");
		int menuType = 0 ;
		Set<Integer> keys = categoriesMap.keySet();
		for(Integer key: keys){
			if(categoriesMap.get(key).equals(category)){
				menuType = key;
				break;
			}
		}
		
		product = new Product();
		product.setPdtName(menuName);
		product.setPdtGg("#963E3E");
		product.setTypeId(menuType);
		product.setPdtId(Integer.parseInt(itemId));
		libraryService.update(product);
		return "CrateSuccess";
	}

	/**
	 * 获得对菜谱分类列表
	 */
	static {
		OpDB myOp = new OpDB();
		/* 菜单分类 */
		String sql = "select * from Menutype order by typeId";
		categoriesMap = myOp.OpGetListBox(sql, null);
		if (categoriesMap == null)
			categoriesMap = new TreeMap<Integer, String>();
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
		List<Product> productList =  libraryService.getAllProduct();
		List<Modifier> modifierList = modifierService.getAllModifier();
		List<Taxes> taxesList = taxesService.getAllTaxes();
		
		request.setAttribute("productList", productList);
		request.setAttribute("modifierList", modifierList);
		request.setAttribute("taxesList", taxesList);
		session.putValue("categoriesMap", categoriesMap);
		return "InitPage";
	}
	
	@SuppressWarnings("deprecation")
	public String selectOfCategory() throws Exception {
		String category = request.getParameter("category");
		int id = 0 ;
		Set<Integer> keys = categoriesMap.keySet();
		for(Integer key: keys){
			if(categoriesMap.get(key).equals(category)){
				id = key;
				break;
			}
		}
		
		List<Product> productList =  libraryService.findByCategory(id);
		List<Modifier> modifierList = modifierService.getAllModifier();
		List<Taxes> taxesList = taxesService.getAllTaxes();
		
		request.setAttribute("productList", productList);
		request.setAttribute("modifierList", modifierList);
		request.setAttribute("taxesList", taxesList);
		session.putValue("categoriesMap", categoriesMap);
		session.putValue("category", category);
		return "InitPage";
	}
	/**
	 * delete
	 * 
	 * @return
	 */
	public String discountDelete() {
		int id =Integer.parseInt(request.getParameter("id"));
		libraryService.productDelete(id);
		return "deleteSuccess";
	}

	public LibraryService getLibraryService() {
		return libraryService;
	}
	@Resource(name = "LibraryService")
	public void setLibraryService(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	public ModifierService getModifierService() {
		return modifierService;
	}
	@Resource(name = "ModifierService")
	public void setModifierService(ModifierService modifierService) {
		this.modifierService = modifierService;
	}

	public TaxesService getTaxesService() {
		return taxesService;
	}
	@Resource(name = "taxesService")
	public void setTaxesService(TaxesService taxesService) {
		this.taxesService = taxesService;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
