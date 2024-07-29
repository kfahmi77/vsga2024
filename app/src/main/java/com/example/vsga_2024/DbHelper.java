package com.example.vsga_2024;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vsga_2024.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "mahasiswa";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_NIM = "nim";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA + " TEXT, " +
                COLUMN_NIM + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMahasiswa(String nama, String nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_NIM, nim);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<String> getAllMahasiswa() {
        ArrayList<String> mahasiswaList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA));
                String nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM));
                mahasiswaList.add(nama + " - " + nim);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mahasiswaList;
    }
}