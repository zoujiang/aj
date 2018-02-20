package com.frame.core.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.ConditionQuery;
import com.frame.core.util.OrderBy;
import com.frame.core.util.pagination.PageUtil;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Repository("genericDAO")
public class GenericDAOImpl implements GenericDAO{

	protected static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);

	private final String HQL_LIST_ALL;
	private final String HQL_COUNT_ALL;

	public GenericDAOImpl() {
		HQL_LIST_ALL = "from ";
		HQL_COUNT_ALL = " select count(*) from ";
	}

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M,PK> PK save(M model) {
		return (PK) getSession().save(model);
	}

	@Override
	public <M> void saveOrUpdate(M model) {
		getSession().saveOrUpdate(model);
	}

	@Override
	public <M> void update(M model) {
		getSession().update(model);

	}

	@Override
	public <M> void merge(M model) {
		getSession().merge(model);
	}

	@Override
    public <M, PK extends Serializable> void delete(Class<M> clazz,PK pk){
		getSession().delete(this.get(clazz, pk));
	}

	@Override
	public <M> void deleteObject(M model) {
		getSession().delete(model);

	}

	@Override
	public <M,PK extends Serializable> boolean exists(Class<M> clazz, PK id) {
		return get(clazz, id) != null;
	}

	@SuppressWarnings("unchecked")
	public<M,PK extends Serializable> M get(Class<M> clazz, PK id) {
		return (M) getSession().get(clazz, id);
	}
	
	

	@Override
	public <M> int countAll(Class<M> clazz) {
		Long total = aggregate(HQL_COUNT_ALL + clazz.getSimpleName());
		return total.intValue();
	}

	@Override
	public <M> List<M> listAll(Class<M> clazz) {
		return list(HQL_LIST_ALL + clazz.getSimpleName());
	}

	// @Override
	// public List<M> listAll(int pn, int pageSize) {
	// return list(HQL_LIST_ALL, pn, pageSize);
	// }
	//
	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public void clear() {
		getSession().clear();
	}

	/**
	 * @see 
	 * @param <T>
	 * @param hsql
	 * @param paramlist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getGenericByHql(final String hsql, final Object... paramlist) {
		Query query = getSession().createQuery(hsql);
		setParameters(query, paramlist);
		List<T> list = query.list();
		return list;
	}

	/**
	 * @see 
	 * @param <T>
	 * @param hsql
	 * @param position
	 * @param pageSize
	 * @param paramlist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getGenericByPosition(final String hsql, final int position, final int pageSize,
			final Object... paramlist) {
		Query query = getSession().createQuery(hsql);
		setParameters(query, paramlist);
		query.setFirstResult(position > 0 ? position : 0);
		query.setMaxResults(pageSize > 0 ? pageSize : 0);
		List<T> list = query.list();
		return list;
	}

	public int getGenericCountByHql(final String hsql, final Object... paramlist) {
		Query query = getSession().createQuery(hsql);
		setParameters(query, paramlist);
		Long num = (Long) query.uniqueResult();
		if(num==null){
			return -1;
		}else{
			return num.intValue();
		}
	}

	protected long getIdResult(String hql, Object... paramlist) {
		long result = -1;
		List<?> list = list(hql, paramlist);
		if (list != null && list.size() > 0) {
			return ((Number) list.get(0)).longValue();
		}
		return result;
	}

	protected <M> List<M> listSelf(final String hql, final int pn, final int pageSize, final Object... paramlist) {
		return this.<M> list(hql, pn, pageSize, paramlist);
	}

	/**
	 * for in
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> listWithIn(final String hql, final int start, final int length,
			final Map<String, Collection<?>> map, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		for (Entry<String, Collection<?>> e : map.entrySet()) {
			query.setParameterList(e.getKey(), e.getValue());
		}
		if (start > -1 && length > -1) {
			query.setMaxResults(length);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> results = query.list();
		return results;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> list(final String hql, final int pn, final int pageSize, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		if (pn < 0) {
			query.setFirstResult(0);
		}
		List<T> results = query.list();
		return results;
	}

	@SuppressWarnings("unchecked")
	protected <T> T unique(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		return (T) query.setMaxResults(1).uniqueResult();
	}

	/**
	 * 
	 * for in
	 */
	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql, final Map<String, Collection<?>> map,
			final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		if (paramlist != null) {
			setParameters(query, paramlist);
			for (Entry<String, Collection<?>> e : map.entrySet()) {
				query.setParameterList(e.getKey(), e.getValue());
			}
		}

		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);

		return (T) query.uniqueResult();

	}

	public int execteBulk(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	public int execteNativeBulk(final String natvieSQL, final Object... paramlist) {
		Query query = getSession().createSQLQuery(natvieSQL);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(final String nativeSQL, final Class<T> entity, final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(nativeSQL).addEntity(entity.getClass());
		setParameters(query, paramlist);
		List<T> result = query.list();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> query(final String nativeSQL, final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(nativeSQL);
		setParameters(query, paramlist);
		List<T> result = query.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(final String nativeSQL, final int pn, final int pageSize, final T entity,
			final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(nativeSQL).addEntity(entity.getClass());
		setParameters(query, paramlist);
		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> result = query.list();
		return result;
	}
	
	

	protected <T> List<T> list(final String sql, final Object... paramlist) {
		return list(sql, -1, -1, paramlist);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> listByNative(final String nativeSQL, final int pn, final int pageSize,
			final List<Entry<String, Class<?>>> entityList, final List<Entry<String, Type>> scalarList,
			final Object... paramlist) {

		SQLQuery query = getSession().createSQLQuery(nativeSQL);
		if (entityList != null) {
			for (Entry<String, Class<?>> entity : entityList) {
				query.addEntity(entity.getKey(), entity.getValue());
			}
		}
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}

		setParameters(query, paramlist);

		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> result = query.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <T> T aggregateByNative(final String natvieSQL, final List<Entry<String, Type>> scalarList,
			final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(natvieSQL);
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}

		setParameters(query, paramlist);

		Object result = query.uniqueResult();
		return (T) result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<T> clazz, ConditionQuery query, OrderBy orderBy, final int pn,
			final int pageSize) {
		Criteria criteria = getSession().createCriteria(clazz);
		query.build(criteria);
		orderBy.build(criteria);
		int start = PageUtil.getPageStart(pn, pageSize);
		if (start != 0) {
			criteria.setFirstResult(start);
		}
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> list(Criteria criteria) {
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <T> T unique(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	public <T> List<T> list(DetachedCriteria criteria) {
		return list(criteria.getExecutableCriteria(getSession()));
	}

	@SuppressWarnings("unchecked")
	public <T> T unique(DetachedCriteria criteria) {
		return (T) unique(criteria.getExecutableCriteria(getSession()));
	}

	protected void setParameters(Query query, Object[] paramlist) {
		if (paramlist != null) {
			for (int i = 0; i < paramlist.length; i++) {
				if(paramlist[i]!=null){
					if (paramlist[i] instanceof Date) {
						query.setTimestamp(i, (Date) paramlist[i]);
					} else {
						query.setParameter(i, paramlist[i]);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M, PK extends Serializable> M load(Class<M> clazz, PK id) {
		return (M) getSession().load(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getGenericByPositionToSQL(String sql, int position,
			int pageSize, Object[] paramlist) {
		//unwrap方法的替代方案，去掉hibernate的侵入性
		Query query = getSession().createSQLQuery(sql);
		//将JPA运用Hibernate的API转成Map结构
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		setParameters(query, paramlist);
		query.setFirstResult(position > 0 ? position : 0);
		query.setMaxResults(pageSize > 0 ? pageSize : 0);
		List<T> list = query.list();
		return list;
	}
	
	@Override
	public <T> List<T> getGenericBySQL(String sql, Object[] paramlist) {
		Query query = getSession().createSQLQuery(sql);
		//将JPA运用Hibernate的API转成Map结构
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		setParameters(query, paramlist);
		List<T> list = query.list();
		return list;
	}
	@Override
	public int getGenericCountToSQL(String sql,Object[] paramlist) {
		/*String countSql="select count(*) from ("+sql+") a";
		Query query=getSession().createSQLQuery(countSql);
		setParameters(query, paramlist);
		BigInteger total=(BigInteger) query.uniqueResult();
		return total.intValue();*/
		
		Query query=getSession().createSQLQuery(sql);
		setParameters(query, paramlist);
		BigDecimal total= (BigDecimal)query.uniqueResult();
		return total.intValue();
	}

	@Override
	public int getGenericIntToSQL(String sql, Object[] paramlist) {
		Query query=getSession().createSQLQuery(sql);
		setParameters(query, paramlist);
		BigInteger total=(BigInteger) query.uniqueResult();
		return total.intValue();
	}
}
