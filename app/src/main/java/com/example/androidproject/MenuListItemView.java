package com.example.androidproject;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
public class MenuListItemView extends LinearLayout {
    TextView tx_menuName;
    TextView tx_amount;
    TextView tx_cost;
    public MenuListItemView(Context context) {
        super(context);
        init(context);
    }
    public MenuListItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.menu_list_item, this, true);
        tx_menuName = findViewById(R.id.menuName);
        tx_amount = findViewById(R.id.amount);
        tx_cost = findViewById(R.id.costView);
    }
    public void setName(String str){
        tx_menuName.setText(str);
    }
    public void setAmount(String str){
        tx_amount.setText(str + "g/개");
    }
    public void setCost(String str) {tx_cost.setText(str + "원"); }
}
