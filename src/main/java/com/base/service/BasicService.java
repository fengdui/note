package com.base.service;

import com.base.dao.IBasicDao;
import com.base.exception.DaoException;
import com.base.exception.ServiceException;
import com.base.util.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: qufengfu
 * Date: 13-7-6
 */
public abstract class BasicService<T> implements IBasicService<T> {
    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract IBasicDao<T> getDao();

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<T> getAll() throws ServiceException {
        try{
            return getDao().getAll();
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<T> getList(int first, int limit) throws ServiceException {
        try{
            return getDao().getList(first, limit);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Integer getCount() throws ServiceException {
        try{
            return getDao().getCount();
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public T getObject(Long key) throws ServiceException {
        try{
            return getDao().getObject(key);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void create(T object) throws ServiceException {
        try{
            getDao().create(object);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(T object) throws ServiceException {
        try{
            getDao().update(object);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Object id) throws ServiceException {
        try{
            getDao().delete(id);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public T findOne(Map<String, Object> map) throws ServiceException {
        try{
            return getDao().findOne(map);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<T> find(Map<String, Object> map) throws ServiceException {
        try{
            return getDao().find(map);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<T> findByIn(Map<String, Object> map) throws ServiceException {
        try{
            return getDao().findByIn(map);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<T> findByLike(Map<String, Object> map) throws ServiceException {
        try{
            return getDao().findByLike(map);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Page<T> getList(Criteria c) throws ServiceException {
        try{
            return getDao().getList(c);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Integer getCount(Criteria c) throws ServiceException {
        try{
            return getDao().getCount(c);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public T findOne(T object) throws ServiceException{
        try{
            return (T)getDao().findOne(object);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }

    /**
     * 根据对象进行列表查询
     * @param object 查询的对象
     * @return 符合条件的对象列表
     * @throws ServiceException
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<T> find(T object) throws ServiceException{
        try{
            return getDao().find(object);
        }catch (DaoException e){
            throw new ServiceException("ERROR:",e);
        }catch (Exception e){
            throw new ServiceException("ERROR:",e);
        }
    }
}