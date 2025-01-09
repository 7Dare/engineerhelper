package com.example.engineerhelper.entity;

public class Product {
    private int pid;
    private String name;
    private int cid;
    private long time;  // 使用 long 存储时间戳

    // 构造函数
    public Product(int pid, String name, int cid, long time) {
        this.pid = pid;
        this.name = name;
        this.cid = cid;
        this.time = time;
    }

    // Getter 和 Setter
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
