package com.example.foodbasket.SubCategory;

/**
 * Created by hp on 3/23/2019.
 */

public class SubCategoryModel {
    String img_grid, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_grid() {
        return img_grid;
    }

    public void setImg_grid(String img_grid) {
        this.img_grid = img_grid;
    }

    public String getCashback() {
        return cashback;
    }

    public void setCashback(String cashback) {
        this.cashback = cashback;
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

    String cashback, title, description;


    public SubCategoryModel(String id, String img_grid, String cashback, String title, String description) {
        this.id = id;
        this.img_grid = img_grid;
        this.cashback = cashback;
        this.title = title;
        this.description = description;
    }


}
