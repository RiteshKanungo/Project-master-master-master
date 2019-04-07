package com.example.foodbasket.CategoryListing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodbasket.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hp on 3/26/2019.
 */

public class Adaptermain extends RecyclerView.Adapter <Adaptermain.ViewHolder>{

    ArrayList<SubCategoryModelMain> list;
    Context context;

    public Adaptermain(ArrayList<SubCategoryModelMain> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycle_layout,null);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubCategoryModelMain profileList =list.get(position);


        holder.txt_cashback.setText(profileList.getCashback());
        Picasso.with(this.context).load(profileList.getImg_grid()).into(holder.img_recycle);
        holder.txt_title.setText(profileList.getTitle());
        holder.txt_desc.setText(profileList.getDescription());
        //  Log.e("Slider_Image ", "http://nuanceinfotechdemo.website/salnoi/sponsor/" + profileList.getImage());
    }
    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public View mview;

        ImageView img_recycle;
        TextView txt_cashback,txt_title,txt_desc;

        public ViewHolder(View itemView) {
            super(itemView);
            mview=itemView;

            img_recycle = mview.findViewById(R.id.img_1); // get the reference of ImageView
            txt_cashback = mview.findViewById(R.id.txt_discount); // get the reference of ImageView
            txt_title = mview.findViewById(R.id.txt_title); // get the reference of ImageView
            txt_desc = mview.findViewById(R.id.txt_desc); // get the reference of ImageView
        }
    }
}
