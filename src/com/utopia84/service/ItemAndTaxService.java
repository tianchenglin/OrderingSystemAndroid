package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.ItemandtaxDAO;
import com.utopia84.model.Itemandtax;

@Component(value="ItemAndTaxService")
public class ItemAndTaxService {
	@Resource(name="ItemandtaxDAO")
	private ItemandtaxDAO ttemandtaxDAO;

	public void save(Itemandtax itemandtax) {
		ttemandtaxDAO.save(itemandtax);
	}
	public List<Itemandtax> getAllDiscount() {
		return ttemandtaxDAO.findAll();
	}
	
	public void delete(int id) {
		ttemandtaxDAO.delete(id);
	}
	
	public ItemandtaxDAO getDiscountDao() {
		return ttemandtaxDAO;
	}
	public void setDiscountDao(ItemandtaxDAO ttemandtaxDAO) {
		this.ttemandtaxDAO = ttemandtaxDAO;
	}
}
