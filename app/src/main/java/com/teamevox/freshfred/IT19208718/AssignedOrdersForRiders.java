package com.teamevox.freshfred.IT19208718;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

public class AssignedOrdersForRiders extends AppCompatActivity {

    RecyclerView assignedOrdersForMeRider;
    private AssignedOrderListForRiderAdapter adapter3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_orders_for_riders);


        assignedOrdersForMeRider = findViewById(R.id.assignedOrdersForMeRider);
        assignedOrdersForMeRider.setLayoutManager(new LinearLayoutManager(this));

         GlobalClass global= ( (GlobalClass) getApplicationContext() );



        FirebaseDatabase database99 = FirebaseDatabase.getInstance();
        DatabaseReference getCommissionRateNow = database99.getReference("riders").child(global.getLoggedRiderUsername());

        getCommissionRateNow.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String tempRate = snapshot.child("riderCommission").getValue(String.class);
                assert tempRate != null;
                Task<Void> setCommissionAsTemp = FirebaseDatabase.getInstance().getReference("loggedRiderCommission").child("tempRate").setValue(tempRate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        FirebaseRecyclerOptions<OrderListDisplayForRiderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderListDisplayForRiderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("assignedOrders").child(global.getLoggedRiderUsername()), OrderListDisplayForRiderModel.class)
                        .build();


        adapter3 = new AssignedOrderListForRiderAdapter(options, global.getLoggedRiderUsername());
        assignedOrdersForMeRider.setAdapter(adapter3);

    }

    protected  void onStart() {

        super.onStart();

        adapter3.startListening();
    }

    protected  void onStop() {

        super.onStop();

        adapter3.stopListening();
    }




}