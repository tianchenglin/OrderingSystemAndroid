package com.utopia84.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.utopia84.model.Product;

@Component(value = "ProductDAO")
public class ProductDAO  {
	     private static final Logger log = LoggerFactory.getLogger(ProductDAO.class);
		//property constants
	public static final String PDT_CODE = "pdtCode";
	public static final String PDT_NAME = "pdtName";
	public static final String PDT_PY = "pdtPy";
	public static final String PDT_UNIT = "pdtUnit";
	public static final String PDT_GG = "pdtGg";
	public static final String PDT_IN_PRICE = "pdtInPrice";
	public static final String PDT_SALE_PRICE1 = "pdtSalePrice1";
	public static final String PDT_SALE_PRICE2 = "pdtSalePrice2";
	public static final String TYPE_ID = "typeId";
	public static final String PDT_CHANGE_PRICE = "pdtChangePrice";
	public static final String PDT_CAN_ZK = "pdtCanZk";
	public static final String PDT_PAY_TYPE = "pdtPayType";
	public static final String DEPART_ID = "departId";
	public static final String PDT_AUTO_INC = "pdtAutoInc";
	public static final String PDT_CAN_USED = "pdtCanUsed";
	public static final String PDT_IN_MIX = "pdtInMix";
	public static final String PDTIS_SET = "pdtisSet";
	public static final String PDT_MCODE = "pdtMcode";
	public static final String PDT_MAKE_TIME = "pdtMakeTime";
	public static final String PDT_ACC_TYPE = "pdtAccType";
	public static final String PDTCHG_NUMBER = "pdtchgNumber";
	public static final String PDTDOWNPRICE1 = "pdtdownprice1";
	public static final String PDTDOWNPRICE2 = "pdtdownprice2";
	public static final String MINREBATE = "minrebate";
	public static final String NOTOUT = "notout";
	public static final String NOTSHOWONBILL = "notshowonbill";
	public static final String NOTSHOW = "notshow";
	public static final String PDT_NO_SHOW = "pdtNoShow";


	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    
    public void save(Product transientInstance) {
        log.debug("saving Product instance");
        try {
            hibernateTemplate.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    public void update(Product transientInstance) {
        log.debug("saving Product instance");
        try {
            hibernateTemplate.update(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
	public void delete(Product persistentInstance) {
        log.debug("deleting Product instance");
        try {
            hibernateTemplate.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Product findById( java.lang.Integer id) {
        log.debug("getting Product instance with id: " + id);
        try {
            Product instance = (Product) hibernateTemplate
                    .get("com.utopia84.model.Product", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Product instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Product as model where model." 
         						+ propertyName + "= ?";
		 return hibernateTemplate.find(queryString,value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
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
	
	public List findByPdtPy(Object pdtPy
	) {
		return findByProperty(PDT_PY, pdtPy
		);
	}
	
	public List findByPdtUnit(Object pdtUnit
	) {
		return findByProperty(PDT_UNIT, pdtUnit
		);
	}
	
	public List findByPdtGg(Object pdtGg
	) {
		return findByProperty(PDT_GG, pdtGg
		);
	}
	
	public List findByPdtInPrice(Object pdtInPrice
	) {
		return findByProperty(PDT_IN_PRICE, pdtInPrice
		);
	}
	
	public List findByPdtSalePrice1(Object pdtSalePrice1
	) {
		return findByProperty(PDT_SALE_PRICE1, pdtSalePrice1
		);
	}
	
	public List findByPdtSalePrice2(Object pdtSalePrice2
	) {
		return findByProperty(PDT_SALE_PRICE2, pdtSalePrice2
		);
	}
	
	public List findByTypeId(Object typeId
	) {
		return findByProperty(TYPE_ID, typeId
		);
	}
	
	public List findByPdtChangePrice(Object pdtChangePrice
	) {
		return findByProperty(PDT_CHANGE_PRICE, pdtChangePrice
		);
	}
	
	public List findByPdtCanZk(Object pdtCanZk
	) {
		return findByProperty(PDT_CAN_ZK, pdtCanZk
		);
	}
	
	public List findByPdtPayType(Object pdtPayType
	) {
		return findByProperty(PDT_PAY_TYPE, pdtPayType
		);
	}
	
	public List findByDepartId(Object departId
	) {
		return findByProperty(DEPART_ID, departId
		);
	}
	
	public List findByPdtAutoInc(Object pdtAutoInc
	) {
		return findByProperty(PDT_AUTO_INC, pdtAutoInc
		);
	}
	
	public List findByPdtCanUsed(Object pdtCanUsed
	) {
		return findByProperty(PDT_CAN_USED, pdtCanUsed
		);
	}
	
	public List findByPdtInMix(Object pdtInMix
	) {
		return findByProperty(PDT_IN_MIX, pdtInMix
		);
	}
	
	public List findByPdtisSet(Object pdtisSet
	) {
		return findByProperty(PDTIS_SET, pdtisSet
		);
	}
	
	public List findByPdtMcode(Object pdtMcode
	) {
		return findByProperty(PDT_MCODE, pdtMcode
		);
	}
	
	public List findByPdtMakeTime(Object pdtMakeTime
	) {
		return findByProperty(PDT_MAKE_TIME, pdtMakeTime
		);
	}
	
	public List findByPdtAccType(Object pdtAccType
	) {
		return findByProperty(PDT_ACC_TYPE, pdtAccType
		);
	}
	
	public List findByPdtchgNumber(Object pdtchgNumber
	) {
		return findByProperty(PDTCHG_NUMBER, pdtchgNumber
		);
	}
	
	public List findByPdtdownprice1(Object pdtdownprice1
	) {
		return findByProperty(PDTDOWNPRICE1, pdtdownprice1
		);
	}
	
	public List findByPdtdownprice2(Object pdtdownprice2
	) {
		return findByProperty(PDTDOWNPRICE2, pdtdownprice2
		);
	}
	
	public List findByMinrebate(Object minrebate
	) {
		return findByProperty(MINREBATE, minrebate
		);
	}
	
	public List findByNotout(Object notout
	) {
		return findByProperty(NOTOUT, notout
		);
	}
	
	public List findByNotshowonbill(Object notshowonbill
	) {
		return findByProperty(NOTSHOWONBILL, notshowonbill
		);
	}
	
	public List findByNotshow(Object notshow
	) {
		return findByProperty(NOTSHOW, notshow
		);
	}
	
	public List findByPdtNoShow(Object pdtNoShow
	) {
		return findByProperty(PDT_NO_SHOW, pdtNoShow
		);
	}
	

	public List findAll() {
		log.debug("finding all Product instances");
		try {
			String queryString = "from Product order by pdtId desc";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllNotSelected() {
		log.debug("finding all not selected Product instances");
		try {
			String queryString = "from Product p, Itemandmenutype i where p.pdtId <> i.itemId order by p.pdtId desc";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Product merge(Product detachedInstance) {
        log.debug("merging Product instance");
        try {
            Product result = (Product) hibernateTemplate
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Product instance) {
        log.debug("attaching dirty Product instance");
        try {
            hibernateTemplate.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Product instance) {
        log.debug("attaching clean Product instance");
        try {
            hibernateTemplate.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public List<Product> findByCategory(int category) {
		return hibernateTemplate.find("from Product where typeId="+category);
	}
}