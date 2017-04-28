package com.example.zhucan.safemap.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhucan on 2017/4/15.
 */

public class ApplicationContext extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
       context=this;
    }

    public static Context getContext(){
        return context;
    }
}
