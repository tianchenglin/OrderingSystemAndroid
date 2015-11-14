package com.utopia84.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.ProductDAO;
import com.utopia84.model.Product;

@Component(value = "LibraryService")
public class LibraryService {
	@Resource(name = "ProductDAO")
	private ProductDAO productDao;

	public void Create(Product product) {
			productDao.save(product);

	}

	public void update(Product product) {
		productDao.update(product);

}
	public List<Product> getAllProduct() {
		return productDao.findAll();
	}
	
	public List<Product> getAllNotSelectedProduct() {
		return productDao.findAllNotSelected();
	}	

	public Product productDelete(int id) {
		Product product = productDao.findById(id);
		productDao.delete(product);
		return product;
	}

	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProducttDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

	public List<Product> findByCategory(int category) {
		// TODO Auto-generated method stub
		return productDao.findByCategory(category);
	}
}
