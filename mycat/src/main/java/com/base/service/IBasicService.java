package com.base.service;

/**
 * User: qufengfu
 * Date: 13-7-6
 */
/*
 * <p>Description:Service基础接口 </p>
 *
 */

import com.base.exception.ServiceException;
import com.base.util.Criteria;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 持久层基本的接口定义 所有的DAO接口都必须继承该接口
 */
public interface IBasicService<T> {

    /**
     * 从数据库读取所有对象
     *
     * @return list 对象的记录集合
     * @throws com.base.exception.ServiceException
     */
    public List<T> getAll() throws ServiceException;

    /**
     * 从数据库读取所有对象
     *
     * @param first 第一条记录
     * @param limit 最大记录数
     * @return 包含对象的记录集合
     * @throws com.base.exception.ServiceException
     */
    public List<T> getList(int first, int limit) throws ServiceException;

    /**
     * 得到对象的总数量
     *
     * @return 对象的总数量
     * @throws com.base.exception.ServiceException
     */
    public Integer getCount() throws ServiceException;

    /**
     * 根据id得到符合条件的该对象，对象不存在时返回空
     *
     * @param key 对象的ID
     * @return 符合条件的该对象
     * @throws com.base.exception.ServiceException
     */
    public T getObject(Long key) throws ServiceException;

    /**
     * 将对象信息持久化
     *
     * @param object
     * @throws com.base.exception.ServiceException
     */
    public void create(T object) throws ServiceException;

    /**
     * 修改对象信息
     *
     * @param object
     * @throws com.base.exception.ServiceException
     */
    public void update(T object) throws ServiceException;

    /**
     * 根据条件删除对象的信息
     *
     * @param id 要删除对象的ID
     * @throws com.base.exception.ServiceException
     */
    public void delete(Object id) throws ServiceException;

    /**
     * 简单查询，将map中的条件组合进行"="查询
     * @param map 查询条件,其中key是字段名,value是条件
     * @return list 包含符合条件对象的记录集合
     * @throws com.base.exception.ServiceException
     */
    public T findOne(Map<String, Object> map) throws ServiceException;

    /**
     * 简单查询，将map中的条件组合进行"="查询
     * @param map 查询条件,其中key是字段名,value是条件
     * @return list 包含符合条件对象的记录集合
     * @throws com.base.exception.ServiceException
     */
    public List<T> find(Map<String, Object> map) throws ServiceException;

    /**
     * 根据in条件查询
     *
     * @param map 查询条件,多个值时请用,分割
     * @return list 包含符合条件对象的记录集合
     * @throws com.base.exception.ServiceException
     */
    public List<T> findByIn(Map<String, Object> map) throws ServiceException;

    /**
     * 得到符合条件的该对象,用Like进行查询
     *
     * @param map 查询条件,其中key是字段名,value是条件
     * @return list 包含符合条件对象的记录集合
     * @throws com.base.exception.ServiceException
     */
    public List<T> findByLike(Map<String, Object> map) throws ServiceException;

    /**
     * 根据传入的Criteria条件查询，返回结果集
     *
     * @param c 传入的Criteria条件查询对象
     * @return 返回符合条件的结果集
     * @throws com.base.exception.ServiceException
     */
    public Page<T> getList(final Criteria c) throws ServiceException;

    /**
     * 根据传入的Criteria条件查询，返回结果数量
     *
     * @param c 传入的Criteria条件查询对象
     * @return 结果数量
     * @throws com.base.exception.ServiceException
     */
    public Integer getCount(Criteria c) throws ServiceException;

    /**
     * 根据对象进行单一查询
     * @param object 查询的对象
     * @return 符合条件的单一对象
     * @throws ServiceException
     */
    public T findOne(T object) throws ServiceException;

    /**
     * 根据对象进行列表查询
     * @param object 查询的对象
     * @return 符合条件的对象列表
     * @throws ServiceException
     */
    public List<T> find(T object) throws ServiceException;

}