package com.example.foodbasket.OrderListing;

/**
 * Created by hp on 4/9/2019.
 */

public class OrderModel {

    String order_id, time, amount, items, status;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderModel(String order_id, String time, String amount, String items, String status) {
        this.order_id = order_id;
        this.time = time;
        this.amount = amount;
        this.items = items;
        this.status = status;
    }
}
