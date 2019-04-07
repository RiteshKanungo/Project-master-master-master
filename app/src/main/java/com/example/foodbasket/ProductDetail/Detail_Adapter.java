package com.example.foodbasket.ProductDetail;

import android.app.Dialog;
import android.app.ProgressDialog;
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

public class Detail_Adapter extends RecyclerView.Adapter<Detail_Adapter.ViewHolder> {

    ArrayList<ProductDetailModel> list;
    Context context;
    int minteger = 1;
    Dialog dialog;
    String token = null;
    ProgressDialog progress;

    public Detail_Adapter(ArrayList<ProductDetailModel> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token = token;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail_recycle, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductDetailModel profileList = list.get(position);

        holder.txt_off.setText(profileList.getDiscount());
        Picasso.with(this.context).load(profileList.getImg_grid()).into(holder.img_recycle);
        holder.txt_title.setText(profileList.getTitle());
        holder.txt_rate.setText(profileList.getRate());
        holder.txt_mrp.setText(profileList.getRate());
        //   holder.txt_quantity.setText(profileList.getQuantity());
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
            layout_cart = mview.findViewById(R.id.layout_cart);
            layout_add = mview.findViewById(R.id.layout_add);

            // btn_delete = mview.findViewById(R.id.btn_delete);
            // btn_add = mview.findViewById(R.id.btn_add);
            // txt_qua = mview.findViewById(R.id.txt_qua);

            layout_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (token.equalsIgnoreCase("Null")) {
                        Intent intent = new Intent("custom-message2");
                        intent.putExtra("status", "login");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_cart);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        dialog.show();
                        dialog.setCancelable(true);

                        btn_delete = dialog.findViewById(R.id.btn_delete);
                        btn_add = dialog.findViewById(R.id.btn_add);
                        txt_qua = dialog.findViewById(R.id.txt_qua);
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
                                Intent intent = new Intent("custom-message2");
                                intent.putExtra("id", value);
                                intent.putExtra("Quantity", txt_qua.getText().toString());
                                intent.putExtra("status", "");
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            }
                        });
                        btn_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                minteger = minteger + 1;
                                txt_qua.setText(minteger + "");
                                int pos = getAdapterPosition();
                                int idvalue = list.get(pos).getId();
                                String value = String.valueOf(idvalue);

                           /* Intent intent = new Intent("custom-message");
                            intent.putExtra("id", value);
                            intent.putExtra("Quantity", txt_qua.getText().toString());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/
                            }
                        });

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (minteger < 1) {
                                    dialog.cancel();
                                } else {
                                    minteger = minteger - 1;
                                    txt_qua.setText(minteger + "");
                                    int pos = getAdapterPosition();
                                    int idvalue = list.get(pos).getId();
                                    String value = String.valueOf(idvalue);
                               /* Intent intent = new Intent("custom-message");
                                intent.putExtra("id", value);
                                intent.putExtra("Quantity", txt_qua.getText().toString());
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/
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
                }
            });
        }
    }
}