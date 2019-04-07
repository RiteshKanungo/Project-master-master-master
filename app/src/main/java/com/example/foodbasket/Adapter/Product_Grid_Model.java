package com.example.foodbasket.Adapter;

import java.io.Serializable;

/**
 * Created by hp on 1/2/2018.
 */

public class Product_Grid_Model implements Serializable {
    int image;
    String des;

    public Product_Grid_Model(int image, String des) {
        this.image = image;
        this.des = des;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}