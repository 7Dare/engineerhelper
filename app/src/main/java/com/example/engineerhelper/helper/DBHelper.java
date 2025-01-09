package com.example.engineerhelper.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.engineerhelper.utils.DatabaseContract;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "engineer_helper.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建工程师表
        db.execSQL(
            "CREATE TABLE " + DatabaseContract.TABLE_ENGINEER + " (" +
            DatabaseContract.ENGINEER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.ENGINEER_USERNAME + " TEXT NOT NULL, " +
            DatabaseContract.ENGINEER_PASSWORD + " TEXT NOT NULL)"
        );

        // 创建客户表
        db.execSQL(
            "CREATE TABLE " + DatabaseContract.TABLE_CUSTOMER + " (" +
            DatabaseContract.CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.CUSTOMER_NAME + " TEXT NOT NULL, " +
            DatabaseContract.CUSTOMER_ADDRESS + " TEXT NOT NULL, " +
            DatabaseContract.CUSTOMER_PHONE + " TEXT, " +
            DatabaseContract.CUSTOMER_EMAIL + " TEXT)"
        );

        // 创建产品表
        db.execSQL(
            "CREATE TABLE " + DatabaseContract.TABLE_PRODUCTS + " (" +
            DatabaseContract.PRODUCTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.PRODUCTS_NAME + " TEXT NOT NULL, " +
            DatabaseContract.PRODUCTS_CID + " INTEGER NOT NULL, " +
            DatabaseContract.PRODUCTS_TIME + " TIMESTAMP NOT NULL, " +
            "FOREIGN KEY(" + DatabaseContract.PRODUCTS_CID + ") REFERENCES " +
            DatabaseContract.TABLE_CUSTOMER + "(" + DatabaseContract.CUSTOMER_ID + "))"
        );

        // 创建授权表
        db.execSQL(
            "CREATE TABLE " + DatabaseContract.TABLE_AUTHORIZATION + " (" +
            DatabaseContract.AUTHORIZATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.AUTHORIZATION_PID + " INTEGER NOT NULL, " +
            DatabaseContract.AUTHORIZATION_START_TIME + " TIMESTAMP NOT NULL, " +
            DatabaseContract.AUTHORIZATION_DURATION + " INTEGER NOT NULL, " +
            DatabaseContract.AUTHORIZATION_END_TIME + " TIMESTAMP NOT NULL, " +
            DatabaseContract.AUTHORIZATION_CID + " INTEGER NOT NULL, " +
            DatabaseContract.AUTHORIZATION_CODE + " TEXT, " +
            "FOREIGN KEY(" + DatabaseContract.AUTHORIZATION_PID + ") REFERENCES " +
            DatabaseContract.TABLE_PRODUCTS + "(" + DatabaseContract.PRODUCTS_ID + "), " +
            "FOREIGN KEY(" + DatabaseContract.AUTHORIZATION_CID + ") REFERENCES " +
            DatabaseContract.TABLE_CUSTOMER + "(" + DatabaseContract.CUSTOMER_ID + "))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除现有表
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_ENGINEER);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_AUTHORIZATION);
        // 重新创建表
        onCreate(db);
    }
}
