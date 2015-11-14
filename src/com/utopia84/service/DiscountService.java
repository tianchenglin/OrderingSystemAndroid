package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.DiscountsDAO;
import com.utopia84.model.Discounts;

@Component(value="discountService")
public class DiscountService {
	@Resource(name="DiscountsDAO")
	private DiscountsDAO discountDao;

	public void discountAdd(int id ,Discounts discount) {
		//如果id为0，则代表数据库中没有此记录
		if(id==0){//
			discountDao.save(discount);
		}else{
			discount.setId(id);
			discountDao.update(discount);
		}
		
	}
	public List<Discounts> getAllDiscount() {
		return discountDao.findAll();
	}
	
	public Discounts discountDelete(int id) {
		Discounts discount = discountDao.findById(id);
		discountDao.delete(discount);
		return discount;
	}

	public boolean discountUpdate(Discounts discount) {
		return discountDao.update(discount);
	}
	
	
	public DiscountsDAO getDiscountDao() {
		return discountDao;
	}
	public void setDiscountDao(DiscountsDAO discountDao) {
		this.discountDao = discountDao;
	}
}
