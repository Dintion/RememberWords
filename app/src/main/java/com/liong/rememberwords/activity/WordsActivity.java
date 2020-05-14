package com.liong.rememberwords.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.Dao;
import com.liong.rememberwords.dao.WordsDao;
import com.liong.rememberwords.domain.Word;

import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {
    private static final String TAG = "WordsActivity";
    private WordsDao wordsDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        wordsDao = new WordsDao(this);
        ListView wordListView = findViewById(R.id.wordsList);
        ArrayList<Word> WordList = getwordList(Dao.getDatabase(this));
        WordListAdapter adapter = new WordListAdapter(WordList);
        wordListView.setAdapter(adapter);
        wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView word = view.findViewById(R.id.word);
                Intent intent = new Intent(WordsActivity.this, WordInfoActivity.class);
                intent.putExtra("word", word.getText().toString());
                startActivity(intent);
            }
        });
    }

    private ArrayList<Word> getwordList(SQLiteDatabase db) {
        ArrayList<Word> words;
        String name = getIntent().getStringExtra("word");
        int isRember = getIntent().getIntExtra("isRember",-1);
        if (isRember != -1) {
            words = wordsDao.selWordByIsRember(isRember);

        } else {
            if (!"".equals(name)) {
                words = wordsDao.selWordByName(name);
            } else {
                words = wordsDao.selAllWord();
            }
        }

        return words;
    }

    ;

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
            TextView pa = view.findViewById(R.id.paa);
            nameText.setText(WordList.get(i).getEnglishWord());
            phoneText.setText(WordList.get(i).getChineseWord());
            pa.setText("/"+WordList.get(i).getPa()+"/");
            return view;
        }
    }
}
