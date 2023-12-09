package com.example.androidproject;
public class MenuListItem {
    private String MenuName;
    private String amount;
    private String cost;
    public MenuListItem(String MenuName, String amount, String cost) {
        this.MenuName  = MenuName;
        this.amount = amount;
        this.cost = cost;
    }
    public String getMenuName() {
        return MenuName;
    }
    public void setMenuName(String menuName) {
        MenuName = menuName;
    }
    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getAmount() { return amount;}
    public void setAmount(String amount) {
        this.amount = amount;
    }

}
