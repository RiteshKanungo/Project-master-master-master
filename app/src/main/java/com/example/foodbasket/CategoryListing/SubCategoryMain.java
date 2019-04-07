package com.example.foodbasket.CategoryListing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubCategoryMain extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycle_view;
    ArrayList<SubCategoryModelMain> subCategoryModelMains;
    Adaptermain subCategoryAdapterMain;
    ImageView img_back;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_category);
      //  id = getIntent().getStringExtra("Id");
        bindView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (!subCategoryModelMains.isEmpty()) {
            subCategoryModelMains.clear();
        }*/
    }

    private void bindView() {
 //      img_back = findViewById(R.id.img_back);
 //      img_back.setOnClickListener(this);
        recycle_view = findViewById(R.id.recycle_view);
        subCategoryModelMains = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
        subCategoryAdapterMain = new Adaptermain( subCategoryModelMains,this);
        getGridData();
    }

    private void getGridData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/categories";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("name");
                                String image = object.getString("image");
                                String discount = object.getString("discount");
                                String discount_by = object.getString("discount_by");
                                String description = object.getString("description");
                                Log.e("description",description);

                                if (discount_by.equalsIgnoreCase("ammount_off")) {
                                 //   subCategoryModelMains.add(new SubCategoryModelMain(image, "Rs " + discount + " off", name, description));
                                } else {
                                 //   subCategoryModelMains.add(new SubCategoryModelMain(image, discount + "% off", name, description));
                                }
                                recycle_view.setAdapter(subCategoryAdapterMain);
                                subCategoryAdapterMain.notifyDataSetChanged();
                            }
                            Log.e("Response", response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //case R.id.img_back:
             //   finish();
        }
    }
}
