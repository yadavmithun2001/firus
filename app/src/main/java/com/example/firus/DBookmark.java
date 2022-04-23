package com.example.firus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.firus.Models.HistoryModel;

import java.util.ArrayList;

public class DBookmark extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookmark";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "bookmarks";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_URL = "url";

    public DBookmark(Context context){
        super(context,DATABASE_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT,"
                + COL_URL + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
       onCreate(db);
    }
    public void savebookmark(String title,String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE,title);
        contentValues.put(COL_URL,url);
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public ArrayList<HistoryModel> loadbookmark(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        ArrayList<HistoryModel> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                list.add(new HistoryModel(cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
    public void deletebookmark(String title){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_NAME,"title=?",new String[]{title});
        db.close();
    }
    public void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
