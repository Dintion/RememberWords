package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liong.rememberwords.R;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private SQLiteDatabase db;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        this.db = createDb();
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean verify = verify();
                if (verify) {
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, CRUDActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initView() {
        this.loginButton = findViewById(R.id.loginButton);
        this.registerButton = findViewById(R.id.registerButton);
        this.passwordEdit = findViewById(R.id.passwordText);
        this.usernameEdit = findViewById(R.id.usernameText);
    }
    public boolean verify() {
        String username = this.usernameEdit.toString();
        String password = this.passwordEdit.toString();
        SQLiteDatabase db = createDb();
        String sql = "select id,password from user_tb where username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        String password2="";
        if (cursor.moveToNext()){
            String user_id = cursor.getString(0);
            password2 = cursor.getString(1);
            String sql1 = "insert into user_info_tb(user_id,loginTime) values(?,?)";
            db.execSQL(sql1,new String[]{user_id,new Date().toString()});
        }
        return password==password2;
    }
    public SQLiteDatabase createDb() {
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(this, "rememberwords.db", null, 1, null) {
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
                String sql_create_user_words_tb = "create table user_word_tb (id integer primary key autoincrement,user_id varchar(120)," +
                        "words varchar(512))";
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
