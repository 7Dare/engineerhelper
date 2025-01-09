package com.example.engineerhelper.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.engineerhelper.entity.Product;
import com.example.engineerhelper.helper.DBHelper;
import com.example.engineerhelper.utils.DatabaseContract;

public class ProductDAO {
    private DBHelper dbHelper;

    public ProductDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // 添加产品
    public long addProduct(String name, int cid, long time) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.PRODUCTS_NAME, name);
        values.put(DatabaseContract.PRODUCTS_CID, cid);
        values.put(DatabaseContract.PRODUCTS_TIME, time);
        long id = db.insert(DatabaseContract.TABLE_PRODUCTS, null, values);
        db.close();
        return id;
    }

    // 获取产品
    public Product getProduct(int pid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.TABLE_PRODUCTS,
                new String[]{DatabaseContract.PRODUCTS_ID, DatabaseContract.PRODUCTS_NAME, DatabaseContract.PRODUCTS_CID, DatabaseContract.PRODUCTS_TIME},
                DatabaseContract.PRODUCTS_ID + "=?", new String[]{String.valueOf(pid)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Product product = new Product(
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.PRODUCTS_ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseContract.PRODUCTS_NAME)),
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.PRODUCTS_CID)),
                cursor.getLong(cursor.getColumnIndex(DatabaseContract.PRODUCTS_TIME))
            );
            cursor.close();
            db.close();
            return product;
        }
        db.close();
        return null;
    }

    // 更新产品
    public int updateProduct(int pid, String name, int cid, long time) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.PRODUCTS_NAME, name);
        values.put(DatabaseContract.PRODUCTS_CID, cid);
        values.put(DatabaseContract.PRODUCTS_TIME, time);
        int rows = db.update(DatabaseContract.TABLE_PRODUCTS, values, DatabaseContract.PRODUCTS_ID + "=?", new String[]{String.valueOf(pid)});
        db.close();
        return rows;
    }

    // 删除产品
    public void deleteProduct(int pid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.TABLE_PRODUCTS, DatabaseContract.PRODUCTS_ID + "=?", new String[]{String.valueOf(pid)});
        db.close();
    }
}
