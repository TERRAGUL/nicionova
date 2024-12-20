package com.example.pract16;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {
    private EditText titleDetail, authorDetail;
    private int bookId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        dbHelper = new DatabaseHelper(this);

        titleDetail = findViewById(R.id.bookTitleDetail);
        authorDetail = findViewById(R.id.bookAuthorDetail);

        // Получение данных из Intent
        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        String title = getIntent().getStringExtra("BOOK_TITLE");
        String author = getIntent().getStringExtra("BOOK_AUTHOR");

        titleDetail.setText(title);
        authorDetail.setText(author);

        // Обработчик кнопки обновления
        findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        // Обработчик кнопки удаления
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    private void updateBook() {
        String newTitle = titleDetail.getText().toString();
        String newAuthor = authorDetail.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, newTitle);
        values.put(DatabaseHelper.COLUMN_AUTHOR, newAuthor);

        db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(bookId)});
        Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(bookId)});
        Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}