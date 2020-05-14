package com.liong.rememberwords.tuil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class JsonUtil {

    private String filePath;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;

    public JsonUtil(String filePath) {
        this.filePath = filePath;
    }

    public static void readJson(String filePath) throws Exception {
        // 开始读JSON数据
        System.out.println("开始读取JSON数据");
        FileInputStream fileInputStream = new FileInputStream(filePath);
        @SuppressWarnings("resource")
        JsonReader jsonReader = new JsonReader(new InputStreamReader(
                fileInputStream, "UTF-8"));

        jsonReader.beginObject();

        System.out.println("哈哈：" + jsonReader.toString());
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals("id")) {
                System.out.println("id:" + jsonReader.nextString());
            }
            if (jsonReader.nextName().equals("name")) {
                System.out.println("name:" + jsonReader.nextString());
            }
        }
        jsonReader.endObject();
        jsonReader.close();


    }

    public static void writeJson(String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);

        @SuppressWarnings("resource")
        //开始写JSON数据
                JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(
                fileOutputStream, "UTF-8"));

        jsonWriter.beginObject();

        jsonWriter.name("id").value("1");

        jsonWriter.name("name").value("Android将军");

        jsonWriter.endObject();

        System.out.println("JSON数据写入完毕！");

        jsonWriter.close();
    }
}


