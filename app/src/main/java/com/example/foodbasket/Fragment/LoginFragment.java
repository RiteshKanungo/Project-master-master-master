package com.example.foodbasket.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.ForgotPassword.ForgorPassActivity;
import com.example.foodbasket.Main.MainActivity;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ritesh on 3/13/2019.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    View view;
    Button btn_login;
    ProgressDialog progressDialog;
    String str_email = null, str_password = null;
    EditText edt_email, edt_password;
    TextView txt_forgot_pass;
    SharedProcessData sharedProcessData;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        bindView();
        return view;
    }

    private void bindView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);

        txt_forgot_pass = view.findViewById(R.id.txt_forgot_pass);
        txt_forgot_pass.setOnClickListener(this);

        sharedProcessData = new SharedProcessData(getActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                if (isNetworkConnected()) {
                    str_email = edt_email.getText().toString().trim();
                    str_password = edt_password.getText().toString().trim();

                    if (str_email.equalsIgnoreCase("") || str_email == null) {
                        edt_email.setError("Enter Email id");
                    } else if (str_password.equalsIgnoreCase("") || str_password == null) {
                        edt_password.setError("Enter Password");
                    } else {
                        try {
                            if (isNetworkConnected()) {
                                Login(str_email, str_password);
                            } else {
                                Toast.makeText(getActivity(), "Check Your Network Connectuion", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Connection Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Connect to Network", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.txt_forgot_pass:
                startActivity(new Intent(getActivity(), ForgorPassActivity.class));
                break;
        }
    }

    public void Login(final String email, final String password) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/user/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("message");
                            if (status.equals("login successfully")) {
                                JSONObject object = jsonObj.getJSONObject("data");
                                String data = object.getString("accessToken");
                                sharedProcessData.setString("Token", data);
                                sharedProcessData.setString("email", email);
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().overridePendingTransition(0, 0);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), "Invalid Credential", Toast.LENGTH_SHORT).show();
                                edt_password.setText("");
                                progressDialog.dismiss();
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
                        edt_password.setText("");
                        progressDialog.dismiss();
                     /*   Log.d("Email", email);
                        Log.d("Password", password);
                        Log.d("Error.Response", error.toString());*/
                        Toast.makeText(getContext(), "Invalid Credential", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

