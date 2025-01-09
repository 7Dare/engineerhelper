package com.example.engineerhelper.entity;

public class Customer {
    private int cid;
    private String name;
    private String address;
    private String phone;
    private String email;

    // 构造函数
    public Customer(int cid, String name, String address, String phone, String email) {
        this.cid = cid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getter 和 Setter
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
