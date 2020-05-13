package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.WordsDao;
import com.liong.rememberwords.domain.Word;

public class WordInfoActivity extends AppCompatActivity {
    private TextView EnglishWordEd;
    private TextView paEd;
    private TextView ChineseWordEd;
    private TextView e1Ed;
    private TextView c1Ed;
    private TextView e2Ed;
    private TextView c2Ed;
    private Button easyButton;
    private static final String TAG = "WordInfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);
        initView();
        String wordStr = getIntent().getStringExtra("word");
        WordsDao wordsDao = new WordsDao(this);
        Word word = wordsDao.selectWordByEnglish(wordStr);
        EnglishWordEd.setText(word.getEnglishWord());
        paEd.setText("/"+word.getPa()+"/");
        ChineseWordEd.setText(word.getChineseWord());
        e1Ed.setText(word.getEnglishInstance1());
        c1Ed.setText(word.getChineseInstance1());
        e2Ed.setText(word.getEnglishInstance2());
        c2Ed.setText(word.getChineseInstance2());
        Log.i(TAG, "onCreate: "+word);
    }

    private void initView() {
        EnglishWordEd = findViewById(R.id.EnglishWord);
        paEd = findViewById(R.id.pa);
        ChineseWordEd = findViewById(R.id.trans);
        e1Ed = findViewById(R.id.e1);
        c1Ed = findViewById(R.id.c1);
        e2Ed = findViewById(R.id.e2);
        c2Ed = findViewById(R.id.c2);
        easyButton = findViewById(R.id.easy);
    }
}
