package com.chen.entry;

/**
 * @program: Online
 * @description:
 * @author: chenliang
 * @date: 2019-12-04 21:05
 **/

public class GoodsVo extends Goods{

    private int page = 1;   //当前第几页
    private int rows = 3;   // 1页有多少条
    private int begin = 0;  //limit从哪里开始  这个值会变化

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }
}
