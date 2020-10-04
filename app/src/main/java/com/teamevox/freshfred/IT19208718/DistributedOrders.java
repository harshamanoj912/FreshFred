package com.teamevox.freshfred.IT19208718;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.teamevox.freshfred.R;

public class DistributedOrders extends AppCompatActivity {

    RecyclerView distributedORidersByRidersResView;
    private DistributedOrdersByRidersAdapter adapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributed_orders);


        distributedORidersByRidersResView = findViewById(R.id.distributedORidersByRidersResView);
        distributedORidersByRidersResView.setLayoutManager(new LinearLayoutManager(this));

        GlobalClass global= ( (GlobalClass) getApplicationContext() );


        FirebaseRecyclerOptions<OrderListDisplayForRiderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderListDisplayForRiderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("completedOrdersByRiders").child(global.getLoggedRiderUsername()), OrderListDisplayForRiderModel.class)
                        .build();



        adapter4 = new DistributedOrdersByRidersAdapter(options, global.getLoggedRiderUsername());
        distributedORidersByRidersResView.setAdapter(adapter4);


    }


    protected  void onStart() {

        super.onStart();

        adapter4.startListening();
    }

    protected  void onStop() {

        super.onStop();

        adapter4.stopListening();
    }








}