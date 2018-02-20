package com.frame.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Repository("baseDAO")
public class BaseDAOImpl extends GenericDAOImpl {

	@Override
	public void clear() {

		super.clear();

	}

	@Override
	public <M> int countAll(Class<M> clazz) {

		return super.countAll(clazz);

	}

	@Override
	public <M, PK extends Serializable> void delete(Class<M> clazz, PK pk) {

		super.delete(clazz, pk);

	}

	@Override
	public <M> void deleteObject(M model) {

		super.deleteObject(model);

	}

	@Override
	public int execteBulk(String hql, Object... paramlist) {

		return super.execteBulk(hql, paramlist);

	}

	@Override
	public int execteNativeBulk(String natvieSQL, Object... paramlist) {

		return super.execteNativeBulk(natvieSQL, paramlist);

	}

	@Override
	public <M, PK extends Serializable> boolean exists(Class<M> clazz, PK id) {

		return super.exists(clazz, id);

	}

	@Override
	public void flush() {

		super.flush();

	}

	@Override
	public <M, PK extends Serializable> M get(Class<M> clazz, PK id) {

		return super.get(clazz, id);

	}

	@Override
	public <M, PK extends Serializable> M load(Class<M> clazz, PK id) {

		return super.load(clazz, id);

	}

	@Override
	public <T> List<T> getGenericByHql(String hsql, Object... paramlist) {

		return super.getGenericByHql(hsql, paramlist);

	}

	@Override
	public <T> List<T> getGenericByPosition(String hsql, int position, int pageSize, Object... paramlist) {

		return super.getGenericByPosition(hsql, position, pageSize, paramlist);

	}

	@Override
	public int getGenericCountByHql(String hsql, Object... paramlist) {

		return super.getGenericCountByHql(hsql, paramlist);

	}

	@Override
	public <M> List<M> listAll(Class<M> clazz) {

		return super.listAll(clazz);

	}

	@Override
	public <M> void merge(M model) {

		super.merge(model);

	}

	@Override
	public <T> List<T> query(String nativeSQL, Class<T> entity, Object... paramlist) {

		return super.query(nativeSQL, entity, paramlist);

	}

	@Override
	public <M, PK> PK save(M model) {

		return super.save(model);

	}

	@Override
	public <M> void saveOrUpdate(M model) {

		super.saveOrUpdate(model);

	}

	@Override
	public <M> void update(M model) {

		super.update(model);

	}
	@Override
	public <T> List<T> getGenericByPositionToSQL(String sql, int position,
			int pageSize, Object[] paramlist) {
		return super.getGenericByPositionToSQL(sql, position, pageSize, paramlist);
	}
	
	@Override
	public int getGenericCountToSQL(String sql, Object[] paramlist){
		return super.getGenericCountToSQL(sql, paramlist);
	}
}
