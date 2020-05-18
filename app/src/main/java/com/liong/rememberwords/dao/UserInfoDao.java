package com.liong.rememberwords.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfoDao {
    private SQLiteDatabase database;
    private Context context;
    private UserDao userDao;
    private SimpleDateFormat dateFormat;
    private static final String TAG = "UserInfoDao";
    @SuppressLint("SimpleDateFormat")
    public UserInfoDao(Context context) {
        this.context = context;
        this.database = Dao.getDatabase(context);
        userDao = new UserDao(context);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void insertUserIdAndLogin(String userId) {
        String sql_add_login_ifo = "insert into user_info_tb(user_id,loginTime) values(?,?)";
        this.database.execSQL(sql_add_login_ifo,new String[]{userId,dateFormat.format(new Date())});
    }
    public void insertUserIdAndLogout(String userId) {
        String sql_add_login_ifo = "insert into user_info_tb(user_id,logout) values(?,?)";
        this.database.execSQL(sql_add_login_ifo,new String[]{userId,dateFormat.format(new Date())});
    }

    public String getUserLastLoginTime(String user_id) {
        Log.i(TAG, "getUserLastLoginTime: "+user_id);
        String sql_login_ifo = "select * from user_info_tb where user_id=? order by id desc";
        @SuppressLint("Recycle") Cursor cursor = this.database.rawQuery(sql_login_ifo,new String[]{user_id});
        String date = "";
        Log.i(TAG, "getUserLastLoginTime: "+cursor.moveToNext());;
        if (cursor.moveToNext()) {
            date = cursor.getString(2);
            Log.i(TAG, "getUserLastLoginTime: "+date);
        }
        return date;
    }
}
