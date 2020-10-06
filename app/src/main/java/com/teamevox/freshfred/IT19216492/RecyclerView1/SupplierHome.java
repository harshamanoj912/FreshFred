package com.teamevox.freshfred.IT19216492.RecyclerView1;

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
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19216492.Supplier;

public class SupplierHome extends AppCompatActivity {

    RecyclerView supplierListRecyclerView;
    private SupplierListDisplayAdapter adapter1;

    private Button nicRefbtn1;
    private FirebaseDatabase Firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_home);



        supplierListRecyclerView = findViewById(R.id.supplierListRecyclerView);
        supplierListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        nicRefbtn1 = findViewById(R.id.nicRefbtn1);




       // Firebase.setAndroidContext(this);
      DatabaseReference fbDb = null;
        if (fbDb == null) {
            fbDb = FirebaseDatabase.getInstance().getReference();
        }


        fbDb.child("suppliers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        int size = (int) dataSnapshot.getChildrenCount();
                        nicRefbtn1.setText(String.valueOf(size));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





        FirebaseRecyclerOptions<Supplier> options2 =
                new FirebaseRecyclerOptions.Builder<Supplier>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("suppliers"), Supplier.class)
                        .build();


        adapter1 = new SupplierListDisplayAdapter(options2);
        supplierListRecyclerView.setAdapter(adapter1);



        // supplierListRecyclerView.


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