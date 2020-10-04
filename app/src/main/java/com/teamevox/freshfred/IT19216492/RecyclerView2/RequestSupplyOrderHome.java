package com.teamevox.freshfred.IT19216492.RecyclerView2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19216492.RequestSupplyOrders;

public class RequestSupplyOrderHome extends AppCompatActivity {

    RecyclerView supplierOrderRecyclerView1;
    private RequestSupplyOrderHomeAdapter adapter71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_supply_order_home);



        supplierOrderRecyclerView1 = findViewById(R.id.supplierOrderRecyclerView1);
        supplierOrderRecyclerView1.setLayoutManager(new LinearLayoutManager(this));




        FirebaseRecyclerOptions<RequestSupplyOrders> options =
                new FirebaseRecyclerOptions.Builder<RequestSupplyOrders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("supplierOrderRequests").child("154267845V"), RequestSupplyOrders.class)
                        .build();


       adapter71 = new RequestSupplyOrderHomeAdapter(options);
        supplierOrderRecyclerView1.setAdapter(adapter71);


        // OrderListRecyclerView.


    }

    protected  void onStart() {

        super.onStart();

       adapter71.startListening();
    }

    protected  void onStop() {

        super.onStop();

       adapter71.stopListening();
    }








}