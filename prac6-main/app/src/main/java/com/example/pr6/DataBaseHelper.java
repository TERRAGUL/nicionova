package com.example.pr6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book_db";
    private static final int SCHEMA = 1;
    static final String TABLE_NAME = "books";
        public static final String COLUMN_ID = "id_book";
        public static final String COLUMN_NAME = "book_name";
        public static final String COLUMN_AUTHOR = "book_author";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("    CREATE TABLE " + TABLE_NAME + "("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COLUMN_NAME + " TEXT, "
        + COLUMN_AUTHOR + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addBook(String bookName, String bookAuthor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vs = new ContentValues();
        vs.put(COLUMN_NAME, bookName);
        vs.put(COLUMN_AUTHOR, bookAuthor);

        long result = db.insert(TABLE_NAME, null, vs);
        db.close();
        return result;
    }

    public Cursor getAllBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public int updateBook(int id, String newName, String newAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_AUTHOR, newAuthor);
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Book getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        Book book = null;
        if (cursor.moveToFirst()) {
            int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String bookName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String bookAuthor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR));
            book = new Book(bookId, bookName, bookAuthor);
        }
        cursor.close();
        db.close();
        return book;
    }
}
