package com.example.foodbasket.OrderListing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foodbasket.R;

import java.util.ArrayList;

/**
 * Created by hp on 4/9/2019.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ArrayList<OrderModel> list;
    Context context;

    public OrderAdapter(ArrayList<OrderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_recycle, null);
        return new OrderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        OrderModel profileList = list.get(position);
        holder.txt_date.setText(profileList.getTime());
        holder.txt_order_id.setText(profileList.getOrder_id());
        holder.txt_amount.setText(profileList.getAmount());
        holder.txt_item.setText(profileList.getItems());
        holder.txt_status.setText(profileList.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mview;

        TextView txt_date, txt_order_id, txt_amount, txt_item, txt_status;

        RelativeLayout layout_add;

        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

            txt_date=mview.findViewById(R.id.txt_date);
            txt_order_id=mview.findViewById(R.id.txt_order_id);
            txt_amount=mview.findViewById(R.id.txt_amount);
            txt_item=mview.findViewById(R.id.txt_item);
            txt_status=mview.findViewById(R.id.txt_status);
        }
    }
}
