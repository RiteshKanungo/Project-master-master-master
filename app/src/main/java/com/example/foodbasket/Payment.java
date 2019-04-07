package com.example.foodbasket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodbasket.Checkout.CheckoutActivity;
import com.example.foodbasket.ConfirmOrder.DateTime;
import com.example.foodbasket.Utils.SharedProcessData;

public class Payment extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout_cod, layout_paytm, layout_mobi;
    TextView txt_price;
    SharedProcessData sharedProcessData;
    String price = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment);
        sharedProcessData = new SharedProcessData(this);
        price = sharedProcessData.getString("FinalPrice");
        bindView();
    }

    private void bindView() {
        layout_cod = findViewById(R.id.layout_cod);
        layout_cod.setOnClickListener(this);

        layout_paytm = findViewById(R.id.layout_paytm);
        layout_paytm.setOnClickListener(this);

        layout_mobi = findViewById(R.id.layout_mobi);
        layout_mobi.setOnClickListener(this);

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText("MRP " + price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_cod:
                if (!price.equalsIgnoreCase("") || price != null) {
                    startActivity(new Intent(Payment.this, DateTime.class));
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    Toast.makeText(Payment.this, "Start Shopping", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.layout_paytm:
                Toast.makeText(this, "Feature Comming Soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.layout_mobi:
                Toast.makeText(this, "Feature Comming Soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Payment.this, CheckoutActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
