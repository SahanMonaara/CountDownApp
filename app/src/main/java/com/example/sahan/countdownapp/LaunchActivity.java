package com.example.sahan.countdownapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class LaunchActivity extends AppCompatActivity {
    public FButton startBtn;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        startBtn = findViewById(R.id.startBtn);
        title = findViewById(R.id.titleTv);
        startBtn.setButtonColor(getResources().getColor(R.color.colorPrimaryDark));
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this,CountDownActivity.class);
                startActivity(intent);
            }
        });

    }
}
