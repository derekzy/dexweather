package com.derekzy.dexweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by derekzy on 2016/2/16.
 */
public class DexWeatherOpenHelper extends SQLiteOpenHelper {
    public DexWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Province(_id integer primary key autoincrement," +
                " province_name text not null," +
                "province_code text not null)");
        db.execSQL("create table if not exists City(_id integer primary key autoincrement," +
                " city_name text not null," +
                " city_code text not null, province_id integer)");
        db.execSQL("create table if not exists County(_id integer primary key autoincrement," +
                " county_name text not null," +
                " county_code text not null, city_id integer not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
