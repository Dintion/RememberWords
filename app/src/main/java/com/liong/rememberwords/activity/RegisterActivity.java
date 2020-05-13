package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liong.rememberwords.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private Button registerButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText phoneEdit;
    private SQLiteDatabase db;
    String username;
    String password;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        username = usernameEdit.getText().toString();
        password = passwordEdit.getText().toString();
        phone = phoneEdit.getText().toString();
        this.registerButton.setOnClickListener(this);
    }

    public void initView() {
        this.usernameEdit = findViewById(R.id.usernameRegisterText);
        this.passwordEdit = findViewById(R.id.passwordRegisterText);
        this.phoneEdit = findViewById(R.id.phoneText);
        this.registerButton = findViewById(R.id.registerRegisterButton);
    }

    @Override
    public void onClick(View v) {
        String sql = "insert into user_tb(username,password,phone) values(?,?,?)";
        this.db = createDb();
        this.db.execSQL(sql, new String[]{username, password, phone});
        Intent intent = new Intent(RegisterActivity.this, CRUDActivity.class);
//String sql_add_login_ifo="insert into user_info_tb(username,loginTime) values(?,?)";
        Toast.makeText(RegisterActivity.this, getString(R.string.registerSuccess), Toast.LENGTH_LONG).show();
//                db.execSQL(sql_add_login_ifo,new String[]{username,new Date().toString()});
        startActivity(intent);
    }
    public SQLiteDatabase createDb() {
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(this, "rememberwords.db", null, 1, null) {
            @Override
            public void onCreate(SQLiteDatabase db) {
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };
        return sqLiteOpenHelper.getWritableDatabase();
    }
}
