package com.example.pract16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Имя базы данных
    private static final String DATABASE_NAME = "books.db";

    // Версия базы данных
    private static final int DATABASE_VERSION = 1;

    // Имя таблицы
    public static final String TABLE_NAME = "books";

    // Поля таблицы
    public static final String COLUMN_ID = "id";       // Идентификатор книги
    public static final String COLUMN_TITLE = "title"; // Название книги
    public static final String COLUMN_AUTHOR = "author"; // Автор книги

    // SQL-запрос для создания таблицы
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старой таблицы при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Метод для добавления книги
    public long addBook(String title, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);

        return db.insert(TABLE_NAME, null, values); // Возвращает -1, если произошла ошибка
    }

    // Метод для получения всех книг
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " ASC");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR));

                books.add(new Book(id, title, author));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return books;
    }

    // Метод для обновления данных книги
    public int updateBook(int id, String newTitle, String newAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_AUTHOR, newAuthor);

        return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Метод для удаления книги
    public int deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
