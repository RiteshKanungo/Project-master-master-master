package com.example.foodbasket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.foodbasket.Cart.Data;
import com.example.foodbasket.Cart.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 3/13/2019.
 */

public class Test extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView img_view;
    public List<Data> dataArrayList = new ArrayList<>();
    public RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cart_fragment);
       /* recyclerView = findViewById(R.id.recycle_view);

        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        mAdapter = new RecyclerViewAdapter(this, dataArrayList);
        recyclerView.setAdapter(mAdapter);
        prepareData();
    }

    private void prepareData() {
        dataArrayList.add(new Data(R.drawable.imagebottom));
        dataArrayList.add(new Data(R.drawable.imagetop));
        dataArrayList.add(new Data(R.drawable.imagebottom));
        dataArrayList.add(new Data(R.drawable.imagetop));
        dataArrayList.add(new Data(R.drawable.imagebottom));
        dataArrayList.add(new Data(R.drawable.imagetop));
        mAdapter.notifyDataSetChanged();
    }
*/
    }
}
