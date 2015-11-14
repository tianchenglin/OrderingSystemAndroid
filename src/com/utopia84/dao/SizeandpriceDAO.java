package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Sizeandprice;

/**
 	* A data access object (DAO) providing persistence and search support for Sizeandprice entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Sizeandprice
  * @author MyEclipse Persistence Tools 
 */
@Component(value = "SizeandpriceDAO")
public class SizeandpriceDAO    {
	     private static final Logger log = LoggerFactory.getLogger(SizeandpriceDAO.class);
		//property constants
	public static final String SIZE_NAME = "sizeName";
	public static final String SIZE_PRICE = "sizePrice";
	public static final String ITEM_ID = "itemId";

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

    
    public void save(Sizeandprice transientInstance) {
        log.debug("saving Sizeandprice instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void update(Sizeandprice transientInstance) {
        log.debug("saving Sizeandprice instance");
        try {
            hibernateTemplate.update(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Sizeandprice persistentInstance) {
        log.debug("deleting Sizeandprice instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Sizeandprice findById( java.lang.Integer id) {
        log.debug("getting Sizeandprice instance with id: " + id);
        try {
            Sizeandprice instance = (Sizeandprice) hibernateTemplate
                    .get("com.utopia84.model.Sizeandprice", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Sizeandprice instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Sizeandprice as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findBySizeName(Object sizeName
	) {
		return findByProperty(SIZE_NAME, sizeName
		);
	}
	
	public List findBySizePrice(Object sizePrice
	) {
		return findByProperty(SIZE_PRICE, sizePrice
		);
	}
	
	public List findByItemId(Object itemId
	) {
		return findByProperty(ITEM_ID, itemId
		);
	}
	

	public List findAll() {
		log.debug("finding all Sizeandprice instances");
		try {
			String queryString = "from Sizeandprice";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Sizeandprice merge(Sizeandprice detachedInstance) {
        log.debug("merging Sizeandprice instance");
        try {
            Sizeandprice result = (Sizeandprice) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Sizeandprice instance) {
        log.debug("attaching dirty Sizeandprice instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Sizeandprice instance) {
        log.debug("attaching clean Sizeandprice instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}