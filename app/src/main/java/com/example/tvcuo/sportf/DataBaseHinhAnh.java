package com.example.tvcuo.sportf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataBaseHinhAnh extends SQLiteOpenHelper {
    public DataBaseHinhAnh( Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sqlQuery){
        SQLiteDatabase database= getWritableDatabase();
        database.execSQL(sqlQuery);
    }

    public Cursor getDataSql(String sqlQuery){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sqlQuery,null);
    }

    public void Insert(int IdSB, String Url){
        SQLiteDatabase database= getWritableDatabase();
        String sql= "INSERT INTO HinhAnh VALUES(null, ?, ?)";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,IdSB);
        statement.bindString(2,Url);

        statement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
