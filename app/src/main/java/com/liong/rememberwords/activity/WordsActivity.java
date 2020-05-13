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
        ArrayList<Word> WordList = getwordList(getSqliteDatebase());
        wordListView.setAdapter(new WordListAdapter(WordList));

    }

    private ArrayList<Word> getwordList(SQLiteDatabase db) {
        String name = getIntent().getStringExtra("word");
        String selSql = "select * from word where englishWord like '%"+name+"%'";
        Log.i(TAG, "getWordList: "+selSql);
        Cursor cursor = db.rawQuery(selSql, null);
        ArrayList<Word> wordList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Word Word = new Word();
            Word.setEnglishWord(cursor.getString(0));
            Word.setChineseWord(cursor.getString(2));
            Log.i(TAG, "getWordList: "+Word);
            wordList.add(Word);
        }
        return wordList;
    };
    public class WordListAdapter extends BaseAdapter {
        ArrayList<Word> WordList;
        public WordListAdapter(ArrayList<Word> WordList) {
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
