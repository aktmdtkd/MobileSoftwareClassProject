package com.example.androidproject;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;


//--------------------------------------------------------------------------------------------------------------------------------------------

public class InsertActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    String date;
    String recordTime;
    ImageButton backBtn;
    TextView tv_date;
    TextView tv_recordTime;
    Button timeBtn;
    String time;
    ImageView foodImg;
    Uri ImgUri;
    Button imgBtn;
    ImageButton mapBtn;
    ListView listView;
    ListAdapter listAdapter;
    EditText editTextName;
    EditText editTextCalories;
    String[] menu_name = new String[3];
    String[] amount = new String[3];
    String[] cost = new String[3];
    ImageButton menuPlusBtn;
    RatingBar ratingBar;
    TextView tv_ratingbar;
    EditText editMemo;
    String total_calorie;
    EditText editTextCost;
    TextView tv_location;
    MenuListItem item;
    ArrayList<MenuListItem> tmp_menuListItems;
    Button insertBtn;
    FoodDataList[] foodData = new FoodDataList[20];
    ArrayList<FoodDataList> foodDataLists = new ArrayList<>();
    //추가한거
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        tv_location = (TextView) findViewById(R.id.location);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_group_btn1:
                        tv_location.setText("상록원1층");
                        break;
                    case R.id.radio_group_btn2:
                        tv_location.setText("상록원2층");
                        break;
                    case R.id.radio_group_btn3:
                        tv_location.setText("상록원3층");
                        break;
                    case R.id.radio_group_btn4:
                        tv_location.setText("기숙사식당");
                        break;
                }
            }
        });

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        recordTime = intent.getStringExtra("recordTime");


        tv_date = (TextView) findViewById(R.id.date);
        tv_date.setTypeface(null, Typeface.BOLD);
        tv_date.setText(date);

        tv_recordTime = (TextView) findViewById(R.id.recordTime);
        tv_recordTime.setTypeface(null, Typeface.BOLD);
        tv_recordTime.setText(recordTime);

        foodData[0] = new FoodDataList("오넛지", "1", "560");
        foodData[1] = new FoodDataList("덮밥", "1", "455");
        foodData[2] = new FoodDataList("불닭철판볶음밥", "1", "678");
        foodData[3] = new FoodDataList("계란라면", "1", "389");
        foodData[4] = new FoodDataList("라볶이", "1", "484");
        foodData[5] = new FoodDataList("햄버거", "1", "888");
        foodData[6] = new FoodDataList("돈까스", "1", "970");
        foodData[7] = new FoodDataList("우동", "1", "266");
        foodData[8] = new FoodDataList("마카롱", "3", "648");
        foodData[9] = new FoodDataList("마들렌", "2", "500");
        foodData[10] = new FoodDataList("공기밥", "1", "300");
        foodData[11] = new FoodDataList("푸딩", "1", "158");
        foodData[12] = new FoodDataList("콜라", "500", "110");
        foodData[13] = new FoodDataList("우유", "250", "40");
        foodData[14] = new FoodDataList("라떼", "300", "55");
        foodData[15] = new FoodDataList("스무디", "600", "890");
        foodData[16] = new FoodDataList("얼그레이", "400", "30");
        foodData[17] = new FoodDataList("김밥", "1", "250");
        foodData[18] = new FoodDataList("치즈라면", "1", "491");
        foodData[19] = new FoodDataList("사이다", "500", "120");

        foodDataLists.addAll(Arrays.asList(foodData).subList(0, 20));

        backBtn = (ImageButton) findViewById(R.id.backtofoodBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
        timeBtn = (Button) findViewById(R.id.time);
        foodImg = findViewById(R.id.foodImg);
        imgBtn = findViewById(R.id.ImageBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });

        listView = findViewById(R.id.list_view);
        editTextName = findViewById(R.id.edit_text_name);
        editTextCalories = findViewById(R.id.edit_text_calories);
        editTextCost = findViewById(R.id.edit_text_cost);
        menuPlusBtn = findViewById(R.id.btn_add);

        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (MenuListItem) listAdapter.getItem(position);
                Toast.makeText(getApplication(), listAdapter.getItemId(position) + " 아이디값", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int count;
                count = listAdapter.getCount();
                item = (MenuListItem) listAdapter.getItem(position);
                if (count > 0) {
                    Log.v("test", "삭제 점검");
                    // 아이템 삭제
                    listAdapter.delItem(item);
                    Log.v("test", "삭제");
                    // listview 선택 초기화.
                    listView.clearChoices();
                    Log.v("test", "뷰리셋");
                    // listview 갱신.
                    listAdapter.notifyDataSetChanged();
                    Log.v("test", "삭제 점검3");
                }
                Toast.makeText(getApplication(), item.getMenuName() + " 삭제 완료", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        menuPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String calories = editTextCalories.getText().toString();
                String cost = editTextCost.getText().toString();

                listAdapter.addItem(new MenuListItem(name, calories, cost));
                listAdapter.notifyDataSetChanged();
            }
        });
        ratingBar = findViewById(R.id.ratingBar);
        tv_ratingbar = findViewById(R.id.tv_ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv_ratingbar.setText(String.valueOf(rating));
            }
        });

        tv_location = (TextView) findViewById(R.id.location);
        editMemo = findViewById(R.id.edit_memo);
        insertBtn = (Button) findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count, total = 0;
                count = listAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    item = (MenuListItem) listAdapter.getItem(i);
                    // 칼로리 찾기
                    for (FoodDataList foodData : foodDataLists) {
                        if (Objects.equals(item.getMenuName(), foodData.getMenu()) && Objects.equals(item.getAmount(), foodData.getAmount())) {
                            Toast.makeText(getBaseContext(), "제대로 저장되는 중.", Toast.LENGTH_LONG).show();
                            menu_name[i] = item.getMenuName();
                            amount[i] = item.getAmount();
                            cost[i] = item.getCost();
                            total += Integer.parseInt(foodData.getCalorie());
                        }
                    }
                }

                total_calorie = Integer.toString(total);

                ContentValues addValues = new ContentValues();
                addValues.put(FoodContentProvider._DATE, date);
                addValues.put(FoodContentProvider.WHEN, recordTime);
                addValues.put(FoodContentProvider._TIME, time);
                addValues.put(FoodContentProvider.IMAGE, ImgUri.toString());
                addValues.put(FoodContentProvider.LOCATION, tv_location.getText().toString());
                addValues.put(FoodContentProvider.MENU_NAME1, menu_name[0]);
                addValues.put(FoodContentProvider.MENU_NAME2, menu_name[1]);
                addValues.put(FoodContentProvider.MENU_NAME3, menu_name[2]);
                addValues.put(FoodContentProvider.AMOUNT1, amount[0]);
                addValues.put(FoodContentProvider.AMOUNT2, amount[1]);
                addValues.put(FoodContentProvider.AMOUNT3, amount[2]);
                addValues.put(FoodContentProvider.COST1, cost[0]);
                addValues.put(FoodContentProvider.COST2, cost[1]);
                addValues.put(FoodContentProvider.COST3, cost[2]);
                addValues.put(FoodContentProvider.SCORE, tv_ratingbar.getText().toString());
                addValues.put(FoodContentProvider.MEMO, editMemo.getText().toString());
                addValues.put(FoodContentProvider.TOTAL_CALORIE, total_calorie);
                getContentResolver().insert(FoodContentProvider.CONTENT_URI, addValues);
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", date);
                Toast.makeText(getBaseContext(), "등록되었습니다.", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

    }

//--------------------------------------------------------------------------------------------------------------------------------------------
    class ListAdapter extends BaseAdapter {
        ArrayList<MenuListItem> listItem = new ArrayList<>();
        public void addItem(MenuListItem item) {
            listItem.add(item);
        }
        public void delItem(Object o) {
            listItem.remove(o);
        }
        @Override
        public int getCount() {
            return listItem.size();
        }
        @Override
        public Object getItem(int position) {
            return listItem.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuListItemView listItemView = null;
            if (convertView == null)
                listItemView = new MenuListItemView(getApplicationContext());
            else
                listItemView = (MenuListItemView) convertView;
            MenuListItem item = listItem.get(position);
            listItemView.setName(item.getMenuName());
            listItemView.setAmount(item.getAmount());
            listItemView.setCost(item.getCost());
            return listItemView;
        }
    }
    public void onClickTime(View view) {
        DialogFragment df = new TimPickerDialog();
        df.show(getSupportFragmentManager(), "Time Picker");
    }
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        timePicker.setIs24HourView(true);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        SimpleDateFormat sdf;
        if (hour > 12) {
            hour -= 12;
             sdf = new SimpleDateFormat("오후 KK시 mm분");
        } else {
             sdf = new SimpleDateFormat("오전 KK시 mm분");
        }
        time = sdf.format(cal.getTime());
        timeBtn.setText(time);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Glide.with(getApplicationContext()).load(data.getData()).override(500, 500).into(foodImg);
            ImgUri = data.getData();
        }
    }
}