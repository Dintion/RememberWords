package com.liong.rememberwords.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class ReadShare {
    private String userId;
    private String username;
    private String password;
    private String loginTime;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public ReadShare(Context context) {
        this.sharedPreferences = context.getSharedPreferences("userLoginInfo", Context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    public String getLoginTime() {
        return  sharedPreferences.getString("loginTime", "");

    }

    public void setLoginTime(String loginTime) {
        this.editor.putString("loginTime", userId);
        this.editor.commit();
    }

    public String getUsername() {
        return  sharedPreferences.getString("username", "");

    }

    public String getUserId() {
        return sharedPreferences.getString("id","");
    }

    public void setUserId(String userId) {
        this.editor.putString("id", userId);
        this.editor.commit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
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
