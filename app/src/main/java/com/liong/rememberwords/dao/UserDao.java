package com.liong.rememberwords.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
    private SQLiteDatabase database;
    private Context context;
    public UserDao(Context context) {
        this.context = context;
        this.database = Dao.getDatabase(context);
    }
    public SQLiteDatabase createDb() {
        return this.database;
    }
   public String selectUserIdByUsername(String username) {
        String sql3 = "select id from user_tb where username=?";
        Cursor cursor = this.database.rawQuery(sql3, new String[]{username});
        String id = "-1";
        if (cursor.moveToNext()) {
            id = "" + cursor.getInt(0);
        }
        return id;
    }

}
