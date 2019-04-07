package com.example.foodbasket.Cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.foodbasket.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Data> dataList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int selectedPosition = -1;
    Context context;

    public RecyclerViewAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Data data = dataList.get(position);
        holder.img_recycle.setImageResource(data.getImg());

        if (selectedPosition == position)
            holder.itemView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.custom_border));

        else
            holder.itemView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.white_border));

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
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_recycle;

        ViewHolder(View itemView) {
            super(itemView);

            img_recycle = itemView.findViewById(R.id.img_recycle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Data getItem(int id) {
        return dataList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}