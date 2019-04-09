package com.example.foodbasket.OrderListing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.Main.MainActivity;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 4/7/2019.
 */

public class ListingActivity extends AppCompatActivity {

    RecyclerView recycle_view;
    ImageView img_back;
    ArrayList<OrderModel> orderModelArrayList;
    OrderAdapter orderAdapter;
    SharedProcessData sharedProcessData;
    String tokrn = null;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_listing);
        img_back = findViewById(R.id.img_back);

        sharedProcessData = new SharedProcessData(this);
        tokrn = sharedProcessData.getString("Token");


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        recycle_view = findViewById(R.id.recycle_view);
        orderModelArrayList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
        orderAdapter = new OrderAdapter(orderModelArrayList, this);
        recycle_view.setAdapter(orderAdapter);
        getOrderData();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListingActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private void getOrderData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/orders";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Product_Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String order_id = jsonObject1.getString("order_id");
                                String dilevery_datetime = jsonObject1.getString("dilevery_datetime");
                                String status = jsonObject1.getString("status");
                                String total_amount = jsonObject1.getString("total_amount");
                                orderModelArrayList.add(new OrderModel(order_id, dilevery_datetime, "Rs "+total_amount, ""+" items", status));
                                orderAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {

                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            Toast.makeText(ListingActivity.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                            Log.d("Error.Response", error.toString());
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + tokrn);

                return params;
            }
        };
        queue.add(postRequest);
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
