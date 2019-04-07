package com.example.foodbasket.Home;

public class Recycledata {


    public Recycledata(String img, int id) {
        this.img = img;
        this.id = id;
    }

    String img;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
