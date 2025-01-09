package com.example.engineerhelper.entity;

public class Authorization {
    private int aid;
    private int pid;
    private long startDateTime;  // 使用 long 存储时间戳
    private int time;
    private long outDateTime;    // 使用 long 存储时间戳
    private int cid;
    private String authorizationCode;

    // 构造函数
    public Authorization(int aid, int pid, long startDateTime, int time, long outDateTime, int cid, String authorizationCode) {
        this.aid = aid;
        this.pid = pid;
        this.startDateTime = startDateTime;
        this.time = time;
        this.outDateTime = outDateTime;
        this.cid = cid;
        this.authorizationCode = authorizationCode;
    }

    // Getter 和 Setter
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public long getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(long startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getOutDateTime() {
        return outDateTime;
    }

    public void setOutDateTime(long outDateTime) {
        this.outDateTime = outDateTime;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
