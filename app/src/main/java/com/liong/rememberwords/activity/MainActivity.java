package com.liong.rememberwords.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.ReadShare;
import com.liong.rememberwords.dao.UserDao;
import com.liong.rememberwords.dao.UserInfoDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button loginButton;
    private Button registerButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private SQLiteDatabase db;
    private ReadShare readShare;
    private String user_id;
    private UserInfoDao userInfoDao;
    private String userLastLoginTime;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        loadDb();
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
        ReadShare readShare = new ReadShare(this);
        if (!readShare.getUsername().equals("")) {
            this.usernameEdit.setText(readShare.getUsername());
            this.passwordEdit.setText(readShare.getPassword());
        }
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean verify = verify();
                if (verify) {
                    Toast.makeText(MainActivity.this, "登陆成功，上次登录时间："+userLastLoginTime, Toast.LENGTH_SHORT).show();
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
        this.db = new UserDao(this).createDb();
        readShare = new ReadShare(this);
        userInfoDao = new UserInfoDao(this);
    }

    public boolean verify() {
        String username = this.usernameEdit.getText().toString();
        String password = this.passwordEdit.getText().toString();
        String sql = "select id,password from user_tb where username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        String password2 = "";
        if ("".equals(password) || password == null) {
            return false;
        }
        while (cursor.moveToNext()) {
            user_id = cursor.getString(0);
            password2 = cursor.getString(1);
        }
        if (password.equals(password2)) {
            Log.i(TAG, "用户id" + user_id);
            userLastLoginTime = userInfoDao.getUserLastLoginTime(user_id);
            userInfoDao.insertUserIdAndLogin(user_id);
            //将登录信息存入Share
            SharedPreferences.Editor editor = getSharedPreferences("userLoginInfo", MODE_PRIVATE).edit();
            ReadShare readShare = new ReadShare(this);
            readShare.setUserId(user_id);
            readShare.setUsername(username);
            readShare.setPassword(password);
            return true;
        }
        return false;
    }

    public void loadDb() {
        String DB_PATH = "/data/data/com.liong.rememberwords/databases/";
        String DB_NAME = "rememberwords.db";     //数据库路径及名称

        if ((new File(DB_PATH + DB_NAME).exists() == false)) {
            File f = new File(DB_PATH);
            // 检查数据库路径文件夹是否存在，不存在的话就建立
            if (!f.exists()) {
                f.mkdir();
            }
            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[1024 * 10];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }  //  将assets中的数据库文件复制到手机中
        };

    }
}
