package com.example.foodbasket.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodbasket.Main.MainActivity;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ritesh on 3/3/2019.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener {
    View view;
    Button btn_signup;
    EditText edt_first_name, edt_last_name, edt_email, edt_password, edt_confirm_password, edt_flat_no;
    String str_first_name, str_last_name, str_email, str_passwpord, str_confirm_password_, str_building;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedProcessData sharedProcessData;
    Spinner spinner;
    ProgressDialog progressDialog;
    ArrayList<String> spinnerModelArrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_fragment, container, false);
        bindView();
        SetSpinner();
        getSpinnerData();
        return view;
    }

    private void SetSpinner() {

        spinner = view.findViewById(R.id.spinner);

        spinnerModelArrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerModelArrayList);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spinner.getSelectedItem().toString();
                String[] parts = value.split(",");
                // Toast.makeText(getActivity(), "" + parts[1], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void bindView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        btn_signup = view.findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        edt_first_name = view.findViewById(R.id.edt_first_name);
        edt_last_name = view.findViewById(R.id.edt_last_name);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        edt_confirm_password = view.findViewById(R.id.edt_confirm);
        edt_flat_no = view.findViewById(R.id.edt_flat_no);


        spinner = view.findViewById(R.id.spinner);
        spinner.setPrompt("Select");
        sharedProcessData = new SharedProcessData(getActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                String value = spinner.getSelectedItem().toString();
                String[] parts = value.split(",");
                Toast.makeText(getActivity(), "" + parts[1], Toast.LENGTH_SHORT).show();
                RegistrationCredential(parts[1]);
                break;
        }
    }

    private void RegistrationCredential(String flat_id) {
        try {
            str_first_name = edt_first_name.getText().toString().trim();
            str_last_name = edt_last_name.getText().toString().trim();
            str_email = edt_email.getText().toString().trim();
            str_passwpord = edt_password.getText().toString();
            str_confirm_password_ = edt_confirm_password.getText().toString();
            str_building = edt_flat_no.getText().toString();

            if (str_first_name.equalsIgnoreCase("") || str_last_name == null) {
                edt_first_name.setError("Enter First Name");
            } else if (str_last_name.equalsIgnoreCase("") || str_last_name == null) {
                edt_last_name.setError("Enter Last Name");
            } else if (str_email.equalsIgnoreCase("") || str_email == null) {
                edt_email.setError("Enter Email Address");
            } else if (!str_email.matches(emailPattern)) {
                edt_email.setError("Enter Valid Email Address");
            } else if (str_passwpord.equalsIgnoreCase("") || str_passwpord == null) {
                edt_password.setError("Enter Password");
            } else if (str_confirm_password_.equalsIgnoreCase("") || str_confirm_password_ == null) {
                edt_confirm_password.setError("Confirm Password");
            } else if (str_building.equalsIgnoreCase("") || str_building == null) {
                edt_flat_no.setError("Enter Building no");
            } else if (str_passwpord.equalsIgnoreCase(str_confirm_password_)) {
                String name = str_first_name + "" + str_last_name;
                if (isNetworkConnected()) {
                    Registration(name, str_email, str_passwpord, flat_id, str_building);
                } else {
                    Toast.makeText(getActivity(), "Check your Network Connection", Toast.LENGTH_SHORT).show();
                }
            } else {
                edt_password.setText("");
                edt_confirm_password.setText("");
                Toast.makeText(getActivity(), "Password Not Matched", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("Exception", e + "");
        }
    }

    private void getSpinnerData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/address/buildings", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response ", response);
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        spinnerModelArrayList.add(name + "," + id);
                        arrayAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.e("Error", e + "");
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
        progressDialog.show();
    }


    public void Registration(final String name, final String email, final String password, final String flat_id, final String building) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ec2-18-217-123-54.us-east-2.compute.amazonaws.com/api/user/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response ", response);
                    JSONObject object = new JSONObject(response);
                    if (object.has("access_token")) {
                        String token = object.getString("access_token");
                        sharedProcessData.setString("Token", token);
                        sharedProcessData.setString("email", email);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().overridePendingTransition(0, 0);
                        getActivity().finish();

                    }
                    if (object.has("message")) {
                        JSONObject jsonObject = object.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("email");
                        Toast.makeText(getActivity(), "" + jsonArray.getString(0), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("Error", e + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Some Problem Occured", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("flat_no", building);
                params.put("building", flat_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
