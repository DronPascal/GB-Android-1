package com.pascal.homeworks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button btnToFirstAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnToFirstAct = findViewById(R.id.back_btn);
        btnToFirstAct.setOnClickListener((View v) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
        });
    }

}