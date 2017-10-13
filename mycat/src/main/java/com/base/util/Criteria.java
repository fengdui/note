package com.base.util;

import java.util.List;

/**
 * 条件查询类
 */
public interface Criteria {
    public List<String> getFields();

    public void setFields(List<String> fields);

    public List<Object> getCondition();

    public void setCondition(List<Object> condition);

    public int getStart();

    public void setStart(int start);

    public int getFetchSize();

    public void setFetchSize(int fetchSize);

    public List<String> getOrder();

    public void setOrder(List<String> order);

    public int getPageNo();

    public void setPageNo(int pageNo);

    public String toString();

    public String getFieldClause();

    public String getOrderClause();

    public String getWhereClause();

    /**
     * 添加条件
     *
     * @param position  添加的位置
     * @param condition
     */
    public void addCondition(int position, Condition condition);

    public void addOrder(int index, String order);
}
