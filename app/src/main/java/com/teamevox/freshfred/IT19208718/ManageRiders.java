package com.teamevox.freshfred.IT19208718;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

public class ManageRiders extends AppCompatActivity {

    RecyclerView riderListRecyclerView;
    private RiderListDisplayAdapter adapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_riders);



        riderListRecyclerView = findViewById(R.id.riderListRecyclerView);
        riderListRecyclerView.setLayoutManager(new LinearLayoutManager(this));




        FirebaseRecyclerOptions<Rider> options2 =
                new FirebaseRecyclerOptions.Builder<Rider>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("riders"), Rider.class)
                        .build();


        adapter1 = new RiderListDisplayAdapter(options2);
        riderListRecyclerView.setAdapter(adapter1);


       // riderListRecyclerView.


    }

    protected  void onStart() {

        super.onStart();

        adapter1.startListening();
    }

    protected  void onStop() {

        super.onStop();

        adapter1.stopListening();
    }








}