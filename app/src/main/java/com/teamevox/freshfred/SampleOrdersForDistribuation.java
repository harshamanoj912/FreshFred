package com.teamevox.freshfred;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SampleOrdersForDistribuation extends AppCompatActivity {


    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_orders_for_distribuation);

        orders = new ArrayList<>();



        recyclerView = findViewById(R.id.recView);

    }
}