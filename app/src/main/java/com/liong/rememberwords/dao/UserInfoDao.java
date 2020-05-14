package com.liong.rememberwords.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class UserInfoDao {
    private SQLiteDatabase database;
    private Context context;
    private UserDao userDao;
    public UserInfoDao(Context context) {
        this.context = context;
        this.database = Dao.getDatabase(context);
        userDao = new UserDao(context);
    }

    public void insertUserIdAndLogin(String userId) {
        String sql_add_login_ifo = "insert into user_info_tb(user_id,loginTime) values(?,?)";
        this.database.execSQL(sql_add_login_ifo,new String[]{userId,new Date().toString()});
    }
    public void insertUserIdAndLogout(String userId) {
        String sql_add_login_ifo = "insert into user_info_tb(user_id,logout) values(?,?)";
        this.database.execSQL(sql_add_login_ifo,new String[]{userId,new Date().toString()});
    }
}
