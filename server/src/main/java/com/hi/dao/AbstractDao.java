package com.hi.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.hi.common.Pagination;

public abstract class AbstractDao {

	protected String DT_FORMAT = "'yyyy-mm-dd hh24:mi:ss'";
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void persist(Object entity) {
		getSession().persist(entity);
	}

	public void delete(Object entity) {
		getSession().delete(entity);
	}

	public <T> T getBeanBySql(Class<T> clazz, String sql) {
		return getBeanBySql(clazz, sql, null);
	}

	public <T> T getBeanBySql(Class<T> clazz, String sql, Map<String, Object> params) {
		List<T> list = getBeansBySql(clazz, sql, params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public <T> List<T> getBeansBySql(Class<T> clazz, String sql) {
		return getBeansBySql(clazz, sql, null, null);
	}

	public <T> List<T> getBeansBySql(Class<T> clazz, String sql, Map<String, Object> params) {
		return getBeansBySql(clazz, sql, params, null);
	}

	public <T> List<T> getBeansBySql(Class<T> clazz, String sql, Pagination pagn) {
		return getBeansBySql(clazz, sql, null, pagn);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getBeansBySql(Class<T> clazz, String sql, Map<String, Object> params, Pagination pagn) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		if (pagn != null) {
			query.setFirstResult(pagn.getStartRow());
			query.setMaxResults(pagn.getPageSize());
		}
		List<T> list = query.setResultTransformer(Transformers.aliasToBean(clazz)).list();

		return list;
	}
}
