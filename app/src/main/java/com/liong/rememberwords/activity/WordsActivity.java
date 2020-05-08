package com.liong.rememberwords.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liong.rememberwords.R;
import com.liong.rememberwords.domain.Word;

import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {
    private static final String TAG = "WordsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        ListView wordListView = findViewById(R.id.wordsList);
        ArrayList<Word> WordList = getPhoneList(getSqliteDatebase());
        wordListView.setAdapter(new PhoneListAdapter(WordList));
    }

    private ArrayList<Word> getPhoneList(SQLiteDatabase db) {
        String selSql = "select * from word";
        Cursor cursor = db.rawQuery(selSql, null);
        ArrayList<Word> phoneList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Word Word = new Word();
            Word.setEnglishWord(cursor.getString(0));
            Word.setChineseWord(cursor.getString(2));
            Log.i(TAG, "getPhoneList: "+Word);
            phoneList.add(Word);
        }
        return phoneList;
    };
    public class PhoneListAdapter extends BaseAdapter {
        ArrayList<Word> WordList;

        public PhoneListAdapter(ArrayList<Word> WordList) {
            this.WordList = WordList;
        }
        @Override
        public int getCount() {
            //有多少条数据
            return WordList.size();
        }

        @Override
        public Object getItem(int i) {

            return WordList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_list_layout, null);
            TextView nameText = view.findViewById(R.id.word);
            TextView phoneText = view.findViewById(R.id.desc);
            nameText.setText(WordList.get(i).getEnglishWord());
            phoneText.setText(WordList.get(i).getChineseWord());
            return view;
        }
    }

    public SQLiteDatabase getSqliteDatebase() {
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(this, "rememberwords.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };
        return sqLiteOpenHelper.getReadableDatabase();
    }
}
