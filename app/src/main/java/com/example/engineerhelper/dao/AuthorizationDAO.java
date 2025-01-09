package com.example.engineerhelper.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.engineerhelper.entity.Authorization;
import com.example.engineerhelper.helper.DBHelper;
import com.example.engineerhelper.utils.DatabaseContract;
//ryh到此一游
public class AuthorizationDAO {
    private DBHelper dbHelper;

    public AuthorizationDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // 添加授权
    public long addAuthorization(int pid, long startTime, int duration, long endTime, int cid, String authorizationCode) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AUTHORIZATION_PID, pid);
        values.put(DatabaseContract.AUTHORIZATION_START_TIME, startTime);
        values.put(DatabaseContract.AUTHORIZATION_DURATION, duration);
        values.put(DatabaseContract.AUTHORIZATION_END_TIME, endTime);
        values.put(DatabaseContract.AUTHORIZATION_CID, cid);
        values.put(DatabaseContract.AUTHORIZATION_CODE, authorizationCode);
        long id = db.insert(DatabaseContract.TABLE_AUTHORIZATION, null, values);
        db.close();
        return id;
    }

    // 获取授权
    public Authorization getAuthorization(int aid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.TABLE_AUTHORIZATION,
                new String[]{DatabaseContract.AUTHORIZATION_ID, DatabaseContract.AUTHORIZATION_PID, DatabaseContract.AUTHORIZATION_START_TIME,
                             DatabaseContract.AUTHORIZATION_DURATION, DatabaseContract.AUTHORIZATION_END_TIME,
                             DatabaseContract.AUTHORIZATION_CID, DatabaseContract.AUTHORIZATION_CODE},
                DatabaseContract.AUTHORIZATION_ID + "=?", new String[]{String.valueOf(aid)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Authorization authorization = new Authorization(
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AUTHORIZATION_ID)),
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AUTHORIZATION_PID)),
                cursor.getLong(cursor.getColumnIndex(DatabaseContract.AUTHORIZATION_START_TIME)),
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AUTHORIZATION_DURATION)),
                cursor.getLong(cursor.getColumnIndex(DatabaseContract.AUTHORIZATION_END_TIME)),
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.AUTHORIZATION_CID)),
                cursor.getString(cursor.getColumnIndex(DatabaseContract.AUTHORIZATION_CODE))
            );
            cursor.close();
            db.close();
            return authorization;
        }
        db.close();
        return null;
    }

    // 更新授权
    public int updateAuthorization(int aid, int pid, long startTime, int duration, long endTime, int cid, String authorizationCode) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AUTHORIZATION_PID, pid);
        values.put(DatabaseContract.AUTHORIZATION_START_TIME, startTime);
        values.put(DatabaseContract.AUTHORIZATION_DURATION, duration);
        values.put(DatabaseContract.AUTHORIZATION_END_TIME, endTime);
        values.put(DatabaseContract.AUTHORIZATION_CID, cid);
        values.put(DatabaseContract.AUTHORIZATION_CODE, authorizationCode);
        int rows = db.update(DatabaseContract.TABLE_AUTHORIZATION, values, DatabaseContract.AUTHORIZATION_ID + "=?", new String[]{String.valueOf(aid)});
        db.close();
        return rows;
    }

    // 删除授权
    public void deleteAuthorization(int aid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.TABLE_AUTHORIZATION, DatabaseContract.AUTHORIZATION_ID + "=?", new String[]{String.valueOf(aid)});
        db.close();
    }
}
