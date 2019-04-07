package com.example.foodbasket.ForgotPassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.foodbasket.Main.LoginSignupActivity;
import com.example.foodbasket.R;

/**
 * Created by hp on 3/17/2019.
 */

public class NewPassword extends AppCompatActivity {
    ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_password);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewPassword.this, LoginSignupActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

    }
}
