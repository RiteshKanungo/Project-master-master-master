package com.example.foodbasket.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.foodbasket.Cart.Data;
import com.example.foodbasket.Cart.RecyclerViewAdapter;
import com.example.foodbasket.Home.RecyclerItemClickListener;
import com.example.foodbasket.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 3/13/2019.
 */

public class CartFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ImageView img_view;
    public List<Data> dataArrayList = new ArrayList<>();
    public RecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment, container, false);

        bindView();
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        mAdapter = new RecyclerViewAdapter(getActivity(), dataArrayList);
        recyclerView.setAdapter(mAdapter);
        prepareData();


        return view;
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

    private void bindView() {
        img_view = view.findViewById(R.id.img_view);
        recyclerView = view.findViewById(R.id.recycle_view);
    }


}
