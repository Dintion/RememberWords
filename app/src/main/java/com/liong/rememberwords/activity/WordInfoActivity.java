package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.Dao;
import com.liong.rememberwords.dao.ReadShare;
import com.liong.rememberwords.dao.UserDao;
import com.liong.rememberwords.dao.WordsDao;
import com.liong.rememberwords.domain.Word;
import com.liong.rememberwords.service.SoundIntentService;

import java.util.Date;

public class WordInfoActivity extends AppCompatActivity {
    private TextView EnglishWordEd;
    private TextView paEd;
    private TextView ChineseWordEd;
    private TextView e1Ed;
    private TextView c1Ed;
    private TextView e2Ed;
    private TextView c2Ed;
    private static final String TAG = "WordInfoActivity";
    private UserDao userDao;
    private ReadShare readShare;
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);
        initView();
        String wordStr = getIntent().getStringExtra("word");
        WordsDao wordsDao = new WordsDao(this);
        word = wordsDao.selectWordByEnglish(wordStr);
        EnglishWordEd.setText(word.getEnglishWord());
        paEd.setText("/" + word.getPa() + "/");
        ChineseWordEd.setText(word.getChineseWord());
        e1Ed.setText(word.getEnglishInstance1());
        c1Ed.setText(word.getChineseInstance1());
        e2Ed.setText(word.getEnglishInstance2());
        c2Ed.setText(word.getChineseInstance2());
    }


    private void initView() {
        EnglishWordEd = findViewById(R.id.EnglishWord);
        paEd = findViewById(R.id.pa);
        ChineseWordEd = findViewById(R.id.trans);
        e1Ed = findViewById(R.id.e1);
        c1Ed = findViewById(R.id.c1);
        e2Ed = findViewById(R.id.e2);
        c2Ed = findViewById(R.id.c2);
    }

    public void backed(View v) {
        Log.i(TAG, "onClick: 我记住了");
        SQLiteDatabase database = Dao.getDatabase(WordInfoActivity.this);
        String sql = "insert into user_word_tb(user_id,word,time) values(?,?,?)";
        new WordsDao(this).updateIsRember(word.getEnglishWord());
        database.execSQL(sql, new String[]{new ReadShare(this).getUserId(), word.getEnglishWord(), new Date().toString()});
    }

    public void sound(View view) {
        Intent intent = new Intent(WordInfoActivity.this, SoundIntentService.class);
        String actionMusic = SoundIntentService.ACTION_MUSIC;
        intent.setAction(actionMusic);
        intent.putExtra("soundPath", word.getPron());
        startService(intent);
    }
}
