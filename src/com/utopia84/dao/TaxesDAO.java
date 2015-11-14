package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Taxes;

/**
 	* A data access object (DAO) providing persistence and search support for Taxes entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Taxes
  * @author MyEclipse Persistence Tools 
 */
@Component(value="TaxesDAO")
public class TaxesDAO    {
	     private static final Logger log = LoggerFactory.getLogger(TaxesDAO.class);
		//property constants
	public static final String TAXE_NAME = "taxeName";
	public static final String RATE = "rate";
	public static final String INCLUDE_TAXES_IN_PRICE = "includeTaxesInPrice";
	public static final String ASSIGN_TAX_TO_ALL_ITEMS = "assignTaxToAllItems";
	public static final String ITEM_ID = "itemId";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
    public void save(Taxes transientInstance) {
        log.debug("saving Taxes instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Taxes persistentInstance) {
        log.debug("deleting Taxes instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Taxes findById( java.lang.Integer id) {
        log.debug("getting Taxes instance with id: " + id);
        try {
            Taxes instance = (Taxes) hibernateTemplate
                    .get("com.utopia84.model.Taxes", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Taxes instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Taxes as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByTaxeName(Object taxeName
	) {
		return findByProperty(TAXE_NAME, taxeName
		);
	}
	
	public List findByRate(Object rate
	) {
		return findByProperty(RATE, rate
		);
	}
	
	public List findByIncludeTaxesInPrice(Object includeTaxesInPrice
	) {
		return findByProperty(INCLUDE_TAXES_IN_PRICE, includeTaxesInPrice
		);
	}
	
	public List findByAssignTaxToAllItems(Object assignTaxToAllItems
	) {
		return findByProperty(ASSIGN_TAX_TO_ALL_ITEMS, assignTaxToAllItems
		);
	}
	
	public List findByItemId(Object itemId
	) {
		return findByProperty(ITEM_ID, itemId
		);
	}
	

	public List<Taxes> findAll() {
		log.debug("finding all Taxes instances");
		System.out.println("finding all Taxess instances");
		try {
			String queryString = "from Taxes";
			 return (List<Taxes>)hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Taxes merge(Taxes detachedInstance) {
        log.debug("merging Taxes instance");
        try {
            Taxes result = (Taxes) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Taxes instance) {
        log.debug("attaching dirty Taxes instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Taxes instance) {
        log.debug("attaching clean Taxes instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    
    public boolean update(Taxes persistentInstance) {
		log.debug("deleting Discounts instance");
		try {
			hibernateTemplate.update(persistentInstance);
			log.debug("delete successful");
			return true;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			return false;
		}
	}
}