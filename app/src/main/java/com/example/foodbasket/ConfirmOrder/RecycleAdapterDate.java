package com.example.foodbasket.ConfirmOrder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.foodbasket.R;
import java.util.ArrayList;

public class RecycleAdapterDate extends RecyclerView.Adapter<RecycleAdapterDate.ViewHolder> {

    ArrayList<DateModel> list;
    int selectedPosition = -1;
    Context context;

    public RecycleAdapterDate(ArrayList<DateModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecycleAdapterDate.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_date, null);
        return new RecycleAdapterDate.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DateModel profileList = list.get(position);
        holder.txt_day.setText(profileList.getDay());
        holder.txt_month.setText(profileList.getDate());

        if (selectedPosition == position) {

            holder.linear.setBackgroundColor(context.getResources().getColor(R.color.colorbackground));
            Intent intent = new Intent("time-message");
            intent.putExtra("Date", profileList.getDate());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } else
            holder.linear.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mview;
        LinearLayout linear;
        TextView txt_month, txt_day;

        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            txt_month = mview.findViewById(R.id.txt_month);
            txt_day = mview.findViewById(R.id.txt_day);
            linear = mview.findViewById(R.id.linear);
        }
    }
}