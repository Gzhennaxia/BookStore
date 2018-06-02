package com.cskaoyan.utils;

import java.util.List;

public class Page<T> {

    //分类页面每页显示4条记录
    public static final int CATEGORY_PER_PAGE_NUM = 4;

    //系统用户页面每页显示2条记录
    public static final int ADMIN_PER_PAGE_NUM = 2;

    //商品列表页面每页显示6条记录
    public static final int PRODUCT_PER_PAGE_NUM = 6;

    //假设hot商品每页显示6条记录
    public static final int HOTPRODUCT_PER_PAGE_NUM = 6;

    //假设hot商品为库存最少的10条记录
    public static final int HOTPRODUCT_TOTAL_NUM = 10;

    //总记录数
    private int totalRecordsNum;

    //当前位于第几页
    private int currentPageNum;

    //总页数
    private int totalPageNum;

    //上一页
    private int prevPageNum;

    //下一页
    private int nextPageNum;

    //该页的记录
    private List<T> records;

    public Page(int currentPageNum, int totalRecordsNum, int perPageNum) {

        //总记录数
        this.totalRecordsNum = totalRecordsNum;

        //当前位于第几页
        this.currentPageNum = currentPageNum;

        //总页数
        this.totalPageNum = totalRecordsNum % perPageNum == 0 ?
                totalRecordsNum / perPageNum : totalRecordsNum / perPageNum + 1;

        //上一页
        this.prevPageNum = currentPageNum - 1 > 0 ? currentPageNum - 1 : 1;

        //下一页
        this.nextPageNum = currentPageNum + 1 < totalPageNum ? currentPageNum + 1 : totalPageNum;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPrevPageNum() {
        return prevPageNum;
    }

    public void setPrevPageNum(int prevPageNum) {
        this.prevPageNum = prevPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalRecordsNum=" + totalRecordsNum +
                ", currentPageNum=" + currentPageNum +
                ", totalPageNum=" + totalPageNum +
                ", prevPageNum=" + prevPageNum +
                ", nextPageNum=" + nextPageNum +
                '}';
    }
}
