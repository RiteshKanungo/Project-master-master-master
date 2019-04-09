package com.example.foodbasket.Checkout;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.ConfirmOrder.DateTime;
import com.example.foodbasket.Main.LoginSignupActivity;
import com.example.foodbasket.Main.MainActivity;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ritesh on 3/13/2019.
 */

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycle_view;
    checkout_Adapter checkoutAdapter;
    RelativeLayout layout_checkout, layout_no_item, layout_shopping, relative_new;
    ArrayList<CheckoutModel> checkoutModelArrayList;
    ScrollView scroll_view;
    TextView txt_mrp, txt_discounted, txt_delivery, txt_total;
    SharedProcessData sharedProcessData;
    String token = null, status = null;
    String id = null, quantity = null;
    ProgressDialog progressDialog;
    ImageView img_back;

    String total_amount = null, total_discount = null, cart_total = null, dilevery_charg = null, grand_total = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = getIntent().getStringExtra("Status");

        sharedProcessData = new SharedProcessData(this);
        token = sharedProcessData.getString("Token");
        Log.e("Token", token);
        if (!token.equalsIgnoreCase("Null")) {
            setContentView(R.layout.checkout_fragment);
            bindView();
        } else {
            sharedProcessData.setString("status", "login");
            startActivity(new Intent(this, LoginSignupActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
    }

    private void bindView() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        recycle_view = findViewById(R.id.recycle_view);
        recycle_view.setNestedScrollingEnabled(false);
        txt_mrp = findViewById(R.id.txt_mrp);
        txt_discounted = findViewById(R.id.txt_discounted);
        txt_delivery = findViewById(R.id.txt_delivery);
        txt_total = findViewById(R.id.txt_total);

        layout_no_item = findViewById(R.id.layout_no_item);

        layout_checkout = findViewById(R.id.layout_checkout);
        layout_checkout.setOnClickListener(this);

        layout_shopping = findViewById(R.id.layout_shopping);
        layout_shopping.setOnClickListener(this);

        scroll_view = findViewById(R.id.scroll_view);
        scroll_view.setVisibility(View.GONE);

        relative_new = findViewById(R.id.relative_new);
        relative_new.setVisibility(View.GONE);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        checkoutModelArrayList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
        checkoutAdapter = new checkout_Adapter(checkoutModelArrayList, this);
        getRecycleData();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            id = intent.getStringExtra("id");
            quantity = intent.getStringExtra("Quantity");
            status = intent.getStringExtra("status");
            Log.e("Status", status);

            if (status.equalsIgnoreCase("login")) {
                startActivity(new Intent(CheckoutActivity.this, LoginSignupActivity.class));
            } else {
                Log.e("Data", id + " : Qua :" + quantity);
                if(!quantity.equals("0")){
                    insertData(id, quantity);
                    getRecycleData();
                }else {
                    insertDataempty(id);
                    getRecycleData();
                }
            }
        }
    };

    private void insertDataempty(final String id) {
        Log.e("Insert", id);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/cart";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cart Response", response);

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("product_id", id);
                params.put("quantity", "0");
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
        progressDialog.show();
    }


    private void insertData(final String product_id, final String qua) {

        Log.e("Insert", qua);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/cart";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cart Response", response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        progressDialog.show();
    }


    private void getRecycleData() {

        if (!checkoutModelArrayList.isEmpty()) {
            checkoutModelArrayList.clear();
        }

        RequestQueue queue = Volley.newRequestQueue(CheckoutActivity.this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/cart";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CartResponse", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() == 0) {
                                layout_no_item.setVisibility(View.VISIBLE);
                                scroll_view.setVisibility(View.GONE);
                                layout_checkout.setVisibility(View.GONE);
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("product_id");
                                    String name = object.getString("product_name");
                                    String image = object.getString("product_image");
                                    String discount = object.getString("discount");
                                    String discount_by = object.getString("discount_price");
                                    String rate = object.getString("price");
                                    String quantity = object.getString("quantity");

                                    // String description = object.getString("description");
                                    // Log.e("description", description);
                                    checkoutModelArrayList.add(new CheckoutModel(Integer.parseInt(id), image, name, rate, quantity, discount));
                                    recycle_view.setAdapter(checkoutAdapter);
                                    checkoutAdapter.notifyDataSetChanged();
                                    layout_no_item.setVisibility(View.GONE);
                                    scroll_view.setVisibility(View.VISIBLE);
                                    layout_checkout.setVisibility(View.VISIBLE);
                                    relative_new.setVisibility(View.VISIBLE);
                                }
                            }

                            txt_mrp.setText(jsonObject.getString("total_amount"));
                            txt_discounted.setText(jsonObject.getString("total_discount"));
                            txt_delivery.setText(jsonObject.getString("dilevery_charg"));
                            grand_total = jsonObject.getString("grand_total");
                            txt_total.setText(grand_total);
                            total_amount = jsonObject.getString("total_amount");

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
                            Toast.makeText(CheckoutActivity.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        queue.add(postRequest);
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            overridePendingTransition(0, 0);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        // startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_checkout:
                sharedProcessData.setString("FinalPrice", grand_total);
                Intent intent = new Intent(this, DateTime.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.layout_shopping:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.img_back:
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    overridePendingTransition(0, 0);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
        }
    }
}
