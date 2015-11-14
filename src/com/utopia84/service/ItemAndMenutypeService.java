package com.utopia84.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.ItemandmenutypeDAO;
import com.utopia84.model.Itemandmenutype;

@Component(value = "ItemAndMenutypeService")
public class ItemAndMenutypeService {
	@Resource(name = "ItemandmenutypeDAO")
	private ItemandmenutypeDAO itemandmenutypeDAO;

	public void save(Itemandmenutype Itemandmenutype) {
		itemandmenutypeDAO.save(Itemandmenutype);
	}

	public void delete(int id) {
		itemandmenutypeDAO.delete(id);
	}

	public ItemandmenutypeDAO getItemandmenutypeDAO() {
		return itemandmenutypeDAO;
	}

	public void setItemandmenutypeDAO(ItemandmenutypeDAO itemandmenutypeDAO) {
		this.itemandmenutypeDAO = itemandmenutypeDAO;
	}

}
