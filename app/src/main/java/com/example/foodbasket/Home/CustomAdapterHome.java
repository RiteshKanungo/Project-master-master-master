package com.example.foodbasket.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foodbasket.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapterHome extends BaseAdapter {
    Context context;
    List<GridData> product_grid_models;
    int height;
    LayoutInflater inflter;


    public CustomAdapterHome(Context applicationContext, List<GridData> product_grid_models) {
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
        view = inflter.inflate(R.layout.layout_grid_demo, null);
        RelativeLayout layout = view.findViewById(R.id.layout);
        ImageView icon = view.findViewById(R.id.icon);
        TextView textView = view.findViewById(R.id.txt_category);
        GridData data = this.product_grid_models.get(i);
        Picasso.with(context).load(data.getIamge()).into(icon);
        textView.setText(data.getDes());
        return view;
    }
}
