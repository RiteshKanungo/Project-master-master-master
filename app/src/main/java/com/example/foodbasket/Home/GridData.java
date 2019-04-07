package com.example.foodbasket.Home;

import java.io.Serializable;

/**
 * Created by hp on 1/2/2018.
 */

public class GridData implements Serializable {
    String iamge, des;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GridData(String iamge, String des, int id) {
        this.iamge = iamge;
        this.des = des;
        this.id = id;
    }

    public String getIamge() {
        return iamge;
    }

    public void setIamge(String iamge) {
        this.iamge = iamge;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}