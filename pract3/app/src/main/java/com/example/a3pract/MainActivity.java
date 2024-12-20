package com.example.a3pract;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button btnChooseImage = findViewById(R.id.btnChooseImage);
        Button btnRecordVideo = findViewById(R.id.btnRecordVideo);
        Button btnCapturePhoto = findViewById(R.id.btnCapturePhoto);

        btnChooseImage.setOnClickListener(v -> openGallery());
        btnRecordVideo.setOnClickListener(v -> checkPermissionsForVideo());
        btnCapturePhoto.setOnClickListener(v -> checkPermissionsForPhoto());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(galleryIntent);
    }

    private void checkPermissionsForVideo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO}, 100);
        } else {
            recordVideo();
        }
    }

    private void checkPermissionsForPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA}, 101);
        } else {
            capturePhoto();
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void recordVideo() {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (videoIntent.resolveActivity(getPackageManager()) != null) {
            resultLauncher.launch(videoIntent);
        } else {
            Toast.makeText(this, "Запись видео недоступна", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void capturePhoto() {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoIntent.resolveActivity(getPackageManager()) != null) {
            resultLauncher.launch(photoIntent);
        } else {
            Toast.makeText(this, "Фотосъемка недоступна", Toast.LENGTH_SHORT).show();
        }
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();

                    if (uri == null && result.getData().getExtras() != null) {
                        // Handle photo capture as Bitmap
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        if (bitmap != null) {
                            saveImageToGallery(bitmap);
                            imageView.setImageBitmap(bitmap);
                        }
                    } else if (uri != null) {
                        // Handle video capture as Uri
                        Log.d("MainActivity", "Видео URI: " + uri.toString());
                        Toast.makeText(this, "Видео сохранено", Toast.LENGTH_SHORT).show();
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    }
                }
            });

    private void saveImageToGallery(Bitmap bitmap) {
        Uri uri;
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "Captured_Image_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                if (bitmap != null) {
                    assert out != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    Toast.makeText(this, "Фото сохранено", Toast.LENGTH_SHORT).show();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear();
                    values.put(MediaStore.Images.Media.IS_PENDING, 0);
                    getContentResolver().update(uri, values, null, null);
                }
                MediaScannerConnection.scanFile(this, new String[]{uri.getPath()}, null,
                        (path, uri1) -> Log.d("MainActivity", "Фото добавлено в галерею: " + path));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Не удалось сохранить фото", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
