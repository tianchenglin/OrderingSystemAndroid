package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.utopia84.model.Staffsign;

/**
 	* A data access object (DAO) providing persistence and search support for Staffsign entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Staffsign
  * @author MyEclipse Persistence Tools 
 */

public class StaffsignDAO    {
	     private static final Logger log = LoggerFactory.getLogger(StaffsignDAO.class);
		//property constants
	public static final String _SACCOUNT = "SAccount";
	public static final String SIGN_TERM = "signTerm";
	public static final String SIGN_IP = "signIp";
	public static final String REMARK = "remark";
	public static final String DELMARK = "delmark";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
    public void save(Staffsign transientInstance) {
        log.debug("saving Staffsign instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Staffsign persistentInstance) {
        log.debug("deleting Staffsign instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Staffsign findById( java.lang.Integer id) {
        log.debug("getting Staffsign instance with id: " + id);
        try {
            Staffsign instance = (Staffsign) hibernateTemplate
                    .get("com.utopia84.model.Staffsign", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Staffsign instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Staffsign as model where model." 
         						+ propertyName + "= ?";
        
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findBySAccount(Object SAccount
	) {
		return findByProperty(_SACCOUNT, SAccount
		);
	}
	
	public List findBySignTerm(Object signTerm
	) {
		return findByProperty(SIGN_TERM, signTerm
		);
	}
	
	public List findBySignIp(Object signIp
	) {
		return findByProperty(SIGN_IP, signIp
		);
	}
	
	public List findByRemark(Object remark
	) {
		return findByProperty(REMARK, remark
		);
	}
	
	public List findByDelmark(Object delmark
	) {
		return findByProperty(DELMARK, delmark
		);
	}
	

	public List findAll() {
		log.debug("finding all Staffsign instances");
		try {
			String queryString = "from Staffsign";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Staffsign merge(Staffsign detachedInstance) {
        log.debug("merging Staffsign instance");
        try {
            Staffsign result = (Staffsign) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Staffsign instance) {
        log.debug("attaching dirty Staffsign instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Staffsign instance) {
        log.debug("attaching clean Staffsign instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}