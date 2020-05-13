package com.liong.rememberwords.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liong.rememberwords.R;

public class CRUDActivity extends AppCompatActivity {
    private static final String TAG = "CRUDActivity";
    private EditText wordEdit;
    private EditText descEdit;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d);
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
                String sql = " insert into word(englishWord,chineseWord) values(?,?) ";
                db.execSQL(sql, new String[]{word, chineseWord});
                Toast.makeText(CRUDActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.select_btn:
                Intent intent = new Intent(CRUDActivity.this, WordsActivity.class);
                word = word == null ? "" : word;
                intent.putExtra("word", word);

                startActivity(intent);
                break;
            case R.id.delete_btn:
                String delSql = "delete from word where englishWord ='" + word + "'";
                db.execSQL(delSql);
                Toast.makeText(this, "删除" + word + "成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.update_btn:
                String updSql = "update word set chineseWord=? where englishWord ='" + word + "'";
                db.execSQL(updSql, new String[]{chineseWord});
                Toast.makeText(this, "修改" + word + "的解释为" + chineseWord, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "别乱点", Toast.LENGTH_LONG).show();

        }
    }
}
