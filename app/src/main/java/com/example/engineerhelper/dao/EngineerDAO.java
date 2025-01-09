package com.example.engineerhelper.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.engineerhelper.entity.Engineer;
import com.example.engineerhelper.helper.DBHelper;
import com.example.engineerhelper.utils.DatabaseContract;

public class EngineerDAO {
    private DBHelper dbHelper;

    public EngineerDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // 添加工程师
    public long addEngineer(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ENGINEER_USERNAME, username);
        values.put(DatabaseContract.ENGINEER_PASSWORD, password);
        long id = db.insert(DatabaseContract.TABLE_ENGINEER, null, values);
        db.close();
        return id;
    }

    // 获取工程师
    public Engineer getEngineer(int eid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.TABLE_ENGINEER,
                new String[]{DatabaseContract.ENGINEER_ID, DatabaseContract.ENGINEER_USERNAME, DatabaseContract.ENGINEER_PASSWORD},
                DatabaseContract.ENGINEER_ID + "=?", new String[]{String.valueOf(eid)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Engineer engineer = new Engineer(
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.ENGINEER_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ENGINEER_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ENGINEER_PASSWORD))
            );
            cursor.close();
            db.close();
            return engineer;
        }
        db.close();
        return null;
    }

    // 更新工程师
    public int updateEngineer(int eid, String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ENGINEER_USERNAME, username);
        values.put(DatabaseContract.ENGINEER_PASSWORD, password);
        int rows = db.update(DatabaseContract.TABLE_ENGINEER, values, DatabaseContract.ENGINEER_ID + "=?", new String[]{String.valueOf(eid)});
        db.close();
        return rows;
    }

    // 删除工程师
    public void deleteEngineer(int eid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.TABLE_ENGINEER, DatabaseContract.ENGINEER_ID + "=?", new String[]{String.valueOf(eid)});
        db.close();
    }
}