package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        TextView textView = findViewById(R.id.tv_history);
        String content = getIntent().getStringExtra("content");
        textView.setText(content);

        Button clearButton = findViewById(R.id.btn_clear);
        clearButton.setOnClickListener(v -> {
            textView.setText("");
            try {
                FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), "db_file.txt"));
                fos.write("".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}