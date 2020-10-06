package com.teamevox.freshfred.IT19216492.RecyclerView2;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.IT19208718.GlobalClass;
import com.teamevox.freshfred.IT19216492.RecyclerView2.RequestSupplyOrderHomeAdapter;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19216492.RequestSupplyOrders;

public class RequestSupplyOrderHome extends AppCompatActivity {

    RecyclerView supplierOrderRecyclerView1;
    private RequestSupplyOrderHomeAdapter adapter71;

    private Button nicRefbtn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_supply_order_home);



        supplierOrderRecyclerView1 = findViewById(R.id.supplierOrderRecyclerView1);
        supplierOrderRecyclerView1.setLayoutManager(new LinearLayoutManager(this));

        nicRefbtn2 = findViewById(R.id.nicRefbtn2);

        GlobalClass global= ( (GlobalClass) getApplicationContext() );
        // Firebase.setAndroidContext(this);
        DatabaseReference fbDb = null;
        if (fbDb == null) {
            fbDb = FirebaseDatabase.getInstance().getReference();
        }


        fbDb.child("supplierOrderRequests").child(global.getGetLoggedSupplierUsername())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        int size = (int) dataSnapshot.getChildrenCount();
                        nicRefbtn2.setText(String.valueOf(size));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





        FirebaseRecyclerOptions<RequestSupplyOrders> options =
                new FirebaseRecyclerOptions.Builder<RequestSupplyOrders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("supplierOrderRequests").child(global.getGetLoggedSupplierUsername()), RequestSupplyOrders.class)
                        .build();



       adapter71 = new RequestSupplyOrderHomeAdapter(options, global.getGetLoggedSupplierUsername());
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