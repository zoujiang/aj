package com.frame.core.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;


/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public interface GenericDAO{
    
	public Session getSession();
	
	/**
	 * 保存pojo
	 */
    public <M,PK> PK save(M model);

    /**
     *保存更新
     * @param model
     */
    public <M> void saveOrUpdate(M model);
    
    /**
     * 更新pojo
     * @param model
     */
    public  <M> void update(M model);
    
    
    
    public  <M> void merge(M model);
    
    /**
     * 删除
     * @param id
     */
    public <M, PK extends Serializable> void delete(Class<M> clazz,PK pk);

    /**
     * 删除
     * @param model
     */
    public <M> void deleteObject(M model);
    
    /**
     * 删除
     * */
    public void deleteByHsql(String hsql);

    /**
     * 获取
     * @param id
     * @return
     */
    public <M,PK extends Serializable> M get(Class<M> clazz,PK id);
    
    /**
     * 获取
     * @param id
     * @return
     */
    public <M,PK extends Serializable> M load(Class<M> clazz,PK id);
    
    /**
     * 总条数
     * @return
     */
    public <M> int countAll(Class<M> clazz);

    /**
     * 所有记录
     * @return
     */
    public <M> List<M> listAll(Class<M> clazz);

    /**
     * 获取数据条数
     */
    public int getGenericCountByHql(final String hsql,final Object ...paramlist);
    
    /**
     * HSQL查询
     */
    public <T>List<T> getGenericByHql(final String hsql,final Object ...paramlist);
    
    /**
     * hsql分页查询
     * @param <T>
     * @param hsql
     * @param position
     * @param pageSize
     * @param paramlist
     * @return
     */
    public <T>List<T> getGenericByPosition(final String hsql,final int position,final int pageSize,final Object ...paramlist);
    
    /**
     * 是否存在
     * @param id
     * @return
     */
    public <M,PK extends Serializable> boolean  exists(Class<M> clazz,PK id);
    
    public void flush();
    
    public void clear();
    
    /**
     * 执行Hsql，insert;update;delete;
     */
    public int execteBulk(final String hql, final Object... paramlist);
    
    /**
     * 执行原生SQL，insert;update;delete;
     * @param natvieSQL
     * @param paramlist
     * @return
     */
    public int execteNativeBulk(final String natvieSQL, final Object... paramlist) ;
    
    /**
     * 执行原生SQL查询
     */
    public <T> List<T> query(final String nativeSQL,final Class<T> entity,final Object... paramlist);
    
    
    /**
     * 执行原生SQL查询
     */
    public <T> List<T> query(final String nativeSQL,final Object... paramlist);

    public <T> List<T> getGenericByPositionToSQL(String sql, int position, int pageSize,Object[] paramlist);

	public int getGenericCountToSQL(String sql, Object[] paramlist);

	public <T> List<T> getGenericBySQL(String sql, Object[] paramlist);

	public int update(String hsql, Object...  params);

}
