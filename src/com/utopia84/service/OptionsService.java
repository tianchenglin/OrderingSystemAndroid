package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.OptionsDAO;
import com.utopia84.model.Options;

@Component(value="OptionsService")
public class OptionsService {
	@Resource(name="OptionsDAO")
	private OptionsDAO optionsDAO;

	public void save(Options options) {
		optionsDAO.save(options);
	}
	public List<Options> getAllDiscount() {
		return optionsDAO.findAll();
	}
	
	public Options delete(int id) {
		Options options = optionsDAO.findById(id);
		optionsDAO.delete(options);
		return options;
	}
	
	public OptionsDAO getDiscountDao() {
		return optionsDAO;
	}
	public void setDiscountDao(OptionsDAO optionsDAO) {
		this.optionsDAO = optionsDAO;
	}
}
