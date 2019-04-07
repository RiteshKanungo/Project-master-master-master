package com.example.foodbasket.ProductDetail;

/**
 * Created by hp on 3/23/2019.
 */

public class ProductDetailModel {
    String img_grid, title, description, rate, quantity, discount;
    int id;

    public ProductDetailModel(int id, String img_grid, String title, String rate, String quantity, String discount) {
        this.id = id;
        this.img_grid = img_grid;
        this.title = title;
        this.rate = rate;
        this.quantity = quantity;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_grid() {
        return img_grid;
    }

    public void setImg_grid(String img_grid) {
        this.img_grid = img_grid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
