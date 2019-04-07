package com.example.foodbasket.ConfirmOrder;

public class DateModel {

    String day, date;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateModel(String day, String date) {
        this.day = day;
        this.date = date;
    }
}
