package com.teamevox.freshfred.IT19208718;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.teamevox.freshfred.IT19208718.OrderListDisplayForRiderAdapter;
import com.teamevox.freshfred.IT19208718.OrderListDisplayForRiderModel;
import com.teamevox.freshfred.R;

public class OrdersForDistribution extends AppCompatActivity {

    RecyclerView orderListRecyclerView;
    OrderListDisplayForRiderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_for_distribution);


        orderListRecyclerView = findViewById(R.id.orderListRecyclerView);
        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<OrderListDisplayForRiderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderListDisplayForRiderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("orders"), OrderListDisplayForRiderModel.class)
                        .build();



        adapter = new OrderListDisplayForRiderAdapter(options);
        orderListRecyclerView.setAdapter(adapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}