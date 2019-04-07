package com.example.foodbasket.ConfirmOrder;

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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.Checkout.CheckoutActivity;
import com.example.foodbasket.Home.RecyclerItemClickListener;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTime extends AppCompatActivity implements View.OnClickListener {

    ImageView img_back;
    TextView txt_change_address, txt_name, txt_address;
    RecyclerView recycle_view, recycle_view2;
    String str_name, str_address, slots = null;
    SharedProcessData sharedProcessData;
    RelativeLayout layout_confirm;
    RecycleAdapterslots recycleAdapterslots;
    RecycleAdapterDate recycleAdapterDate;
    ArrayList<DateModel> dateModelArrayList;
    ArrayList<SlotModel> slotModelArrayList;
    ProgressDialog progressDialog;
    String time = null, token = null;
    Calendar calendar;
    String dayOfWeek=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_time_date);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        getSharedData();
        sharedProcessData = new SharedProcessData(this);
        token = sharedProcessData.getString("Token");
        str_address = sharedProcessData.getString("address");

        Date d = new Date();
        dayOfWeek = (String) DateFormat.format("dd-mm-yyyy ", d.getTime());
        bindView();
        slotModelArrayList = new ArrayList<>();
        getRecycleData();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("time-message"));
        getRecycleData2();
    }

    private void getRecycleData2() {

        recycle_view2 = findViewById(R.id.recycle_view2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DateTime.this, LinearLayoutManager.VERTICAL, false);
        recycle_view2.setLayoutManager(mLayoutManager);

        recycleAdapterslots = new RecycleAdapterslots(slotModelArrayList, DateTime.this);
        recycle_view2.setAdapter(recycleAdapterslots);

        getTimeData(dayOfWeek.substring(0, 2));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            slots = intent.getStringExtra("Date").substring(0, 2);
            Log.e("Date", slots);
            getTimeData(slots);
            recycleAdapterslots.notifyDataSetChanged();
            //  Toast.makeText(context, "" + slots, Toast.LENGTH_SHORT).show();

            recycle_view2.addOnItemTouchListener(new RecyclerItemClickListener(DateTime.this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    time = slotModelArrayList.get(position).getSlots();
                }
            }));
        }
    };

    private void getTimeData(final String time) {
        slotModelArrayList.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/order/timing/" + time;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Date Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject2 = jsonObject.getJSONObject("data");

                            JSONArray jsonArray = jsonObject2.getJSONArray("time");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String slots = jsonObject1.getString("datetime");
                                String readable = jsonObject1.getString("readable");
                                slotModelArrayList.add(new SlotModel(slots, readable));
                                recycle_view2.setAdapter(recycleAdapterslots);
                                recycleAdapterslots.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                            Toast.makeText(DateTime.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(postRequest);
        progressDialog.show();
    }

    private void getRecycleData() {
        recycle_view = findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
        dateModelArrayList = new ArrayList<>();
        recycleAdapterDate = new RecycleAdapterDate(dateModelArrayList, this);
        getData();
    }

    private void getData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/order/timing";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Date Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String day_name = jsonObject1.getString("day_name");
                                String day = jsonObject1.getString("day");
                                String month = jsonObject1.getString("month");
                                Log.e("Date", day + " " + month);
                                dateModelArrayList.add(new DateModel(day_name, day + " " + month));
                                recycle_view.setAdapter(recycleAdapterDate);
                                 recycleAdapterDate.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                            Toast.makeText(DateTime.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(postRequest);
        progressDialog.show();
    }

    private void getSharedData() {
        sharedProcessData = new SharedProcessData(this);
        str_name = sharedProcessData.getString("name");
        //str_address = sharedProcessData.getString("address");
    }

    private void bindView() {
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        txt_change_address = findViewById(R.id.txt_change_address);
        txt_change_address.setOnClickListener(this);

        txt_name = findViewById(R.id.txt_name);
        txt_name.setText(str_name);

        txt_address = findViewById(R.id.txt_address);
        txt_address.setText(str_address);

        txt_address = findViewById(R.id.txt_address);
        layout_confirm = findViewById(R.id.layout_confirm);
        layout_confirm.setOnClickListener(this);
        // txt_address.setText(str_address);
        //
    }

    @Override
    public void onBackPressed() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            startActivity(new Intent(this, CheckoutActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else {
            startActivity(new Intent(this, CheckoutActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                startActivity(new Intent(this, CheckoutActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.txt_change_address:
                break;

            case R.id.layout_confirm:
                if (time != null) {
                    placeOrder(time);
                } else {
                    Toast.makeText(this, "Select Delivery Time ", Toast.LENGTH_SHORT).show();
                }
        }

    }

    private void placeOrder(final String datetime) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/orders";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");
                            String displayText = jsonObject.getString("displayText");
                            Toast.makeText(DateTime.this, "" + msg, Toast.LENGTH_SHORT).show();
                            JSONObject object = jsonObject.getJSONObject("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                            Toast.makeText(DateTime.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dilevery_datetime", datetime);
                params.put("payment_method", "cod");
                return params;
            }

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
}
