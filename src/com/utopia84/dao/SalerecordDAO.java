package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.utopia84.model.Salerecord;

/**
 	* A data access object (DAO) providing persistence and search support for Salerecord entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.utopia84.model.Salerecord
  * @author MyEclipse Persistence Tools 
 */

public class SalerecordDAO {
	     private static final Logger log = LoggerFactory.getLogger(SalerecordDAO.class);
		//property constants
	public static final String PAY_ID = "payId";//支付编码
	public static final String BILLID = "billid";//是否一次支付
	public static final String PDT_CODE = "pdtCode";//菜编码
	public static final String PDT_NAME = "pdtName";//菜名
	public static final String NUMBER = "number";//菜的数量
	public static final String PRICE = "price";//价格
	public static final String REBATE = "rebate";//折扣
	public static final String STATUS = "status";//状态
	public static final String DESK_NAME = "deskName";//桌子名
	public static final String OTHER_SPEC_NO1 = "otherSpecNo1";//备注1
	public static final String OTHER_SPEC_NO2 = "otherSpecNo2";//备注2
	public static final String OTHER_SPEC = "otherSpec";//备注
	public static final String WAITER = "waiter";//服务员
	public static final String TAX = "tax";//税收


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
	//保存记录
    public void save(Salerecord transientInstance) {
        log.debug("saving Salerecord instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    //删除记录
	public void delete(Salerecord persistentInstance) {
        log.debug("deleting Salerecord instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
	//根据ID得到记录
    public Salerecord findById( java.lang.Integer id) {
        log.debug("getting Salerecord instance with id: " + id);
        try {
            Salerecord instance = (Salerecord) hibernateTemplate
                    .get("com.utopia84.model.Salerecord", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Salerecord instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Salerecord as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPayId(Object payId
	) {
		return findByProperty(PAY_ID, payId
		);
	}
	
	public List findByBillid(Object billid
	) {
		return findByProperty(BILLID, billid
		);
	}
	
	public List findByPdtCode(Object pdtCode
	) {
		return findByProperty(PDT_CODE, pdtCode
		);
	}
	
	public List findByPdtName(Object pdtName
	) {
		return findByProperty(PDT_NAME, pdtName
		);
	}
	
	public List findByNumber(Object number
	) {
		return findByProperty(NUMBER, number
		);
	}
	
	public List findByPrice(Object price
	) {
		return findByProperty(PRICE, price
		);
	}
	
	public List findByRebate(Object rebate
	) {
		return findByProperty(REBATE, rebate
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	
	public List findByDeskName(Object deskName
	) {
		return findByProperty(DESK_NAME, deskName
		);
	}
	
	public List findByOtherSpecNo1(Object otherSpecNo1
	) {
		return findByProperty(OTHER_SPEC_NO1, otherSpecNo1
		);
	}
	
	public List findByOtherSpecNo2(Object otherSpecNo2
	) {
		return findByProperty(OTHER_SPEC_NO2, otherSpecNo2
		);
	}
	
	public List findByOtherSpec(Object otherSpec
	) {
		return findByProperty(OTHER_SPEC, otherSpec
		);
	}
	
	public List findByWaiter(Object waiter
	) {
		return findByProperty(WAITER, waiter
		);
	}
	
	public List findByTax(Object tax
	) {
		return findByProperty(TAX, tax
		);
	}
	
	//得到全部记录
	public List findAll() {
		log.debug("finding all Salerecord instances");
		try {
			String queryString = "from Salerecord";
			 return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
    public Salerecord merge(Salerecord detachedInstance) {
        log.debug("merging Salerecord instance");
        try {
            Salerecord result = (Salerecord) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Salerecord instance) {
        log.debug("attaching dirty Salerecord instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Salerecord instance) {
        log.debug("attaching clean Salerecord instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}