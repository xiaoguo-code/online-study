package com.gyr.studycommon.view;

import java.io.Serializable;
import java.util.List;

public class PageView<T> implements Serializable {
    /****输入参数****/
    private int pageSize;//每页记录数
    private int pageIndex;//第几页

    private String orderColumn; //排序字段
    private String orderOption; //升降序

    /****End:输入参数****/

    /****输出参数****/
    private List<T> result;//查询结果集
    private long recordCount;//总记录数
    private int pageCount;//总页数
    /****End:输出参数****/

    public static final String ORDER_ASC = "ASC"; //升降序
    public static final String ORDER_DESC = "DESC"; //升降序


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getOrderOption() {
        return orderOption;
    }

    public void setOrderOption(String orderOption) {
        this.orderOption = orderOption;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    @Override
    public String toString() {
        return "PageView{" +
                "pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", orderColumn='" + orderColumn + '\'' +
                ", orderOption='" + orderOption + '\'' +
                ", result=" + result +
                ", recordCount=" + recordCount +
                ", pageCount=" + pageCount +
                '}';
    }


}
