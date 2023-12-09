package com.example.androidproject;

import static com.example.androidproject.FoodContentProvider.WHEN;
import static com.example.androidproject.FoodContentProvider._DATE;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    ImageButton backBtn;
    String date;
    String when;
    TextView tv_date;
    TextView tv_when;
    TextView tv_time;
    ImageView foodImg;
    TextView tv_score;
    TextView tv_memo;
    TextView tv_location;
    TextView tv_menu1;
    TextView tv_menu2;
    TextView tv_menu3;
    TextView tv_total_calorie;

    RatingBar ratingBar;

    ImageButton locationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        when = intent.getStringExtra("when");
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(date);
        tv_when = (TextView) findViewById(R.id.tv_when);
        tv_when.setText(when);
        backBtn = (ImageButton) findViewById(R.id.backtofoodBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_score = (TextView) findViewById(R.id.tv_score);
        foodImg = (ImageView) findViewById(R.id.foodImg);
        tv_memo = (TextView) findViewById(R.id.tv_memo);
        tv_memo.setMovementMethod(new ScrollingMovementMethod());
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
        ratingBar = findViewById(R.id.ratingBar);
        tv_total_calorie = (TextView) findViewById(R.id.tv_month_total_calorie);
        String[] columns = new String[]{"_time", "image", "score", "location", "memo", "total_calorie", "menu_name1", "menu_name2", "menu_name3", "amount1", "amount2", "amount3", "cost1", "cost2", "cost3"};
        String where = _DATE + "=" + "\"" + date + "\"" + " and " + WHEN + "=" + "\"" + when + "\"";
        Cursor c = getContentResolver().query(FoodContentProvider.CONTENT_URI, columns, where, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                tv_time.setText(c.getString(0));
                String image = c.getString(1);
                String score = c.getString(2);
                tv_score.setText(score);
                ratingBar.setRating(Float.parseFloat(score));
                String location = c.getString(3);
                tv_location.setText(location);
                tv_memo.setText(c.getString(4));
                tv_total_calorie.setText(c.getString(5) + " kcal");
                tv_menu1.setText(c.getString(6) + "     " + c.getString(9) + "g/개     " + c.getString(12) + "원");
                if (c.getString(7) != null) {
                    tv_menu2.setText(c.getString(7) + "     " + c.getString(10) + "g/개     " + c.getString(13) + "원");
                }
                if (c.getString(8) != null) {
                    tv_menu3.setText(c.getString(8) + "     " + c.getString(11) + "g/개     " + c.getString(14) + "원");
                }

                Glide.with(getApplicationContext()).load(image).override(500, 500).into(foodImg);
            }
            c.close();
        }
    }
}