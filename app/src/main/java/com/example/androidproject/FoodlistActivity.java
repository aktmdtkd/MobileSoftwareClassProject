package com.example.androidproject;

import static com.example.androidproject.FoodContentProvider._DATE;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FoodlistActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageButton insertBtn;
    TextView tv_date;
    TextView tv_calorie;
    String total_calorie;
    String date;
    String when;
    String calorie;
    ListView lvList;
    ListViewAdapter listAdapter;
    ImageButton analyzingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        calorie = intent.getStringExtra("calorie");
        tv_date = (TextView)findViewById(R.id.date);
        tv_date.setTypeface(null, Typeface.BOLD);
        tv_date.setText(date);
        tv_calorie = (TextView)findViewById(R.id.calorie);
        tv_calorie.setTypeface(null, Typeface.BOLD);
        lvList = (ListView)findViewById(R.id.lv_list);
        displayList();

        insertBtn = (ImageButton) findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertPopupActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    static class ListViewAdapter extends BaseAdapter {

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
        public void addItemToList(String when, String calorie){
            DayListItem listdata = new DayListItem();

            listdata.setWhen_name(when);
            listdata.setTotal_calorie(calorie);
            list.add(listdata);

        }
    }

    void displayList(){
        String[] columns = new String[]{"_when", "total_calorie"};
        String where = _DATE + "=" +  "\"" + date + "\"";
        int day_total_cal = 0;
        Cursor c = getContentResolver().query(FoodContentProvider.CONTENT_URI, columns,  where, null, "_time", null);
        if (c != null) {
            ListViewAdapter adapter = new ListViewAdapter();
            while(c.moveToNext()){
                day_total_cal += Integer.parseInt(c.getString(1));
                adapter.addItemToList(c.getString(0),c.getString(1));

            }
            total_calorie = Integer.toString(day_total_cal);
            tv_calorie.setText("하루 총 칼로리 " +  total_calorie + " kcal");
            lvList.setAdapter(adapter);
            c.close();

            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DayListItem item = (DayListItem) adapter.getItem(position);
                    when = item.getWhen_name();
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("date", date);
                    intent.putExtra("when", when);
                    startActivity(intent);
                }
            });
        }
    }

}