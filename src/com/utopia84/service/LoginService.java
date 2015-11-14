package com.utopia84.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.utopia84.dao.StaffDAO;
import com.utopia84.model.Staff;

@Component(value="loginService")
public class LoginService {
	@Resource(name="StaffDAO")
	private StaffDAO staffDao;
	
	public Staff validate(String userName, String password) {
		return staffDao.validate(userName, password);
	}
	public StaffDAO getStaffDao() {
		return staffDao;
	}
	public void setStaffDao(StaffDAO staffDao) {
		this.staffDao = staffDao;
	}
}
