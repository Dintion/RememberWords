package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liong.rememberwords.R;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText phoneEdit;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        final String username = usernameEdit.toString();
        final String password = passwordEdit.toString();
        final String phone = phoneEdit.toString();

        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql="insert into word_tb(enligshWord,chineseWord) values(?,?)";
                db.execSQL(sql,new String[]{username,password});
            }
        });
    }

    public void initView() {
        this.usernameEdit = findViewById(R.id.usernameRegisterText);
        this.passwordEdit = findViewById(R.id.passwordRegisterText);
        this.phoneEdit = findViewById(R.id.phoneText);
        this.registerButton = findViewById(R.id.registerRegisterButton);
    }
}
