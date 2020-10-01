package com.teamevox.freshfred.IT19208718;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

import java.util.Objects;

public class RiderEarningHistory extends AppCompatActivity {

    TextView totalDeliveredItemCount, totalDeliveredEarning, forYou, forOwner;
    ImageView moneyHandOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_earning_history);


        totalDeliveredItemCount = findViewById(R.id.totalDeliveredItemCount);
        totalDeliveredEarning = findViewById(R.id.totalDeliveredEarning);
        forYou = findViewById(R.id.forYou);
        forOwner = findViewById(R.id.forOwner);
        moneyHandOver = findViewById(R.id.moneyHandOver);

        final GlobalClass global= ( (GlobalClass) getApplicationContext() );
        FirebaseDatabase database99 = FirebaseDatabase.getInstance();
        DatabaseReference getCommissionRateNow = database99.getReference("commissionForRiders").child(global.getLoggedRiderUsername());

        getCommissionRateNow.addValueEventListener(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {

                totalDeliveredItemCount.setText("Total Item Delivered : " + Objects.requireNonNull(snapshot.child("totalItemDelivered").getValue()).toString());
                totalDeliveredEarning.setText("Your Total Earnings : " + Objects.requireNonNull(snapshot.child("totalCostOfDeliveredItems").getValue()).toString() + " LKR");
                forYou.setText(Objects.requireNonNull(snapshot.child("totalCommission").getValue()).toString() + " LKR") ;
                forOwner.setText(Objects.requireNonNull(snapshot.child("forOwner").getValue()).toString() + " LKR") ;


                moneyHandOver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Task<Void>  forOwners1 = FirebaseDatabase.getInstance().getReference("totalEarningHistory").child("totalItemDelivered").setValue(ServerValue.increment(Integer.parseInt(Objects.requireNonNull(snapshot.child("totalItemDelivered").getValue()).toString())));
                        Task<Void>  forOwners2 = FirebaseDatabase.getInstance().getReference("totalEarningHistory").child("totalCostOfDeliveredItems").setValue(ServerValue.increment(Double.parseDouble(Objects.requireNonNull(snapshot.child("totalCostOfDeliveredItems").getValue()).toString())));
                        Task<Void>  forOwners3 = FirebaseDatabase.getInstance().getReference("totalEarningHistory").child("totalRiderCommission").setValue(ServerValue.increment(Double.parseDouble(Objects.requireNonNull(snapshot.child("totalCommission").getValue()).toString())));
                        Task<Void>  forOwners4 = FirebaseDatabase.getInstance().getReference("totalEarningHistory").child("forOwner").setValue(ServerValue.increment(Double.parseDouble(Objects.requireNonNull(snapshot.child("forOwner").getValue()).toString())));


                       Task<Void>  forRider1 = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(global.getLoggedRiderUsername()).child("totalItemDelivered").setValue(0);
                       Task<Void>  forRider2 = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(global.getLoggedRiderUsername()).child("totalCostOfDeliveredItems").setValue(0);
                       Task<Void>  forRider3 = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(global.getLoggedRiderUsername()).child("totalCommission").setValue(0);
                       Task<Void>  forRider4 = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(global.getLoggedRiderUsername()).child("forOwner").setValue(0);




                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        moneyHandOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }



}