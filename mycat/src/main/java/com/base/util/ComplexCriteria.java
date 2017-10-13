package com.base.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 条件查询类
 */
public class ComplexCriteria implements Criteria {
    protected transient final Log log = LogFactory.getLog(getClass());
    private List<String> fields;
    /**
     * 要排序的字段
     */
    private List<String> order;
    /**
     * 条件查询子类
     */
    private List<Object> condition;
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

    public ComplexCriteria(HttpServletRequest request) {
        this.pageNo = ServletRequestUtils.getIntParameter(request, "page", 1);
        this.fetchSize = ServletRequestUtils.getIntParameter(request, "fetchSize", 10);
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
    @SuppressWarnings("unchecked")
    public String toString() {
        StringBuffer clause = new StringBuffer();
        List<Object> l = this.getCondition();
        if (l != null) {
            Condition[] c1;
            // 循环读取条件对象
            int size = l.size();

            // 如果size > 1, 先构造第一个where子句
            if (size > 0) {
                c1 = (Condition[])l.get(0);

                clause.append(" where ");
                clause.append(toString(c1));
            }

            // 循环从1开始
            for (int i = 1; i < size; i++) {
                c1 = (Condition[])l.get(i);

                // 直接构造where条件
                clause.append(" and ");
                clause.append(toString(c1));
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

        log.debug("Sql generated: " + sql);

        return sql;
    }

    public String getFieldClause() {
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
            Condition[] c1 = (Condition[])obj;

            // 直接构造where条件
            clause.append(" and ");
            clause.append(toString(c1));
        }

        return clause.toString();
    }

    private String toString(Condition[] con) {
        StringBuffer clause = new StringBuffer("(");
        Condition condition = null;

        if (con.length > 0) {
            condition = con[0];

            clause.append(" ");
            clause.append(condition.getField());
            clause.append(" ");
            clause.append(condition.getRelation());
            clause.append(" ");
            if (condition.getValue() instanceof String){
                clause.append("'").append(condition.getValue()).append("'");
            }else{
                clause.append(condition.getValue());
            }
        }

        for (int i = 1; i < con.length; i++) {
            condition = con[i];

            clause.append(" or ");
            clause.append(condition.getField());
            clause.append(" ");
            clause.append(condition.getRelation());
            clause.append(" ");
            if (condition.getValue() instanceof String){
                clause.append("'").append(condition.getValue()).append("'");
            }else{
                clause.append(condition.getValue());
            }
        }

        clause.append(")");

        String sql = clause.toString();

        return sql;
    }

    public void addCondition(int position, Condition condition) {
        if (condition != null && this.condition != null && position >= 0 && this.condition.size() > position) {
            Condition[] cons = (Condition[])this.condition.get(position);
            if (cons != null) {
                Condition[] newCons = new Condition[cons.length + 1];
                System.arraycopy(cons, 0, newCons, 0, cons.length);
                newCons[cons.length] = condition;

                this.condition.remove(position);
                this.condition.add(newCons);
            }
        }
    }

    public void addOrder(int index, String order) {
        if (order != null && !"".equals(order)){
            this.order.add(index,order);
        }
    }
}