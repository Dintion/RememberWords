package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.UserDao;
import com.liong.rememberwords.dao.UserInfoDao;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private Button registerButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText phoneEdit;
    private SQLiteDatabase db;
    private UserDao userDao;
    private UserInfoDao userInfoDao;
    String username;
    String password;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        this.registerButton.setOnClickListener(this);
    }

    public void initView() {
        this.usernameEdit = findViewById(R.id.usernameRegisterText);
        this.passwordEdit = findViewById(R.id.passwordRegisterText);
        this.phoneEdit = findViewById(R.id.phoneText);
        this.registerButton = findViewById(R.id.registerRegisterButton);
        userDao = new UserDao(this);
        userInfoDao = new UserInfoDao(this);
    }

    @Override
    public void onClick(View v) {
        username = usernameEdit.getText().toString();
        password = passwordEdit.getText().toString();
        phone = phoneEdit.getText().toString();
        Log.i(TAG, "onClick: " + username + password + phone);
        String sql = "insert into user_tb(username,password,phone) values(?,?,?)";
        this.db = userDao.createDb();
        this.db.execSQL(sql, new String[]{username, password, phone});
        Intent intent = new Intent(RegisterActivity.this, CRUDActivity.class);
        userInfoDao.insertUserIdAndLogin(username);
        Toast.makeText(RegisterActivity.this, getString(R.string.registerSuccess), Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}
