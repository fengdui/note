package com.base.util;

import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单条件查询类
 */
public class SimpleCriteria implements Criteria {
    private List<String> fields = new ArrayList<String>();
    /**
     * 要排序的字段
     */
    private List<String> order = new ArrayList<String>();
    /**
     * 条件查询子类
     */
    private List<Object> condition = new ArrayList<Object>();
    /**
     * 当前页数
     */
    private int pageNo = 1;
    /**
     * 第一条记录
     */
    private int start;
    /**
     * 最大记录数
     */
    private int fetchSize = 10;

    public SimpleCriteria() {
    }

    public SimpleCriteria(int pageNo, int fetchSize) {
        this.pageNo = pageNo;
        this.fetchSize = fetchSize;
        this.start = (this.pageNo - 1) * this.fetchSize;
    }

    public SimpleCriteria(HttpServletRequest request) {
        this.pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        this.fetchSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        this.start = (this.pageNo - 1) * this.fetchSize;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<Object> getCondition() {
        return condition;
    }

    public void setCondition(List<Object> condition) {
        this.condition = condition;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
        this.start = (this.pageNo - 1) * this.fetchSize;
    }

    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        this.start = (this.pageNo - 1) * this.fetchSize;
    }

    @Override
    public String toString() {
        StringBuffer clause = new StringBuffer();
        List<Object> l = this.getCondition();
        if (l != null) {
            Condition c1;
            // 循环读取条件对象
            int size = l.size();

            // 如果size > 1, 先构造第一个where子句
            if (size > 0) {
                c1 = (Condition)l.get(0);
                clause.append(" where ");
                clause.append(c1.getField());
                clause.append(" ");
                clause.append(c1.getRelation());
                clause.append(" ");
                clause.append(c1.getValue());
            }

            // 循环从1开始
            for (int i = 1; i < size; i++) {
                c1 = (Condition)l.get(i);

                // 直接构造where条件
                clause.append(" ");
                clause.append(c1.getType());
                clause.append(" ");
                clause.append(c1.getField());
                clause.append(" ");
                clause.append(c1.getRelation());
                clause.append(" ");
                clause.append(c1.getValue());
            }
        }

        if (this.getOrder().size() > 0) {
            clause.append(" ORDER BY ");
            for (int i = 0; i < this.getOrder().size(); i++) {
                clause.append(this.getOrder().get(i));
                clause.append(",");
            }
        }

        String sql = clause.toString();

        if (sql.endsWith(","))
            sql = sql.substring(0, sql.length() - 1);
        return sql;
    }

    public String getFieldClause() {
        if (fields.size()==0) return " * ";
        StringBuffer clause = new StringBuffer();
        if (this.getFields().size() > 0) {
            for (String field : this.getFields()) {
                clause.append(field);
                clause.append(",");
            }
        }

        return clause.substring(0,clause.length()-1).toString();
    }

    public String getOrderClause() {
        if (order.size()==0) return " ";
        StringBuffer clause = new StringBuffer();
        if (this.getOrder().size() > 0) {
            clause.append(" ORDER BY ");
            for (String field : this.getOrder()) {
                clause.append(field);
                clause.append(",");
            }
        }

        return clause.substring(0,clause.length()-1).toString();
    }

    public String getWhereClause() {
        StringBuffer clause = new StringBuffer();
        List<Object> l = this.getCondition();

        // 循环读取条件对象
        for (Object obj : l) {
            Condition c1 = (Condition)obj;

            // 直接构造where条件
            clause.append(" ");
            clause.append(c1.getType());
            clause.append(" ");
            clause.append(c1.getField());
            clause.append(" ");
            clause.append(c1.getRelation());
            clause.append(" ");
            if (c1.getValue() instanceof String){
                clause.append("'").append(c1.getValue()).append("'");
            }else{
                clause.append(c1.getValue());
            }
        }

        return clause.toString();
    }

    public void addCondition(int position, Condition condition) {
        if (condition != null)
            this.condition.add(condition);
    }

    public void addOrder(int index, String order) {
        if (order != null && !"".equals(order)){
            this.order.add(index,order);
        }
    }
}