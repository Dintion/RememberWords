package com.liong.rememberwords.activity;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initVmPolicy();
    }
    /**
     * 使用VmPolicy方式检测FileUriExposure异常，解决7.0路径问题
     */
    private void initVmPolicy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

}
