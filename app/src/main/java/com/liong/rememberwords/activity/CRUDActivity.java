package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.ReadShare;
import com.liong.rememberwords.dao.UserInfoDao;
import com.liong.rememberwords.dao.WordsDao;

public class CRUDActivity extends AppCompatActivity {
    private static final String TAG = "CRUDActivity";
    private EditText wordEdit;
    private EditText descEdit;
    private SQLiteDatabase db;
    private WordsDao wordsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d);
        wordsDao = new WordsDao(this);
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this, "rememberwords.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
            }
            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            }
        };
        db = helper.getReadableDatabase();
        wordEdit = findViewById(R.id.word_edt);
        descEdit = findViewById(R.id.desc_edt);
    }

    public void operate(View v) {
        String word = wordEdit.getText().toString();
        String chineseWord = descEdit.getText().toString();
        switch (v.getId()) {
            case R.id.insert_btn:
                if (!("".equals(word) || "".equals(chineseWord))) {
                    String sql = " insert into word(englishWord,chineseWord) values(?,?) ";
                    db.execSQL(sql, new String[]{word, chineseWord});
                    Toast.makeText(CRUDActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CRUDActivity.this, "请输入要添加的内容", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.select_btn:
                Intent intent = new Intent(CRUDActivity.this, WordsActivity.class);
                word = word == null ? "" : word;
                intent.putExtra("word", word);
                startActivity(intent);
                break;
            case R.id.delete_btn:
                Intent intent2 = new Intent(CRUDActivity.this, WordsActivity.class);
                intent2.putExtra("isRember", 1);
                startActivity(intent2);
                break;
            case R.id.update_btn:
                Intent intent3 = new Intent(CRUDActivity.this, WordsActivity.class);
                intent3.putExtra("isRember", 0);
                startActivity(intent3);

                break;
            case R.id.logoutButton:
                Intent intentToMain = new Intent(CRUDActivity.this, MainActivity.class);
                ReadShare readShare = new ReadShare(this);
                String userId = readShare.getUserId();
                new UserInfoDao(this).insertUserIdAndLogout(userId);
                Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
                startActivity(intentToMain);
                break;
            default:
                Toast.makeText(this, "别乱点", Toast.LENGTH_SHORT).show();

        }
    }
}
