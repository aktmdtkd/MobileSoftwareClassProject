package com.example.androidproject;

import static com.example.androidproject.FoodContentProvider._DATE;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AnalyzingActivity extends AppCompatActivity {

    CalendarView calendar;
    TextView tv_date;
    ImageButton backBtn;
    TextView asakcal;
    TextView ohirukcal;
    TextView yorukcal;
    TextView drinkkcal;
    ListView lvList;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzing);

        calendar = findViewById(R.id.calendarView);
        tv_date = findViewById(R.id.tv_date);

        asakcal = findViewById(R.id.asakcal);
        ohirukcal = findViewById(R.id.ohirukcal);
        yorukcal = findViewById(R.id.yorukcal);
        drinkkcal = findViewById(R.id.drinkkcal);

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        displayList();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                tv_date.setText(month + 1 + "월 ");
            }
        });

        backBtn = (ImageButton) findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //칼로리,비용 보여주는 기능 필요

    }

    class ListViewAdapter extends BaseAdapter {
        ArrayList<DayListItem> list = new ArrayList<DayListItem>();

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            final Context context = viewGroup.getContext();

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_listview, viewGroup, false);
            }

            TextView tv_when = (TextView) view.findViewById(R.id.tv_when);
            TextView tv_total_calorie = (TextView) view.findViewById(R.id.tv_month_total_calorie);

            DayListItem listdata = list.get(i);

            tv_when.setText(listdata.getWhen_name());
            tv_total_calorie.setText(listdata.getTotal_calorie());

            return view;
        }

        public void addItemToList(String when, String calorie) {
            DayListItem listdata = new DayListItem();

            listdata.setWhen_name(when);
            listdata.setTotal_calorie(calorie);
            list.add(listdata);

        }
    }

    void displayList() {
        String[] columns = new String[]{"_when", "total_calorie", "_date"};
        String where = _DATE + "=" + "\"" + date + "\"";

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        int asa_total_cal = 0;
        int ohiru_total_cal = 0;
        int yoru_total_cal = 0;
        int drink_total_cal = 0;
        Cursor c = getContentResolver().query(FoodContentProvider.CONTENT_URI, columns, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                String cdate = c.getString(2);
                String month = cdate.substring(cdate.indexOf(" ") + 1, cdate.indexOf("월"));

                if (c.getString(0).equalsIgnoreCase("아침") && currentMonth == Integer.parseInt(month)) {
                    asa_total_cal += Integer.parseInt(c.getString(1));
                }
                if (c.getString(0).equalsIgnoreCase("점심") && currentMonth == Integer.parseInt(month)) {
                    ohiru_total_cal += Integer.parseInt(c.getString(1));
                }
                if (c.getString(0).equalsIgnoreCase("저녁") && currentMonth == Integer.parseInt(month)) {
                    yoru_total_cal += Integer.parseInt(c.getString(1));
                }
                if (c.getString(0).equalsIgnoreCase("음료") && currentMonth == Integer.parseInt(month)) {
                    drink_total_cal += Integer.parseInt(c.getString(1));
                }
            }
            c.close();
        }
        asakcal.setText(asa_total_cal + "kcal");
        ohirukcal.setText(ohiru_total_cal + "kcal");
        yorukcal.setText(yoru_total_cal + "kcal");
        drinkkcal.setText(drink_total_cal + "kcal");
    }

}