package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Staff;

/**
 * A data access object (DAO) providing persistence and search support for Staff
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.utopia84.model.Staff
 * @author MyEclipse Persistence Tools
 */
@Component(value = "StaffDAO")
public class StaffDAO {

	private static final Logger log = LoggerFactory.getLogger(StaffDAO.class);
	private HibernateTemplate hibernateTemplate;
	// property constants
	public static final String _SACCOUNT = "SAccount";
	public static final String _SPWD = "SPwd";
	public static final String _SNAME = "SName";
	public static final String _SSEX = "SSex";
	public static final String _SIDENT = "SIdent";
	public static final String _SADDR = "SAddr";
	public static final String _SPHONE = "SPhone";
	public static final String _STYPE = "SType";
	public static final String DELMARK = "delmark";
	public static final String _SONLINE = "SOnline";

	public void save(Staff transientInstance) {
		log.debug("saving Staff instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Staff persistentInstance) {
		log.debug("deleting Staff instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Staff findById(java.lang.Integer id) {
		log.debug("getting Staff instance with id: " + id);
		try {
			Staff instance = (Staff) hibernateTemplate.get(
					"com.utopia84.model.Staff", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Staff instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Staff as model where model."
					+ propertyName + "= ?";
			return hibernateTemplate.find(queryString,value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySAccount(Object SAccount) {
		return findByProperty(_SACCOUNT, SAccount);
	}

	public List findBySPwd(Object SPwd) {
		return findByProperty(_SPWD, SPwd);
	}

	public List findBySName(Object SName) {
		return findByProperty(_SNAME, SName);
	}

	public List findBySSex(Object SSex) {
		return findByProperty(_SSEX, SSex);
	}

	public List findBySIdent(Object SIdent) {
		return findByProperty(_SIDENT, SIdent);
	}

	public List findBySAddr(Object SAddr) {
		return findByProperty(_SADDR, SAddr);
	}

	public List findBySPhone(Object SPhone) {
		return findByProperty(_SPHONE, SPhone);
	}

	public List findBySType(Object SType) {
		return findByProperty(_STYPE, SType);
	}

	public List findByDelmark(Object delmark) {
		return findByProperty(DELMARK, delmark);
	}

	public List findBySOnline(Object SOnline) {
		return findByProperty(_SONLINE, SOnline);
	}

	public List findAll() {
		log.debug("finding all Staff instances");
		try {
			String queryString = "from Staff";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Staff merge(Staff detachedInstance) {
		log.debug("merging Staff instance");
		try {
			Staff result = (Staff) hibernateTemplate.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Staff instance) {
		log.debug("attaching dirty Staff instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Staff instance) {
		log.debug("attaching clean Staff instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public Staff validate(String admin_account, String password) {
		@SuppressWarnings("unchecked")
		List<Staff> staffs = (List<Staff>) hibernateTemplate
				.find("from Staff where SAccount = '" + admin_account + "'");
		if (!staffs.isEmpty()) {
			Staff staff = staffs.get(0);
			if (staff.getSPwd().equals(password)) {
				return staff;
			}
		}
		return null;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}