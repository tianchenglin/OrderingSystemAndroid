package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.dao.ItemandmodiDAO;
import com.utopia84.model.Itemandmodi;

@Component(value="ItemAndModiService")
public class ItemAndModiService {
	@Resource(name="ItemandmodiDAO")
	private ItemandmodiDAO itemandmodiDao;

	public void save(Itemandmodi itemandmodi) {
		itemandmodiDao.save(itemandmodi);
	}
	public List<Itemandmodi> getAllDiscount() {
		return itemandmodiDao.findAll();
	}
	
	public void delete(int id) {
		itemandmodiDao.delete(id);
	}

	public ItemandmodiDAO getDiscountDao() {
		return itemandmodiDao;
	}
	public void setDiscountDao(ItemandmodiDAO itemandmodiDao) {
		this.itemandmodiDao = itemandmodiDao;
	}
}
