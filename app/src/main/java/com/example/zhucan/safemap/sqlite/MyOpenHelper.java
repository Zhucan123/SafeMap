package com.example.zhucan.safemap.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhucan on 2017/4/14.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE="create table _dict(_id integer primary " +
            "key autoincrement ,Latitude,Longitude,name)";

    public MyOpenHelper(Context context ,String name,int version){
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db ,int oldVersion,int newVersion){

    }
}
