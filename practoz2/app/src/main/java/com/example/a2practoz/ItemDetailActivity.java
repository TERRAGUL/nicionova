package com.example.a2practoz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ImageView imageView = findViewById(R.id.item_image_full);
        TextView titleTextView = findViewById(R.id.item_title_full);
        TextView descriptionTextView = findViewById(R.id.item_description_full);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int imageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_foreground);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        imageView.setImageResource(imageResId);
    }
}

