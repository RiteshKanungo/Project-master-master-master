package com.example.foodbasket.ConfirmOrder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.foodbasket.R;
import java.util.ArrayList;

public class RecycleAdapterslots extends RecyclerView.Adapter<RecycleAdapterslots.ViewHolder> {

    ArrayList<SlotModel> list;
    Context context;
    private int lastSelectedPosition = -1;
    SlotModel profileList;

    public RecycleAdapterslots(ArrayList<SlotModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecycleAdapterslots.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_slots, null);
        return new RecycleAdapterslots.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        profileList = list.get(position);
        holder.txt_slot.setText(profileList.getReadable());
        holder.radioButton.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mview;

        TextView txt_slot;
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            txt_slot = mview.findViewById(R.id.txt_slot);
            radioButton = mview.findViewById(R.id.radioButton);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String idvalue = list.get(pos).getSlots();
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    //Toast.makeText(context, "selected offer is " + idvalue, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}