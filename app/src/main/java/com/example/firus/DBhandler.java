package com.example.firus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.firus.Models.HistoryModel;

import java.util.ArrayList;

public class DBhandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "history";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME_HISTORY = "myhistory";
    private static final String COL_ID = "id";
    private static final String COL_TITLE_HISTORY = "title";
    private static final String COL_URL_HISTORY ="url";


    public DBhandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME_HISTORY + " ("
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE_HISTORY + " TEXT,"
                + COL_URL_HISTORY + " TEXT)";
        db.execSQL(query);
    }
    public void savehistory(String title,String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE_HISTORY,title);
        contentValues.put(COL_URL_HISTORY,url);
        db.insert(TABLE_NAME_HISTORY,null,contentValues);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HISTORY);
        onCreate(db);
    }

    public ArrayList<HistoryModel> loadhistory(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_HISTORY,null);
        ArrayList<HistoryModel> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                list.add(new HistoryModel(cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void deletehistory(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_HISTORY, "title=?", new String[]{title});
        db.close();
    }
    public void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_HISTORY,null,null);
    }
}
