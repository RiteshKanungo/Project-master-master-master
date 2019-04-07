package com.example.foodbasket.ForgotPassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.foodbasket.R;

/**
 * Created by ritesh on 3/17/2019.
 */

public class ForgorPassActivity extends AppCompatActivity {
    Button btn_contiune;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pass_forgot);
        btn_contiune = findViewById(R.id.btn_contiune);

        btn_contiune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgorPassActivity.this, NewPassword.class));
                finish();
            }
        });
    }
}
