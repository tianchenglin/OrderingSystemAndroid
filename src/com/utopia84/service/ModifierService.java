package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.ModifierDAO;
import com.utopia84.model.Modifier;
import com.utopia84.model.Tags;

@Component(value="ModifierService")
public class ModifierService {
	@Resource(name="ModifierDAO")
	private ModifierDAO modifierDao;

	public void modifierAdd(int id ,Modifier modifier) {
		//如果id为0，则代表数据库中没有此记录
		if(id==0){//
			modifierDao.save(modifier);
		}else{
			modifier.setId(id);
			modifierDao.update(modifier);
		}
		
	}
	public List<Modifier> getAllModifier() {
		return modifierDao.findAll();
	}
	
	public Modifier modifierDelete(int id) {
		Modifier modifier = modifierDao.findById(id);
		modifierDao.delete(modifier);
		return modifier;
	}

	
	
	public ModifierDAO getDiscountDao() {
		return modifierDao;
	}
	public void setDiscountDao(ModifierDAO modifierDao) {
		this.modifierDao = modifierDao;
	}
	public void update(int modiid, int length) {
		Modifier modi = modifierDao.findById(modiid);
		modi.setInclude(length);
		modifierDao.update(modi);
	}
}
