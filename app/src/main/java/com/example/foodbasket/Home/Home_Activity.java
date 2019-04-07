package com.example.foodbasket.Home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home_Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    RecyclerView recycle_view;
    ExpandableHeightGridView grid_view;
    RecyclerAdapterHome recyclerViewAdapterHome;
    ArrayList<Recycledata> recyclePojos = new ArrayList<>();
    CustomAdapterHome gridAdapter;
    ArrayList<GridData> gridlist;
    String id = null;
    CardView card_grid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main_layout);
        img = findViewById(R.id.img);
        recycleView();
        gridView();
        bindview();
        card_grid = findViewById(R.id.card_grid);
        if (isNetworkConnected()) {
            getData();
        } else {
            Toast.makeText(this, "Coonect to Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void gridView() {
        grid_view = (ExpandableHeightGridView) findViewById(R.id.grid_view);
        grid_view.setExpanded(true);

        gridlist = new ArrayList<>();
        gridAdapter = new CustomAdapterHome(Home_Activity.this, gridlist);
    }

    private void recycleView() {
        recycle_view = findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/home";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String viewname = jsonObject1.getString("link");
                            Picasso.with(Home_Activity.this).load(viewname).into(img);

                            JSONObject jsonObject2 = jsonArray.getJSONObject(1);
                            String type = jsonObject2.getString("viewType");

                            if (type.equalsIgnoreCase("hRecycleView")) {
                                JSONArray jsonArray1 = jsonObject2.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject object = jsonArray1.getJSONObject(i);
                                    String image = object.getString("image");
                                    String id = object.getString("id");
                                    Log.e("Id", id);
                                    recyclePojos.add(new Recycledata(image, Integer.parseInt(id)));
                                    recyclerViewAdapterHome = new RecyclerAdapterHome(recyclePojos, Home_Activity.this);
                                    recycle_view.setAdapter(recyclerViewAdapterHome);
                                    recyclerViewAdapterHome.notifyDataSetChanged();
                                }
                                getRecycleListner();
                            }

                            JSONObject jsonObject3 = jsonArray.getJSONObject(2);
                            String type2 = jsonObject3.getString("viewType");
                            if (type2.equalsIgnoreCase("grid3View")) {
                                JSONArray array = jsonObject3.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject4 = array.getJSONObject(i);
                                    String img_url = jsonObject4.getString("image");
                                    String name = jsonObject4.getString("name");
                                    String id = jsonObject4.getString("id");
                                    Log.e("name", name);
                                    gridlist.add(new GridData(img_url, name, Integer.parseInt(id)));
                                    grid_view.setAdapter(gridAdapter);
                                    gridAdapter.notifyDataSetChanged();
                                }
                                if (gridlist.isEmpty()) {
                                    card_grid.setVisibility(View.GONE);
                                } else {
                                    card_grid.setVisibility(View.VISIBLE);
                                }
                                getGridListner();
                            }

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
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void getRecycleListner() {
        recycle_view.addOnItemTouchListener(new RecyclerItemClickListener(Home_Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int recycle_id = recyclePojos.get(position).getId();
                Log.e("Postion", recycle_id + "");
            }
        }));
    }

    public void getGridListner() {
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int grid_id = gridlist.get(position).getId();
                Log.e("Gridid", grid_id + "");
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }

    private void bindview() {
        img = findViewById(R.id.img);
        recycle_view = findViewById(R.id.recycle_view);
        grid_view = findViewById(R.id.grid_view);
    }

    @Override
    public void onClick(View view) {
    }
}
