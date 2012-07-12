package com.skcoe.db.impl;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;




import com.skcoe.db.entity.UserAccount;


public class UserAccountImpl  extends HibernateDaoSupport {

	public void save(UserAccount stock){
		getHibernateTemplate().save(stock);
	}
 
	public void update(UserAccount stock){
		getHibernateTemplate().update(stock);
	}
 
	public void delete(UserAccount stock){
		getHibernateTemplate().delete(stock);
	}
 
	public List findAll(){
		List list = getHibernateTemplate().find( "from UserAccount");
		return list;
	}



}


