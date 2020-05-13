package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.UserDao;
import com.liong.rememberwords.dao.ReadShare;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button loginButton;
    private Button registerButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private SQLiteDatabase db;
    private ReadShare readShare;

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
        this.db = new UserDao(this).createDb();
        readShare = new ReadShare(this);
    }

    public boolean verify() {
        String username = this.usernameEdit.getText().toString();
        String password = this.passwordEdit.getText().toString();
        String sql = "select id,password from user_tb where username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        Log.i(TAG, "verify: " + username);
        String password2 = "";
        if ("".equals(password) || password == null) {
            return false;
        }
        while (cursor.moveToNext()) {
            String user_id = cursor.getString(0);
            password2 = cursor.getString(1);
            Log.i(TAG, "verify: " + user_id + password2);
            String sql1 = "insert into user_info_tb(user_id,loginTime) values(?,?)";
            db.execSQL(sql1, new String[]{user_id, new Date().toString()});
        }
        if (password.equals(password2)) {
            //将登录信息存入Share
            readShare.setUsername(username);
            readShare.setPassword(password);
            return true;
        }
        return false;
    }
}
