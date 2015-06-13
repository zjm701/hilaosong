package com.hi.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public void persist(Object entity) {
		getSession().persist(entity);
	}
	
	public void delete(Object entity) {
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T>  getBeansBySql(Class<T> clazz, String sql, Map<String, Object> params) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if(params != null){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		List<T> list = query.
				setResultTransformer(Transformers.aliasToBean(clazz)).list();
		
		return list;
	}
}
