package com.example.pr6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBookActivity extends AppCompatActivity {
    private EditText editbookName, editbookAuthor;
    private Button add;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_book);

        editbookName = findViewById(R.id.editTextName);
        editbookAuthor = findViewById(R.id.editTextAuthor);
        add = findViewById(R.id.add);

        db = new DataBaseHelper(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookToDatabase();
            }
        });

    }
    private void addBookToDatabase(){
        String bookName = editbookName.getText().toString().trim();
        String bookAuthor = editbookAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()){
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show();
            return;
        }

        long result = db.addBook(bookName, bookAuthor);

        if (result>0){
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddBookActivity.this, MainActivity.class));
            finish();
        }
        else{
            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
        }
    }
}
