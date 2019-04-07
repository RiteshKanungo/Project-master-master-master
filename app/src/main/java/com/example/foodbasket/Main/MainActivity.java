package com.example.foodbasket.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.Checkout.CheckoutActivity;
import com.example.foodbasket.Fragment.CategoriesFragment;
import com.example.foodbasket.Fragment.HomeFragment;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;
import com.example.foodbasket.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    SharedProcessData sharedProcessData;
    String email = null, token = null;
    FragmentTransaction fragmentTransaction;
    TextView txt_login, txt_signup;
    DrawerLayout drawer, drawer2;
    ImageView img;

    int k = 0;
    String name, user_email, address;
    TextView txt_name, txt_email, txt_address;
    ImageView img_edit;
    RelativeLayout layout1, layout2, layout3, layout4, layout5;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedProcessData = new SharedProcessData(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        try {
            email = sharedProcessData.getString("email");
            token = sharedProcessData.getString("Token");
            if (email == null || token.equalsIgnoreCase("Null")) {
                getNormalView();
            } else {
                getLoginView();
                getProfileData(token);
            }
        } catch (Exception e) {
            Log.e("Exception", e + "");
        }
        bindView();
    }

    private void bindView() {
        layout1 = findViewById(R.id.layout1);
        layout1.setOnClickListener(this);

        layout2 = findViewById(R.id.layout2);
        layout2.setOnClickListener(this);

        layout3 = findViewById(R.id.layout3);
        layout3.setOnClickListener(this);

        layout4 = findViewById(R.id.layout4);
        layout4.setOnClickListener(this);

        layout5 = findViewById(R.id.layout5);
        layout5.setOnClickListener(this);
    }

    private void getProfileData(final String token) {
        Log.e("Token", token);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/user";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ProfileData", response);
                        try {
                            Log.e("Response", response + "");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject user = data.getJSONObject("user");
                            name = user.getString("name");
                            user_email = user.getString("email");
                            JSONArray jsonArray = user.getJSONArray("address");
                            JSONObject address = jsonArray.getJSONObject(0);
                            String flat = address.getString("address");
                            String building = address.getString("building_apartment");
                            txt_email.setText(email);
                            txt_name.setText(name);
                            txt_address.setText(flat + "," + building);

                            sharedProcessData.setString("name", name);
                            sharedProcessData.setString("email", email);
                            sharedProcessData.setString("address", flat + "," + building);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error + "");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        queue.add(postRequest);
        progressDialog.show();
    }

    @Override
    protected void onResume() {
//        gridlist.clear();
//        recyclePojos.clear();
        if (isNetworkConnected()) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
            fragmentTransaction.commit();
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(this, "Connect to Network", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }


    private void getLoginView() {
        setContentView(R.layout.activity_main2);
        Log.e("Email", email);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        drawer2 = findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer2, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer2.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        txt_email = hView.findViewById(R.id.txt_mail);
        txt_name = hView.findViewById(R.id.txt_user_name);
        txt_address = hView.findViewById(R.id.txt_address);
        img_edit = hView.findViewById(R.id.img_edit);

        k = 1;
    }

    private void getNormalView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        k = 2;
        txt_login = headerView.findViewById(R.id.txt_login);
        txt_login.setOnClickListener(this);

        txt_signup = headerView.findViewById(R.id.txt_signup);
        txt_signup.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        try {
            if (k == 2) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            } else if (k == 2) {
                {
                    if (drawer2.isDrawerOpen(GravityCompat.START)) {
                        drawer2.closeDrawer(GravityCompat.START);
                    } else {
                        super.onBackPressed();
                    }
                }
            } else {
                finish();
            }
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.cart) {
            startActivity(new Intent(this, CheckoutActivity.class));
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.my_orders) {

        } else if (id == R.id.offers) {
            Intent intent = new Intent(this, WebViewFragment.class);
            intent.putExtra("url", "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/offers");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.about_us) {
            Intent intent = new Intent(this, WebViewFragment.class);
            intent.putExtra("url", "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/aboutUs");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.customer_support) {
            Intent intent = new Intent(this, WebViewFragment.class);
            intent.putExtra("url", "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/custmerSupport");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.faq) {
            Intent intent = new Intent(this, WebViewFragment.class);
            intent.putExtra("url", "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/faq");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.tc) {
            Intent intent = new Intent(this, WebViewFragment.class);
            intent.putExtra("url", "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/tnc");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.logout) {
            sharedProcessData.setString("Token", "Null");
            sharedProcessData.setString("email", "");
            sharedProcessData.setString("address", "");
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        if (k == 1) {
            drawer2.closeDrawer(GravityCompat.START);
        } else {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_login:
                sharedProcessData.setString("status", "login");
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(this, LoginSignupActivity.class));
                    finish();
                }
                break;

            case R.id.txt_signup:
                sharedProcessData.setString("status", "signup");
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(this, LoginSignupActivity.class));
                    finish();
                }
                break;

            case R.id.layout2:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new CategoriesFragment());
                fragmentTransaction.commit();
                overridePendingTransition(0, 0);
                break;

            case R.id.layout3:
                break;

            case R.id.layout4:
             /*  fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                fragmentTransaction.commit();
                overridePendingTransition(0, 0);*/
                break;

            case R.id.layout5:
                startActivity(new Intent(this, CheckoutActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.layout1:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                fragmentTransaction.commit();
                overridePendingTransition(0, 0);
                break;

        }
    }
}
