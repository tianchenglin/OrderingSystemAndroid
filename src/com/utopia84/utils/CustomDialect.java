package com.utopia84.utils;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class CustomDialect extends MySQL5Dialect {

	public CustomDialect(){
		super();
		this.registerHibernateType(-1,Hibernate.STRING.getName());
		this.registerHibernateType(-4,Hibernate.STRING.getName());
	}
}
