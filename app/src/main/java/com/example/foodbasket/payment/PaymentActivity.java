package com.example.foodbasket.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodbasket.ConfirmOrder.DateTime;
import com.example.foodbasket.R;

public class PaymentActivity extends AppCompatActivity {

    LinearLayout layout_cod;
    TextView txt_price;
    String rate = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment);
        layout_cod = findViewById(R.id.layout_cod);
        txt_price = findViewById(R.id.txt_price);
        rate = getIntent().getStringExtra("rate");
        txt_price.setText(rate + "");

        layout_cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rate.equalsIgnoreCase("") || rate != null) {
                    startActivity(new Intent(PaymentActivity.this, DateTime.class));
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    Toast.makeText(PaymentActivity.this, "Start Shopping", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
