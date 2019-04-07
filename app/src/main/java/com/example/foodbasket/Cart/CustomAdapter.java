package com.example.foodbasket.Cart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.foodbasket.Adapter.Product_Grid_Model;
import com.example.foodbasket.R;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product_Grid_Model> product_grid_models;
    int height;
    LayoutInflater inflter;


    public CustomAdapter(Context applicationContext, ArrayList<Product_Grid_Model> product_grid_models, int height) {
        this.context = applicationContext;
        this.product_grid_models = product_grid_models;
        this.height = height;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return product_grid_models.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.layout_grid, null); // inflate the layout
        RelativeLayout layout = view.findViewById(R.id.layout);
        ImageView icon = view.findViewById(R.id.icon); // get the reference of ImageView
        Product_Grid_Model data = (Product_Grid_Model) this.product_grid_models.get(i);

        Log.e("CustomAdapter", String.valueOf(height));
        icon.requestLayout();

        icon.getLayoutParams().height = height;
        icon.setImageResource(data.getImage()); // set logo images
        return view;
    }
}
