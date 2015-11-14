package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.DiscountsDAO;
import com.utopia84.dao.TaxesDAO;
import com.utopia84.model.Discounts;
import com.utopia84.model.Tags;
import com.utopia84.model.Taxes;

@Component(value="taxesService")
public class TaxesService {
	@Resource(name="TaxesDAO")
	private TaxesDAO taxesDao;

	public void taxesAdd(int id ,Taxes taxes) {
		//如果id为0，则代表数据库中没有此记录
		if(id==0){//
			taxesDao.save(taxes);
		}else{
			taxes.setId(id);
			taxesDao.update(taxes);
		}
		
	}
	public List<Taxes> getAllTaxes() {
		return taxesDao.findAll();
	}
	
	public Taxes taxesDelete(int id) {
		Taxes taxes = taxesDao.findById(id);
		taxesDao.delete(taxes);
		return taxes;
	}

	public boolean taxesUpdate(Taxes taxes) {
		return taxesDao.update(taxes);
	}
	
	
	public TaxesDAO getTaxesDao() {
		return taxesDao;
	}
	public void setTaxesDao(TaxesDAO taxesDao) {
		this.taxesDao = taxesDao;
	}
	public void update(int taxesid, int length) {
		Taxes taxes = taxesDao.findById(taxesid);
		taxes.setInclude(length);
		taxesDao.update(taxes);
	}
}
