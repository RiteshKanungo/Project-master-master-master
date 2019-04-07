package com.example.foodbasket.Main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foodbasket.Fragment.LoginFragment;
import com.example.foodbasket.Fragment.SignUpFragment;
import com.example.foodbasket.R;
import com.example.foodbasket.Utils.SharedProcessData;

/**
 * Created by Ritesh on 3/13/2019.
 */

public class LoginSignupActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_login, txt_signup;
    FragmentTransaction fragmentTransaction;
    RelativeLayout layout_session;
    LinearLayout layout;
    SharedProcessData sharedProcessData;
    String status = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

      /*  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_login);
        sharedProcessData = new SharedProcessData(this);
        layout = findViewById(R.id.layout);

        txt_login = findViewById(R.id.txt_login);
        txt_login.setOnClickListener(this);

        txt_signup = findViewById(R.id.txt_signup);
        txt_signup.setOnClickListener(this);

        layout_session = findViewById(R.id.layout_session);

        status = sharedProcessData.getString("status");

        if (status.equalsIgnoreCase("signup")) {
            txt_signup.setBackgroundColor(getResources().getColor(R.color.colorbackground));
            txt_signup.setTextColor(getResources().getColor(R.color.colorWhite));
            txt_login.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            txt_login.setTextColor(getResources().getColor(R.color.colorbackground));
            txt_login.setBackground(getResources().getDrawable(R.drawable.layout_backgroud));
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout_session, new SignUpFragment());
            fragmentTransaction.commit();
        } else {
            txt_login.setBackgroundColor(getResources().getColor(R.color.colorbackground));
            txt_signup.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            txt_login.setTextColor(getResources().getColor(R.color.colorWhite));
            txt_signup.setTextColor(getResources().getColor(R.color.colorbackground));
            txt_signup.setBackground(getResources().getDrawable(R.drawable.layout_backgroud));
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout_session, new LoginFragment());
            fragmentTransaction.commit();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login:

                txt_login.setBackgroundColor(getResources().getColor(R.color.colorbackground));
                txt_signup.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                txt_login.setTextColor(getResources().getColor(R.color.colorWhite));
                txt_signup.setTextColor(getResources().getColor(R.color.colorbackground));
                txt_signup.setBackground(getResources().getDrawable(R.drawable.layout_backgroud));
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_session, new LoginFragment());
                fragmentTransaction.commit();
                break;

            case R.id.txt_signup:

                txt_signup.setBackgroundColor(getResources().getColor(R.color.colorbackground));
                txt_signup.setTextColor(getResources().getColor(R.color.colorWhite));
                txt_login.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                txt_login.setTextColor(getResources().getColor(R.color.colorbackground));
                txt_login.setBackground(getResources().getDrawable(R.drawable.layout_backgroud));
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_session, new SignUpFragment());
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
