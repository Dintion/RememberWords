package com.liong.rememberwords.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.liong.rememberwords.R;
import com.liong.rememberwords.dao.Dao;
import com.liong.rememberwords.dao.ReadShare;
import com.liong.rememberwords.dao.UserDao;
import com.liong.rememberwords.dao.WordsDao;
import com.liong.rememberwords.domain.Word;
import com.liong.rememberwords.service.SoundIntentService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

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

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void share(View v) {

        /**
         * 获取屏幕布局图片
         */

        String imgpath = saveImage();
        File file = new File(imgpath);
        Uri uri;

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_TEXT, "考四级不容易，所以我用大家都在用的我爱记单词背" + word.getEnglishWord() + ",它的意思是:" + word.getChineseWord());
//        intent.setType("text/plain");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(Intent.createChooser(intent, "选择分享应用"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            uri = FileProvider.getUriForFile(this, "com.liong.rememberwords.fileprovider", file);
        } else {
            //拍照结果输出到这个uri对应的file中
            uri = Uri.fromFile(new File(imgpath));
        }
        Log.i(TAG, "分享图片路径"+uri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(intent);
        file.delete();
    }

    // 1. 初始化布局：
    private String saveImage() {
        LayoutInflater from = LayoutInflater.from(this);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        View v = from.inflate(R.layout.activity_word_info, null);
        ConstraintLayout ll_poster = v.findViewById(R.id.www);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        EnglishWordEd = v.findViewById(R.id.EnglishWord);
        paEd = v.findViewById(R.id.pa);
        ChineseWordEd = v.findViewById(R.id.trans);
        e1Ed = v.findViewById(R.id.e1);
        c1Ed = v.findViewById(R.id.c1);
        e2Ed = v.findViewById(R.id.e2);
        c2Ed = v.findViewById(R.id.c2);


        EnglishWordEd.setText(word.getEnglishWord());
        paEd.setText("/" + word.getPa() + "/");
        ChineseWordEd.setText(word.getChineseWord());
        e1Ed.setText(word.getEnglishInstance1());
        c1Ed.setText(word.getChineseInstance1());
        e2Ed.setText(word.getEnglishInstance2());
        c2Ed.setText(word.getChineseInstance2());

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;
        ll_poster.setLayoutParams(params);
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        // 2. 将布局转成bitmap
        return createPicture(v);
    }

    private String createPicture(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        //3.bitmap存本地
        String strPath = getFilesDir().getPath() +"/"+UUID.randomUUID().toString()+ ".png";
        FileOutputStream fos = null;
        try {
            File file = new File(strPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);

            //当指定压缩格式为PNG时保存下来的图片显示正常
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.e("MainActivity", "图片生成：" + file.getAbsolutePath());
            //当指定压缩格式为JPEG时保存下来的图片背景为黑色
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strPath;
    }

}
