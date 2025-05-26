package com.example.pr6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {
    private DataBaseHelper dbHelper;
    private int bookId;
    private EditText editBookName, editBookAuthor;
    private Button updateButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        dbHelper = new DataBaseHelper(this);
        editBookName = findViewById(R.id.edit_book_name);
        editBookAuthor = findViewById(R.id.edit_book_author);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        Intent intent = getIntent();
        if (intent != null) {
            bookId = intent.getIntExtra("bookId", -1);
            if (bookId != -1) {
                loadBookData(bookId);
            }
        }

        updateButton.setOnClickListener(v -> updateBook());
        deleteButton.setOnClickListener(v -> deleteBook());
    }

    private void loadBookData(int bookId) {
        Book book = dbHelper.getBookById(bookId);
        if (book != null) {
            editBookName.setText(book.getBook_name());
            editBookAuthor.setText(book.getBook_author());
        }
    }

    private void updateBook() {
        String name = editBookName.getText().toString();
        String author = editBookAuthor.getText().toString();
        if (name.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        int rowsAffected = dbHelper.updateBook(bookId, name, author);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Ошибка обновления", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook() {
        dbHelper.deleteBook(bookId);
        Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
        finish();
    }
}
