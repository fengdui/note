package com.fengdui.oa.framework.web;

import com.xh.market.framework.constant.ConstantColumn;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-5 上午9:35:18
 * @desc PageInfo.java
 */
public class PageInfo {

	private static final int[] PAGE_SIZE_ARRAY = { 10, 20, 30, 60, 80, 100 };
	public static int DEFAULT_PAGE_SIZE = PAGE_SIZE_ARRAY[0];

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页显示记录数
	private int totalPage;// 总页数
	private int totalResult;// 总记录数
	private int currentPage = 1;// 当前页
	private int currentResult;// 当前记录起始索引
	private int currentSeq;// 当前起始序号

	private String orderBy = null;// 排序字段
	private String order = null;// 排序方向，升序还是降序
	private List<String> orderByList = null;// 排序字段附加
	private List<String> orderList = null;// 排序方向附加，升序还是降序

	private String pageSummar;// 分页总结条
	private String pageControl;// 分页控制条
	private String pageControl1;// 分页控制条1
	private String pageControl2;// 分页控制条2

	public PageInfo() {

	}

	public void initPage(int totalResult) {
		// 计算总记录数
		this.totalResult = totalResult;

		// 计算总页数
		totalPage = totalResult / pageSize;
		if (totalResult % pageSize > 0) {
			totalPage++;
		}
		if (totalResult == 0) {
			totalPage = 1;
		}

		// 计算当前页
		if (currentPage < 0) {
			currentPage = 1;
		} else if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		// 计算当前记录起始索引
		currentResult = (currentPage - 1) * pageSize;
		if (currentResult < 0) {
			currentResult = 0;
		}

		// 计算当前起始序号
		currentSeq = currentResult + 1;

		// 计算分页总结条
		StringBuffer pageSummarStr = new StringBuffer();
		pageSummarStr.append("<input type='hidden' id='currentPage' name='currentPage' value='" + currentPage + "'/>");
		pageSummarStr.append("<input type='hidden' id='pageOrderBy' name='orderBy' value='" + getOrderBy() + "'/>");
		pageSummarStr.append("<input type='hidden' id='pageOrder' name='order' value='" + getOrder() + "'/>");
		pageSummarStr.append("<select name='pageSize' style='width: 60px; height: 34px; padding: 6px;' onchange='jumpToPage(1)'>");
		for (int size : PAGE_SIZE_ARRAY) {
			pageSummarStr.append("<option value='" + size + "'" + (size == pageSize ? "selected='selected'" : "") + ">" + size + "</option>");
		}
		pageSummarStr.append("</select>");
		pageSummarStr.append("<span style='border-left: 1px solid #ccc; margin-left: 5px; padding: 5px 0px 8px 8px;'>共<strong style='padding: 0 5px;'>" + totalResult
				+ "</strong>条记录，共<strong style='padding: 0 5px;'>" + totalPage + "</strong>页</span>");
		pageSummar = pageSummarStr.toString();

		// 计算分页控制条
		StringBuffer pageControlStr = new StringBuffer();
		pageControlStr.append("<div class='dataTables_paginate'>");
		pageControlStr.append("<ul class='pagination'>");
		if (currentPage == 1) {
			pageControlStr.append("<li class='disabled'><a href='javascript:void(0);'>首页</a></li>");
			pageControlStr.append("<li class='disabled'><a href='javascript:void(0);'>上一页</a></li>");
		} else {
			pageControlStr.append("<li><a href='javascript:void(0);' onclick='jumpToPage(1)'>首页</a></li>");
			pageControlStr.append("<li><a href='javascript:void(0);' onclick='jumpToPage(" + (currentPage - 1) + ")'>上一页</a></li>");
		}
		pageControlStr.append("<li class='spinner spinner-right' style='display: inline-block; float: left;'>");
		pageControlStr.append("<div class='spinner-buttons btn-group' style='height: 32px;'>");
		pageControlStr.append("<button type='button' class='btn spinner-up purple' style='height: 16px;'>");
		pageControlStr.append("<i class='fa fa-angle-up'></i>");
		pageControlStr.append("</button>");
		pageControlStr.append("<button type='button' class='btn spinner-down purple' style='height: 16px;'>");
		pageControlStr.append("<i class='fa fa-angle-down'></i>");
		pageControlStr.append("</button>");
		pageControlStr.append("</div>");
		pageControlStr
				.append("<input type='text' class='spinner-input form-control' onkeydown=\"if(event.keyCode==13) jumpToPage(value)\" onkeyup=\"if(isNaN(value) || value.trim() == '' || value > "
						+ totalPage
						+ ") execCommand('undo');\" onafterpaste=\"if(isNaN(value) || value.trim() == '' || value > "
						+ totalPage
						+ ") execCommand('undo');\" style='width: 65px; height: 32px; padding-right: 25px; padding-top: 10px; border-right: none;' />");
		pageControlStr.append("</li>");
		if (currentPage == totalPage) {
			pageControlStr.append("<li class='disabled'><a href='javascript:void(0);'>下一页</a></li>");
			pageControlStr.append("<li class='disabled'><a href='javascript:void(0);'>尾页</a></li>");
		} else {
			pageControlStr.append("<li><a href='javascript:void(0);' onclick='jumpToPage(" + (currentPage + 1) + ")'>下一页</a></li>");
			pageControlStr.append("<li><a href='javascript:void(0);' onclick='jumpToPage(" + totalPage + ")'>尾页</a></li>");
		}
		pageControlStr.append("</ul>");
		pageControlStr.append("</div>");
		pageControl = pageControlStr.toString();
		if (pageControl.length() > 1000) {
			pageControl1 = pageControl.substring(0, 1000);
			pageControl2 = pageControl.substring(1000);
		} else {
			pageControl1 = pageControl;
		}
		pageControl = null;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public int getCurrentSeq() {
		return currentSeq;
	}

	public void setCurrentSeq(int currentSeq) {
		this.currentSeq = currentSeq;
	}

	public String getOrderBy() {
		if (null == orderBy) {
			return ConstantColumn.CREATE_TIME;
		}
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		if (null == order) {
			return ConstantColumn.ORDER_DESC;
		}
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<String> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<String> orderByList) {
		this.orderByList = orderByList;
	}

	public List<String> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<String> orderList) {
		this.orderList = orderList;
	}

	public String getPageSummar() {
		return pageSummar;
	}

	public void setPageSummar(String pageSummar) {
		this.pageSummar = pageSummar;
	}

	public String getPageControl() {
		return pageControl;
	}

	public void setPageControl(String pageControl) {
		this.pageControl = pageControl;
	}

	public String getPageControl1() {
		return pageControl1;
	}

	public void setPageControl1(String pageControl1) {
		this.pageControl1 = pageControl1;
	}

	public String getPageControl2() {
		return pageControl2;
	}

	public void setPageControl2(String pageControl2) {
		this.pageControl2 = pageControl2;
	}

}
