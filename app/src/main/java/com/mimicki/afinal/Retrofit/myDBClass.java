//package com.mimicki.afinal.Retrofit;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
///**
// * Created by chanunya on 2/8/2017 AD.
// */
//public class myDBClass extends SQLiteOpenHelper {
//
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "epls";
//    private static final String TABLE_MEMBER = "login";
//
//    public myDBClass(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE" + TABLE_MEMBER + "(username INTEGER PRIMARY KEY," + "password TEXT(100));");
//        Log.d("CREATE TABLE", "Create Table Successfully.");
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}
