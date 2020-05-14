package com.liong.rememberwords.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dao {
    public static SQLiteDatabase getDatabase(Context context) {
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(context, "rememberwords.db", null, 1, null) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                /**
                 * 创建对应的数据库
                 */
                String sql_create_user = "create table user_tb (id integer primary key autoincrement," +
                        "username varchar(20)," +
                        "password varchar(60)," +
                        "phone varchar(20))";
                String sql_create_user_login_info = "create table user_info_tb (id integer primary key autoincrement," +
                        "user_id varchar(120)," +
                        "loginTime Date," +
                        "logout Date)";
                String sql_create_user_words_tb = "create table user_word_tb (id integer primary key autoincrement," +
                        "user_id varchar(120)," +
                        "word varchar(512)," +
                        "time Date)";
                String sql_create_words = "CREATE TABLE word (" +
                        "  `englishWord` varchar(512) NOT NULL," +
                        "  `pa` varchar(512) DEFAULT NULL," +
                        "  `chineseWord` varchar(512) DEFAULT NULL," +
                        "  `englishInstance1` varchar(512) DEFAULT NULL," +
                        "  `chineseInstance1` varchar(512) DEFAULT NULL," +
                        "  `englishInstance2` varchar(512) DEFAULT NULL," +
                        "  `chineseInstance2` varchar(512) DEFAULT NULL," +
                        "  `collect` int(4) DEFAULT NULL," +
                        "  `pron` varchar(512) DEFAULT NULL," +
                        "isrember int(1) DEFAULT 0," +
                        "  PRIMARY KEY (`englishWord`)" +
                        ")";
                db.execSQL(sql_create_user);
                db.execSQL(sql_create_user_login_info);
                db.execSQL(sql_create_user_words_tb);
                db.execSQL(sql_create_words);
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        return sqLiteOpenHelper.getWritableDatabase();
    }
}
