package com.example.engineerhelper.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.engineerhelper.entity.Customer;
import com.example.engineerhelper.helper.DBHelper;
import com.example.engineerhelper.utils.DatabaseContract;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private DBHelper dbHelper;

    public CustomerDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // 添加客户
    public long addCustomer(String name, String address, String phone, String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CUSTOMER_NAME, name);
        values.put(DatabaseContract.CUSTOMER_ADDRESS, address);
        values.put(DatabaseContract.CUSTOMER_PHONE, phone);
        values.put(DatabaseContract.CUSTOMER_EMAIL, email);
        long id = db.insert(DatabaseContract.TABLE_CUSTOMER, null, values);
        db.close();
        return id;
    }

    // 获取单个客户
    public Customer getCustomer(int cid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.TABLE_CUSTOMER,
                new String[]{DatabaseContract.CUSTOMER_ID, DatabaseContract.CUSTOMER_NAME, DatabaseContract.CUSTOMER_ADDRESS, DatabaseContract.CUSTOMER_PHONE, DatabaseContract.CUSTOMER_EMAIL},
                DatabaseContract.CUSTOMER_ID + "=?", new String[]{String.valueOf(cid)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Customer customer = new Customer(
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.CUSTOMER_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_PHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_EMAIL))
            );
            cursor.close();
            db.close();
            return customer;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    // 更新客户
    public int updateCustomer(int cid, String name, String address, String phone, String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CUSTOMER_NAME, name);
        values.put(DatabaseContract.CUSTOMER_ADDRESS, address);
        values.put(DatabaseContract.CUSTOMER_PHONE, phone);
        values.put(DatabaseContract.CUSTOMER_EMAIL, email);
        int rows = db.update(DatabaseContract.TABLE_CUSTOMER, values, DatabaseContract.CUSTOMER_ID + "=?", new String[]{String.valueOf(cid)});
        db.close();
        return rows;
    }

    // 删除客户
    public void deleteCustomer(int cid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.TABLE_CUSTOMER, DatabaseContract.CUSTOMER_ID + "=?", new String[]{String.valueOf(cid)});
        db.close();
    }

    // 获取所有客户
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseContract.TABLE_CUSTOMER,
                new String[]{
                        DatabaseContract.CUSTOMER_ID,
                        DatabaseContract.CUSTOMER_NAME,
                        DatabaseContract.CUSTOMER_ADDRESS,
                        DatabaseContract.CUSTOMER_PHONE,
                        DatabaseContract.CUSTOMER_EMAIL
                },
                null, // selection
                null, // selectionArgs
                null, // groupBy
                null, // having
                DatabaseContract.CUSTOMER_NAME + " ASC" // orderBy
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") Customer customer = new Customer(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.CUSTOMER_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_ADDRESS)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_PHONE)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_EMAIL))
                        );
                        customers.add(customer);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return customers;
    }

    // 搜索客户
    public List<Customer> searchCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DatabaseContract.CUSTOMER_NAME + " LIKE ? OR " +
                DatabaseContract.CUSTOMER_ADDRESS + " LIKE ? OR " +
                DatabaseContract.CUSTOMER_PHONE + " LIKE ? OR " +
                DatabaseContract.CUSTOMER_EMAIL + " LIKE ?";
        String wildcardQuery = "%" + query + "%";
        String[] selectionArgs = {wildcardQuery, wildcardQuery, wildcardQuery, wildcardQuery};

        Cursor cursor = db.query(
                DatabaseContract.TABLE_CUSTOMER,
                new String[]{
                        DatabaseContract.CUSTOMER_ID,
                        DatabaseContract.CUSTOMER_NAME,
                        DatabaseContract.CUSTOMER_ADDRESS,
                        DatabaseContract.CUSTOMER_PHONE,
                        DatabaseContract.CUSTOMER_EMAIL
                },
                selection,
                selectionArgs,
                null,
                null,
                DatabaseContract.CUSTOMER_NAME + " ASC"
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") Customer customer = new Customer(
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.CUSTOMER_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_ADDRESS)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_PHONE)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CUSTOMER_EMAIL))
                        );
                        customers.add(customer);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return customers;
    }
}
