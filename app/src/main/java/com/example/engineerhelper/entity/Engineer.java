package com.example.engineerhelper.entity;

public class Engineer {
    private int eid;
    private String username;
    private String password;

    // 构造函数
    public Engineer(int eid, String username, String password) {
        this.eid = eid;
        this.username = username;
        this.password = password;
    }

    // Getter 和 Setter
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}