package com.example.foodbasket.Home;

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

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.ViewHolder> {

    ArrayList<Recycledata> list;
    Context context;

    public RecyclerAdapterHome(ArrayList<Recycledata> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_recycle, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recycledata profileList = list.get(position);

        Picasso.with(this.context).load(profileList.getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mview;

        TextView text;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            imageView = (ImageView) mview.findViewById(R.id.img);
        }
    }
}