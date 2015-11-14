package com.utopia84.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.ItemandtagDAO;
import com.utopia84.model.Itemandtags;

@Component(value = "ItemAndTagService")
public class ItemAndTagService {
	@Resource(name = "ItemandtagDAO")
	private ItemandtagDAO itemandtagDAO;

	public void save(Itemandtags Itemandtags) {
		itemandtagDAO.save(Itemandtags);
	}

	public void delete(int id) {
		itemandtagDAO.delete(id);
	}

	public ItemandtagDAO getItemandtagDAO() {
		return itemandtagDAO;
	}

	public void setItemandtagDAO(ItemandtagDAO itemandtagDAO) {
		this.itemandtagDAO = itemandtagDAO;
	}

}
