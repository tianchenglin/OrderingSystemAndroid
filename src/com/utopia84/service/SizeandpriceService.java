package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.SizeandpriceDAO;
import com.utopia84.model.Sizeandprice;

@Component(value="SizeandpriceService")
public class SizeandpriceService {
	@Resource(name="SizeandpriceDAO")
	private SizeandpriceDAO spDao;

	public void save(int id ,Sizeandprice sp) {
		//如果id为0，则代表数据库中没有此记录
		if(id==0){//
			spDao.save(sp);
		}else{
			sp.setId(id);
			spDao.update(sp);
		}
		
	}
	public List<Sizeandprice> getAllDiscount() {
		return spDao.findAll();
	}
	
	public Sizeandprice spDelete(int id) {
		Sizeandprice discount = spDao.findById(id);
		spDao.delete(discount);
		return discount;
	}
	public SizeandpriceDAO getSpDao() {
		return spDao;
	}
	public void setSpDao(SizeandpriceDAO spDao) {
		this.spDao = spDao;
	}
	
}
