package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Options;

/**
 	* A data access object (DAO) providing persistence and search support for Options entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Options
  * @author MyEclipse Persistence Tools 
 */
@Component(value = "OptionsDAO")
public class OptionsDAO {
	     private static final Logger log = LoggerFactory.getLogger(OptionsDAO.class);
		//property constants
	public static final String OPTION_NAME = "optionName";
	public static final String OPTION_PRICE = "optionPrice";
	public static final String MODIFIER_ID = "modifierId";



	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    public void save(Options transientInstance) {
        log.debug("saving Options instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Options persistentInstance) {
        log.debug("deleting Options instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Options findById( java.lang.Integer id) {
        log.debug("getting Options instance with id: " + id);
        try {
            Options instance = (Options) hibernateTemplate
                    .get("com.utopia84.model.Options", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Options instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Options as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByOptionName(Object optionName
	) {
		return findByProperty(OPTION_NAME, optionName
		);
	}
	
	public List findByOptionPrice(Object optionPrice
	) {
		return findByProperty(OPTION_PRICE, optionPrice
		);
	}
	
	public List findByModifierId(Object modifierId
	) {
		return findByProperty(MODIFIER_ID, modifierId
		);
	}
	

	public List findAll() {
		log.debug("finding all Options instances");
		try {
			String queryString = "from Options";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Options merge(Options detachedInstance) {
        log.debug("merging Options instance");
        try {
            Options result = (Options) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Options instance) {
        log.debug("attaching dirty Options instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Options instance) {
        log.debug("attaching clean Options instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}