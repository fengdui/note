package com.base.dao;

import com.base.exception.DaoException;
import com.base.util.Criteria;
import com.base.util.Parameter;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * User: qufengfu
 * Date: 13-7-6
 */
public abstract class MybatisBasicDao<T> implements IBasicDao<T> {
    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SqlSession sqlSession;

    protected Class<T> modelClass;
    private String namespace;

    public MybatisBasicDao(Class<T> modelClass){
        this.modelClass = modelClass;
        this.namespace = modelClass.getName();
    }

    public List<T> getAll() throws DaoException {
        return this.sqlSession.selectList(namespace + ".selectAll");
    }

    public List<T> getList(int first, int limit) throws DaoException {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        map.put("offset", first);
        map.put("limit", limit);

        return this.sqlSession.selectList(namespace + ".selectSome",map);
    }

    public Integer getCount() throws DaoException {
        return (Integer)this.sqlSession.selectOne(namespace + ".getTotalCount");
    }

    public T getObject(Long key) throws DaoException {
        return (T)this.sqlSession.selectOne(namespace + ".getObject", key);
    }

    public void create(T object) throws DaoException {
        this.sqlSession.insert(namespace + ".insert", object);
    }

    public void update(T object) throws DaoException {
        this.sqlSession.update(namespace + ".update", object);
    }

    public void delete(Object id) throws DaoException {
        this.sqlSession.delete(namespace + ".delete", id);
    }

    public T findOne(Map<String, Object> map) throws DaoException {
        StringBuilder sb = new StringBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        Set<String> keys = map.keySet();
        for(String key : keys){
            params.add(new Parameter(key, map.get(key)));
        }

        return (T)this.sqlSession.selectOne(namespace + ".findOneByMap",params);
    }

    public T findOne(T object) throws DaoException {
        return (T)this.sqlSession.selectOne(namespace + ".findOneByObject",object);
    }

    public List<T> find(Map<String, Object> map) throws DaoException {
        StringBuilder sb = new StringBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        Set<String> keys = map.keySet();
        for(String key : keys){
            params.add(new Parameter(key, map.get(key)));
        }

        return this.sqlSession.selectList(namespace + ".findByMap",params);
    }

    public List<T> find(T object) throws DaoException {
        return this.sqlSession.selectList(namespace + ".findByObject",object);
    }

    public List<T> findByIn(Map<String, Object> map) throws DaoException {
        StringBuilder sb = new StringBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        Set<String> keys = map.keySet();
        for(String key : keys){
            params.add(new Parameter(key, map.get(key)));
        }

        return this.sqlSession.selectList(namespace + ".findByIn",params);
    }

    public List<T> findByLike(Map<String, Object> map) throws DaoException {
        StringBuilder sb = new StringBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        Set<String> keys = map.keySet();
        for(String key : keys){
            params.add(new Parameter(key, map.get(key)));
        }

        return this.sqlSession.selectList(namespace + ".findByLike",params);
    }

    public Page<T> getList(Criteria c) throws DaoException {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("fieldsClause", c.getFieldClause());
        map.put("whereClause", c.getWhereClause());
        map.put("orderClause", c.getOrderClause());
        map.put("offset", String.valueOf(c.getStart()));
        map.put("limit", String.valueOf(c.getFetchSize()));

        int count = getCount(c);
        List<T> list = this.sqlSession.selectList(namespace + ".query",map);

        final Criteria cri = c;

        Page<T> page = new PageImpl<T>(list, new Pageable() {
            public int getPageNumber() {
                return cri.getPageNo();
            }

            public int getPageSize() {
                return cri.getFetchSize();
            }

            public int getOffset() {
                return cri.getStart();
            }

            public Sort getSort() {
                List<String> orders = cri.getOrder();
                if (orders.size()==0) return null;

                String order = orders.get(0);
                String[] splits = order.split(" ");
                if (splits.length>1){
                    if ("asc".equals(splits[1])){
                        return new Sort(Sort.Direction.ASC, splits[0]);
                    }else{
                        return new Sort(Sort.Direction.DESC, splits[0]);
                    }
                }else{
                    return new Sort(splits[0]);
                }
            }
        }, count);

        return page;
    }

    public Integer getCount(Criteria c) throws DaoException {
        int count = 0;

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("whereClause", c.getWhereClause());

        count = (Integer)this.sqlSession.selectOne(namespace + ".getCount",map);

        return count;
    }
}