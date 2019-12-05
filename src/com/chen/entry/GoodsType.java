package com.chen.entry;

/**
 * @program: WebOnline-Thired
 * @description:
 * @author: chenliang
 * @date: 2019-11-22 01:39
 **/

public class GoodsType {
    private int id;
    private String typename;
    private int level;
    private int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
