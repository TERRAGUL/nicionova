package com.example.pract16;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText titleInput, authorInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Инициализация полей и базы данных
        titleInput = findViewById(R.id.bookTitle);
        authorInput = findViewById(R.id.bookAuthor);
        dbHelper = new DatabaseHelper(this);

        // Установка обработчика на кнопку "Сохранить"
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
    }

    private void saveBook() {
        // Получение текста из полей
        String title = titleInput.getText().toString().trim();
        String author = authorInput.getText().toString().trim();

        // Проверка, что поля не пустые
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author)) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Добавление книги в базу данных
        long result = dbHelper.addBook(title, author);
        if (result != -1) {
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
            finish(); // Закрыть активность после добавления
        } else {
            Toast.makeText(this, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        }
    }
}