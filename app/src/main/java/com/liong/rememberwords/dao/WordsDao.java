package com.liong.rememberwords.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liong.rememberwords.domain.Word;

import java.util.ArrayList;

public class WordsDao {
    private Context context;
    private SQLiteDatabase database;
    private String selSql;

    public WordsDao(Context context) {
        this.context = context;
        this.database = Dao.getDatabase(context);
    }
    public Word selectWordByEnglish(String EnglishWord) {
        String sql = "select * from word where englishWord=?";
        Cursor cursor = this.database.rawQuery(sql, new String[]{EnglishWord});
        Word word = new Word();
        if (cursor.moveToNext()) {
            word.setEnglishWord(cursor.getString(0));
            word.setPa(cursor.getString(1));
            word.setChineseWord(cursor.getString(2));
            word.setEnglishInstance1(cursor.getString(3));
            word.setChineseInstance1(cursor.getString(4));
            word.setEnglishInstance2(cursor.getString(5));
            word.setChineseInstance2(cursor.getString(6));
            word.setCollect(cursor.getInt(7));
            word.setPron(cursor.getString(8));
        }
        return word;
    }

    public void updateIsRember(String word) {
        String sql="update word set isrember =1 where englishWord=?";
        this.database.execSQL(sql,new String[]{word});
    }

    private ArrayList<Word> selWordByName(String name,Integer isrember) {
        selSql = "select * from word where englishWord like '%" + name + "%'";
        if (isrember != null) {
            selSql = selSql+" and isrember="+isrember;
        }
        @SuppressLint("Recycle") Cursor cursor = this.database.rawQuery(selSql,null);
        ArrayList<Word> wordList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Word Word = new Word();
            Word.setEnglishWord(cursor.getString(0));
            Word.setPa(cursor.getString(1));
            Word.setChineseWord(cursor.getString(2));
            wordList.add(Word);
        }
        return wordList;
    }
    public ArrayList<Word> selAllWord() {
        return selWordByName("", null);
    }
    public ArrayList<Word> selWordByIsRember(Integer isrember) {
        return selWordByName("", isrember);
    }
    public ArrayList<Word> selWordByName(String name) {
        return selWordByName(name, null);
    }

    public int updateWord(Word word) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("chineseword", word.getChineseWord());
        return this.database.update("word", contentValues, " englishword=?", new String[]{word.getEnglishWord()});
    }
    public int delateWord(String word) {
        return this.database.delete("word",  " englishWord=?", new String[]{word});
    }


}
