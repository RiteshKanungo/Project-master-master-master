package com.example.foodbasket.SubCategory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.example.foodbasket.Main.MainActivity;
import com.example.foodbasket.ProductDetail.Product_Detail_Main;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubCategory extends AppCompatActivity implements View.OnClickListener {
    GridView grid_category;
    ArrayList<SubCategoryModel> subCategoryModels;
    SubCategoryAdapter subCategoryAdapter;
    ImageView img_back;
    RelativeLayout relative_grid;
    TextView txt_no_item;
    String id, status;
    SharedProcessData sharedProcessData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sudcategory);
        // id = getIntent().getStringExtra("Id");
        // id = getIntent().getStringExtra("Id");
        sharedProcessData = new SharedProcessData(this);
        id = sharedProcessData.getString("Id");
        bindView();
    }

    @Override
    protected void onResume() {
        getGridData(Integer.parseInt(id));
        super.onResume();
        if (!subCategoryModels.isEmpty()) {
            subCategoryModels.clear();
        }
    }

    private void bindView() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        grid_category = findViewById(R.id.grid_category);
        relative_grid = findViewById(R.id.relative_grid);
        txt_no_item = findViewById(R.id.txt_no_item);
        subCategoryModels = new ArrayList<>();
        subCategoryAdapter = new SubCategoryAdapter(this, subCategoryModels);
    }

    private void getGridData(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/sub-categories/" + id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.e("Length", String.valueOf(jsonArray.length()));

                            if (jsonArray.length() == 0) {
                                relative_grid.setVisibility(View.GONE);
                                txt_no_item.setVisibility(View.VISIBLE);
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String image = object.getString("image");
                                String discount = object.getString("discount");
                                String discount_by = object.getString("discount_by");
                                String description = object.getString("description");
                                Log.e("description", description);

                                if (discount_by.equalsIgnoreCase("ammount_off")) {

                                    subCategoryModels.add(new SubCategoryModel(id, image, "Rs " + discount + " off", name, description));
                                } else {
                                    subCategoryModels.add(new SubCategoryModel(id, image, discount + "% off", name, description));
                                }

                                grid_category.setAdapter(subCategoryAdapter);
                                subCategoryAdapter.notifyDataSetChanged();
                            }
                            getGridListner();
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
                            Toast.makeText(SubCategory.this, "Some Problem Occured", Toast.LENGTH_SHORT).show();
                        }
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
        progressDialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                }
        }
    }

    public void getGridListner() {
        grid_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String grid_id = subCategoryModels.get(position).getId();
                //Log.e("Gridid", grid_id + "");
                Intent intent = (new Intent(SubCategory.this, Product_Detail_Main.class));
                intent.putExtra("sub_cat_Id", grid_id + "");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }

    }
}
