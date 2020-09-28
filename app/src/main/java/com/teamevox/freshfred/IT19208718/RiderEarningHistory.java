package com.teamevox.freshfred.IT19208718;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

public class RiderEarningHistory extends AppCompatActivity {

    TextView totalDeliveredItemCount, totalDeliveredEarning, forYou, forOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_earning_history);


        totalDeliveredItemCount = findViewById(R.id.totalDeliveredItemCount);
        totalDeliveredEarning = findViewById(R.id.totalDeliveredEarning);
        forYou = findViewById(R.id.forYou);
        forOwner = findViewById(R.id.forOwner);

        GlobalClass global= ( (GlobalClass) getApplicationContext() );
        FirebaseDatabase database99 = FirebaseDatabase.getInstance();
        DatabaseReference getCommissionRateNow = database99.getReference("commissionForRiders").child(global.getLoggedRiderUsername());

        getCommissionRateNow.addValueEventListener(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                totalDeliveredItemCount.setText("Total Item Delivered : " + snapshot.child("totalItemDelivered").getValue().toString());
                totalDeliveredEarning.setText("Your Total Earnings : " + snapshot.child("totalCostOfDeliveredItems").getValue().toString() + " LKR");
                forYou.setText(snapshot.child("totalCommission").getValue().toString() + " LKR") ;
                forOwner.setText(snapshot.child("forOwner").getValue().toString() + " LKR") ;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}