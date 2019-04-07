package com.example.foodbasket.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.Home.CustomAdapterHome;
import com.example.foodbasket.Home.GridData;
import com.example.foodbasket.Home.Recycledata;
import com.example.foodbasket.Home.RecyclerAdapterHome;
import com.example.foodbasket.Home.RecyclerItemClickListener;
import com.example.foodbasket.R;
import com.example.foodbasket.SubCategory.SubCategory;
import com.example.foodbasket.Utils.SharedProcessData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    View view;
    ImageView img;
    RecyclerView recycle_view;
    GridView grid_view;
    RecyclerAdapterHome recyclerViewAdapterHome;
    ArrayList<Recycledata> recyclePojos = new ArrayList<>();
    CustomAdapterHome gridAdapter;
    List<GridData> gridlist;
    LinearLayout linearLayout;
    ScrollView scrollView;
    RelativeLayout relative_scroll;
    ViewGroup.LayoutParams layoutParams;
    String image = null, id = null;
    ProgressDialog progressDialog;
    SharedProcessData sharedProcessData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recycleView();
        gridView();
        bindview();
        sharedProcessData = new SharedProcessData(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        getData();
        convert();
        return view;
    }

    private void gridView() {
        grid_view = view.findViewById(R.id.grid_view);
        layoutParams = grid_view.getLayoutParams();
        gridlist = new ArrayList<>();
        gridAdapter = new CustomAdapterHome(getActivity(), gridlist);
    }

    private void recycleView() {
        recycle_view = view.findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycle_view.setLayoutManager(mLayoutManager2);
    }


    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                            Picasso.with(getActivity()).load(viewname).into(img);

                            JSONObject jsonObject2 = jsonArray.getJSONObject(1);
                            String type = jsonObject2.getString("viewType");

                            if (type.equalsIgnoreCase("hRecycleView")) {
                                JSONArray jsonArray1 = jsonObject2.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject object = jsonArray1.getJSONObject(i);
                                    image = object.getString("image");
                                    id = object.getString("id");
                                    Log.e("Id", id);

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recyclePojos.add(new Recycledata(image, Integer.parseInt(id)));
                                            recyclerViewAdapterHome = new RecyclerAdapterHome(recyclePojos, getActivity());
                                            recycle_view.setAdapter(recyclerViewAdapterHome);
                                            recyclerViewAdapterHome.notifyDataSetChanged();
                                        }
                                    });

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
                                getGridListner();
                            }

                           /* if (gridlist.size() <= 3) {
                                layoutParams.height = 400;
                                grid_view.setLayoutParams(layoutParams);
                            } else if (gridlist.size() > 3 && gridlist.size() <= 6) {
                                layoutParams.height = 800;
                                grid_view.setLayoutParams(layoutParams);
                            } else {
                                layoutParams.height = 480;
                                grid_view.setLayoutParams(layoutParams);
                            }*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
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
        progressDialog.show();
    }

    public void getRecycleListner() {
        recycle_view.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
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
                //Log.e("Gridid", grid_id + "");
                Intent intent = (new Intent(getActivity(), SubCategory.class));
                // intent.putExtra("Id", grid_id + "");
                sharedProcessData.setString("BackStatus", "Home");
                sharedProcessData.setString("Id", grid_id + "");
                startActivity(intent);
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }

    private void bindview() {
        img = view.findViewById(R.id.img);
        recycle_view = view.findViewById(R.id.recycle_view);
        grid_view = view.findViewById(R.id.grid_view);
        linearLayout = view.findViewById(R.id.relativeLayout);
        relative_scroll = view.findViewById(R.id.relative_scroll);
    }


    public void convert() {
        float dip = 160f;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        Log.e("Pixel", String.valueOf(px));
    }
}