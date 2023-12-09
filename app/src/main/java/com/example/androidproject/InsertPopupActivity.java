package com.example.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class InsertPopupActivity extends Activity {
    Button breakfastBtn;
    Button lunchBtn;
    Button dinnerBtn;
    Button drinkBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_popup);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        breakfastBtn = (Button) findViewById(R.id.breakfastBtn);
        breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("recordTime", "아침");
                startActivity(intent);
            }
        });

        lunchBtn = (Button) findViewById(R.id.lunchBtn);
        lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("recordTime", "점심");
                startActivity(intent);
            }
        });


        dinnerBtn = (Button) findViewById(R.id.dinnerBtn);
        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("recordTime", "저녁");
                startActivity(intent);
            }
        });

        drinkBtn = (Button) findViewById(R.id.drinkBtn);
        drinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("recordTime", "음료");
                startActivity(intent);
            }
        });
    }
}