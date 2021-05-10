/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.gyr.studycommon.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页数
	 */
	private int currPage;
	/**
	 * 列表数据
	 */
	private List<?> list;

	private boolean first;
	private boolean last;

	public static final String ORDER_ASC = "ASC"; //升降序
	public static final String ORDER_DESC = "DESC"; //升降序


	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 * 逻辑分页
	 * @param list        列表数据
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(int pageSize, int currPage,List<?> list) {
		int begin = (currPage-1) * pageSize	;
		int end = begin + pageSize ;
		int totalCount = list.size();
		if(end>totalCount){
			end = totalCount;
		}
		//总页数
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
		List<?> result = null;
		if(list.isEmpty()){

		}else{
			result = list.subList(begin, end);
		}

		this.list = result;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.setCurrPage(currPage);
	}


	/**
	 * 分页
	 */
	public PageUtils(IPage<?> page) {
		this.list = page.getRecords();
		this.totalCount = (int)page.getTotal();
		this.pageSize = (int)page.getSize();
		this.totalPage = (int)page.getPages();
		this.setCurrPage((int)page.getCurrent());
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
		if(currPage==1){
			setFirst(true);
		}
		if(currPage==totalPage){
			setLast(true);
		}
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "PageUtils{" +
				"totalCount=" + totalCount +
				", pageSize=" + pageSize +
				", totalPage=" + totalPage +
				", currPage=" + currPage +
				", list=" + list +
				", first=" + first +
				", last=" + last +
				'}';
	}
}
