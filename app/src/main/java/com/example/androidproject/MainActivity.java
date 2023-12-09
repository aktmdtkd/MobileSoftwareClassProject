package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    TextView tv_date;
    TextView today;
    Button foodListBtn;
    ImageButton analyzingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = findViewById(R.id.calendar);
        tv_date = findViewById(R.id.foodlistbtn);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                tv_date.setText(year + "년 " + (month + 1) + "월 " + day + "일");
           }
        });

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd일");
        today = findViewById(R.id.today);
        today.setText("오늘:"+format.format(Calendar.getInstance().getTime()));
        foodListBtn = (Button) findViewById(R.id.foodlistbtn);
        foodListBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", tv_date.getText().toString());
                startActivity(intent);
            }
        });
        analyzingBtn = (ImageButton) findViewById(R.id.analyzingbtn);
        analyzingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AnalyzingActivity.class);
                startActivity(intent);
            }
        });
    }
}