package com.example.foodbasket.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.CategoryListing.Adaptermain;
import com.example.foodbasket.CategoryListing.SubCategoryModelMain;
import com.example.foodbasket.Home.RecyclerItemClickListener;
import com.example.foodbasket.R;
import com.example.foodbasket.SubCategory.SubCategory;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 3/13/2019.
 */

public class CategoriesFragment extends Fragment implements View.OnClickListener {

    View view;
    RecyclerView recycle_view;
    ArrayList<SubCategoryModelMain> subCategoryModelMains;
    Adaptermain subCategoryAdapterMain;
    ImageView img_back;
    String id;
    SharedProcessData sharedProcessData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_category, container, false);
        bindView();
        return view;
    }

    private void bindView() {
        sharedProcessData = new SharedProcessData(getActivity());
        //      img_back = findViewById(R.id.img_back);
        //      img_back.setOnClickListener(this);
        recycle_view = view.findViewById(R.id.recycle_view);
        recycle_view.setNestedScrollingEnabled(false);
        subCategoryModelMains = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
        subCategoryAdapterMain = new Adaptermain(subCategoryModelMains, getActivity());
        getGridData();
    }

    private void getGridData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                                String id = object.getString("id");

                                String name = object.getString("name");
                                String image = object.getString("image");
                                String discount = object.getString("discount");
                                String discount_by = object.getString("discount_by");
                                String description = object.getString("description");
                                Log.e("description", description);

                                if (discount_by.equalsIgnoreCase("ammount_off")) {
                                    subCategoryModelMains.add(new SubCategoryModelMain(id, image, "UP TO " + discount + " off", name, description));
                                } else {
                                    subCategoryModelMains.add(new SubCategoryModelMain(id, image, "UP TO " + discount + "% off", name, description));
                                }
                                recycle_view.setAdapter(subCategoryAdapterMain);
                                subCategoryAdapterMain.notifyDataSetChanged();
                            }

                            getRecycleListner();
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

    public void getRecycleListner() {
        recycle_view.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String grid_id = subCategoryModelMains.get(position).getId();
                //Log.e("Gridid", grid_id + "");
                Intent intent = (new Intent(getActivity(), SubCategory.class));

                //intent.putExtra("Id", grid_id + "");
                sharedProcessData.setString("BackStatus", "Categories");
                sharedProcessData.setString("Id", grid_id + "");
                startActivity(intent);

               /* startActivity(new Intent(getActivity(), Product_Detail_Main.class));
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();*/
                //  int recycle_id =subCategoryModelMains .get(position).getId();
                //    Log.e("Postion", recycle_id + "");
            }
        }));
    }
}
