package com.hi.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.hi.common.Pagination;
import com.hi.model.Count;
import com.hi.model.Nextval;

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

	public long getNextvalBySeqName(String seqName) {
		String sql = "select " + seqName + ".nextval as id from dual";
		return this.getUniqueBeanBySql(Nextval.class, sql, null).getID().longValue();
	}

	public long countBySql(String sql) {
		return countBySql(sql, null);
	}

	public long countBySql(String sql, Map<String, Object> params) {
		return this.getUniqueBeanBySql(Count.class, "select count(*) as \"count\" from (" + sql + ")", params).longValue();
	}

	public <T> T getFirstBeanBySql(Class<T> clazz, String sql) {
		return getFirstBeanBySql(clazz, sql, null);
	}

	public <T> T getFirstBeanBySql(Class<T> clazz, String sql, Map<String, Object> params) {
		return getFirstBeanBySql(clazz, sql, params, new Pagination(2));
	}

	public <T> T getUniqueBeanBySql(Class<T> clazz, String sql) {
		return getUniqueBeanBySql(clazz, sql, null);
	}

	public <T> T getUniqueBeanBySql(Class<T> clazz, String sql, Map<String, Object> params) {
		return getFirstBeanBySql(clazz, sql, params, null);
	}

	private <T> T getFirstBeanBySql(Class<T> clazz, String sql, Map<String, Object> params, Pagination pagn) {
		List<T> list = getBeansBySql(clazz, sql, params, pagn);
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

	public int executiveSql(String sql, Map<String, Object> params) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.executeUpdate();
	}
}
