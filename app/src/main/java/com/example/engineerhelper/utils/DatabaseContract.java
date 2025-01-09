package com.example.engineerhelper.utils;

// 用于存储表名和列名
public final class DatabaseContract {
    private DatabaseContract() {} // 私有构造函数防止实例化

    // 表名
    public static final String TABLE_ENGINEER = "engineer";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_PRODUCTS = "product";
    public static final String TABLE_AUTHORIZATION = "authorization";

    // Engineer 表的列名
    public static final String ENGINEER_ID = "eid";
    public static final String ENGINEER_USERNAME = "username";
    public static final String ENGINEER_PASSWORD = "password";

    // Customer 表的列名
    public static final String CUSTOMER_ID = "cid";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_ADDRESS = "address";
    public static final String CUSTOMER_PHONE = "phone";
    public static final String CUSTOMER_EMAIL = "email";

    // Products 表的列名
    public static final String PRODUCTS_ID = "pid";
    public static final String PRODUCTS_NAME = "name";
    public static final String PRODUCTS_CID = "cid";
    public static final String PRODUCTS_TIME = "time";

    // Authorizations 表的列名
    public static final String AUTHORIZATION_ID = "aid";
    public static final String AUTHORIZATION_PID = "pid";
    public static final String AUTHORIZATION_START_TIME = "start_date_time";
    public static final String AUTHORIZATION_DURATION = "time";
    public static final String AUTHORIZATION_END_TIME = "out_date_time";
    public static final String AUTHORIZATION_CID = "cid";
    public static final String AUTHORIZATION_CODE = "authorization_code";
}
