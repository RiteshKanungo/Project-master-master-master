package com.example.foodbasket.SubCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodbasket.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SubCategoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<SubCategoryModel> product_grid_models;
    int height;
    LayoutInflater inflter;


    public SubCategoryAdapter(Context applicationContext, ArrayList<SubCategoryModel> product_grid_models) {
        this.context = applicationContext;
        this.product_grid_models = product_grid_models;
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
        view = inflter.inflate(R.layout.layout_subcategory_grid, null); // inflate the layout
        ImageView img_grid = view.findViewById(R.id.img_grid); // get the reference of ImageView
        TextView txt_cashback = view.findViewById(R.id.txt_cashback); // get the reference of ImageView
        TextView txt_title = view.findViewById(R.id.txt_title); // get the reference of ImageView
        TextView txt_desc = view.findViewById(R.id.txt_desc); // get the reference of ImageView
        SubCategoryModel data = (SubCategoryModel) this.product_grid_models.get(i);
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT,665));

        Picasso.with(context).load(data.getImg_grid()).into(img_grid);
        txt_cashback.setText(data.getCashback());
        txt_title.setText(data.getTitle());
        txt_desc.setText(data.getDescription());
        return view;
    }
}
