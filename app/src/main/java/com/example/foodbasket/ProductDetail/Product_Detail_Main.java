package com.example.foodbasket.ProductDetail;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.Checkout.CheckoutActivity;
import com.example.foodbasket.Home.RecyclerItemClickListener;
import com.example.foodbasket.Main.LoginSignupActivity;
import com.example.foodbasket.R;
import com.example.foodbasket.SubCategory.SubCategory;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Created by ritesh on 3/29/2019.
 */

public class Product_Detail_Main extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycle_view;
    ArrayList<ProductDetailModel> productDetailModels;
    Detail_Adapter detail_adapter;
    ImageView img_back;
    RelativeLayout layout_view_cart;
    String id = null, quantity = null;
    SharedProcessData sharedProcessData;
    String grid_id = null;
    ProgressDialog progressDialog;
    String token = null, status = null, backstatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_detail);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        sharedProcessData = new SharedProcessData(this);
        token = sharedProcessData.getString("Token");
        backstatus = sharedProcessData.getString("BackStatus");
        grid_id = getIntent().getStringExtra("sub_cat_Id");
        Log.e("Token", token);
        bindView();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message2"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            id = intent.getStringExtra("id");
            quantity = intent.getStringExtra("Quantity");
            status = intent.getStringExtra("status");
            Log.e("Status", status);

            if (status.equalsIgnoreCase("login")) {
                startActivity(new Intent(Product_Detail_Main.this, LoginSignupActivity.class));
            } else {
                Log.e("Data", id + " : Qua :" + quantity);
                insertData(id, quantity);
            }
        }
    };

    private void insertData(final String product_id, final String qua) {
        Log.e("Insert", qua);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/cart";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cart Response", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Toast.makeText(Product_Detail_Main.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("product_id", product_id);
                params.put("quantity", qua);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        queue.add(request);
    }

    private void bindView() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        recycle_view = findViewById(R.id.recycle_view);
        layout_view_cart = findViewById(R.id.layout_view_cart);
        layout_view_cart.setOnClickListener(this);

        productDetailModels = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
        detail_adapter = new Detail_Adapter(productDetailModels, this, token);
        getGridData();
    }

    private void getGridData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/products/category/" + grid_id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Product_Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String image = object.getString("image");
                                String discount = object.getString("discount");
                                String discount_by = object.getString("discount_by");
                                String rate = object.getString("price");
                                String quantity = object.getString("stock");

                                // String description = object.getString("description");
                                // Log.e("description", description);

                                if (discount_by.equalsIgnoreCase("amount_off")) {
                                    productDetailModels.add(new ProductDetailModel(Integer.parseInt(id), image, name, rate, quantity, "Rs " + discount + " off"));
                                } else {
                                    productDetailModels.add(new ProductDetailModel(Integer.parseInt(id), image, name, rate, quantity, discount + "% off"));
                                }
                                recycle_view.setAdapter(detail_adapter);
                                detail_adapter.notifyDataSetChanged();
                            }
                            getRecycleListner();
                            Log.e("Response", response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            Toast.makeText(Product_Detail_Main.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                            Log.d("Error.Response", error.toString());
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(postRequest);
        progressDialog.show();

    }

    public void getRecycleListner() {
        recycle_view.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int recycle_id = productDetailModels.get(position).getId();
               /* Intent intent = (new Intent(Product_Detail_Main.this, ProdctDetail.class));
                  intent.putExtra("Id", recycle_id + "");
                  startActivity(intent);*/
                Log.e("Postion", recycle_id + "");
            }
        }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(this, SubCategory.class));
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    startActivity(new Intent(this, SubCategory.class));
                    overridePendingTransition(0, 0);
                    finish();
                }

                break;

            case R.id.layout_view_cart:
                Intent intent = new Intent(this, CheckoutActivity.class);
                intent.putExtra("Status", "detail");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
        }
    }

    @Override
    public void onBackPressed() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            startActivity(new Intent(this, SubCategory.class));
            overridePendingTransition(0, 0);
            finish();
        } else {
            startActivity(new Intent(this, SubCategory.class));
            overridePendingTransition(0, 0);
            finish();
        }

    }
}