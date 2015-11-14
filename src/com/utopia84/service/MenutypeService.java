package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.MenutypeDAO;
import com.utopia84.model.Menutype;
import com.utopia84.model.Tags;

@Component(value = "menutypeService")
public class MenutypeService {
	@Resource(name="MenutypeDAO")
	private MenutypeDAO menutypeDao;

	public void menutypeAdd(String typeId ,Menutype menutype) {
		//如果id为0，则代表数据库中没有此记录
		int id = Integer.parseInt(typeId);
		if(id==0){//
			menutypeDao.save(menutype);
		}else{
			menutype.setTypeId(typeId);
			menutypeDao.update(menutype);
		}
		
	}
	public List<Menutype> getAllMenutype() {
		return menutypeDao.findAll();
	}
	
	public Menutype menutypeDelete(String id) {
		Menutype menutype = menutypeDao.findById(id);
		menutypeDao.delete(menutype);
		return menutype;
	}

	public boolean menutypeUpdate(Menutype menutype) {
		return menutypeDao.update(menutype);
	}
	
	
	public MenutypeDAO getMenutypeDao() {
		return menutypeDao;
	}
	public void setMenutypeDao(MenutypeDAO menutypeDao) {
		this.menutypeDao = menutypeDao;
	}
	public void update(String menutypeid, int length) {
		Menutype menutype = menutypeDao.findById(menutypeid);
		menutype.setInclude(length);
		menutypeDao.update(menutype);
	}
}
