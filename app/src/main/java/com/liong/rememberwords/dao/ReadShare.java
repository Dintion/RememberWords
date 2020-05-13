package com.liong.rememberwords.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class ReadShare {
    private String username;
    private String password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public ReadShare(Context context) {
        this.sharedPreferences = context.getSharedPreferences("userLoginInfo", Context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    public String getUsername() {
        return  sharedPreferences.getString("username", "");

    }

    public void setUsername(String username) {
        this.editor.putString("username", username);
        this.editor.commit();
    }

    public String getPassword() {
        return  sharedPreferences.getString("pwd", "");

    }

    public void setPassword(String password) {
        this.editor.putString("pwd", password);
        this.editor.commit();
    }
}
