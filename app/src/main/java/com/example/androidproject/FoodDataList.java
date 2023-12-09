package com.example.androidproject;

public class FoodDataList {

    String menu;
    String amount;
    String calorie;

    public FoodDataList(String menu, String amount, String calorie){
        this.menu = menu;
        this.amount = amount;
        this.calorie = calorie;
    }
    public String getMenu() {
        return menu;
    }
    public void setMenu(String menu) {
        this.menu = menu;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getCalorie() {
        return calorie;
    }
    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

}
