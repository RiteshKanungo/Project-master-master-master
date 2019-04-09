package com.example.foodbasket.Checkout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foodbasket.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ritesh on 3/26/2019.
 */

public class checkout_Adapter extends RecyclerView.Adapter<checkout_Adapter.ViewHolder> {

    ArrayList<CheckoutModel> list;
    Context context;
    Dialog dialog;
    int minteger = 1;

    public checkout_Adapter(ArrayList<CheckoutModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkout_recycle, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CheckoutModel profileList = list.get(position);

        holder.txt_off.setText(profileList.getDiscount());
        Picasso.with(this.context).load(profileList.getImg_grid()).into(holder.img_recycle);
        holder.txt_title.setText(profileList.getTitle());
        holder.txt_rate.setText(profileList.getRate());
        holder.txt_mrp.setText(profileList.getRate());
        holder.txt_quantity.setText(profileList.getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mview;

        ImageView img_recycle;
        TextView txt_off, txt_title, txt_desc, txt_rate, txt_mrp, txt_quantity, txt_qua, btn_delete, btn_add;
        LinearLayout layout_cart;
        Button btn_cancel, btn_save;
        RelativeLayout layout_add;

        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

            img_recycle = mview.findViewById(R.id.img_1);
            txt_off = mview.findViewById(R.id.txt_off);
            txt_title = mview.findViewById(R.id.txt_title);
            txt_desc = mview.findViewById(R.id.txt_desc);
            txt_rate = mview.findViewById(R.id.txt_rate);
            txt_mrp = mview.findViewById(R.id.txt_mrp);
            txt_quantity = mview.findViewById(R.id.txt_quantity);


            txt_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_update_cart);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    dialog.show();
                    dialog.setCancelable(true);

                    btn_delete = dialog.findViewById(R.id.btn_delete);
                    btn_add = dialog.findViewById(R.id.btn_add);
                    txt_qua = dialog.findViewById(R.id.txt_qua);

                    int pos = getAdapterPosition();
                    String quantity = list.get(pos).getQuantity();
                    txt_qua.setText(quantity);

                    btn_cancel = dialog.findViewById(R.id.btn_cancel);
                    btn_save = dialog.findViewById(R.id.btn_save);

                    btn_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                            minteger = 1;
                            int pos = getAdapterPosition();
                            int idvalue = list.get(pos).getId();
                            String value = String.valueOf(idvalue);
                            Intent intent = new Intent("custom-message");
                            intent.putExtra("id", value);
                            intent.putExtra("status", "");
                            intent.putExtra("Quantity", txt_qua.getText().toString());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    });
                    btn_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer val= Integer.valueOf(txt_qua.getText().toString());
                            minteger = val + 1;
                            txt_qua.setText(minteger + "");
                        }
                    });

                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer val= Integer.valueOf(txt_qua.getText().toString());

                            if (val < 1) {
                                dialog.cancel();
                            } else {
                                minteger = val - 1;
                                txt_qua.setText(minteger + "");
                            }
                        }
                    });

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                }
            });

        }
    }
}
