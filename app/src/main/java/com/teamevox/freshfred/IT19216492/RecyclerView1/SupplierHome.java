package com.teamevox.freshfred.IT19216492.RecyclerView1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19216492.Supplier;

public class SupplierHome extends AppCompatActivity {

    RecyclerView supplierListRecyclerView;
    private SupplierListDisplayAdapter adapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_home);



        supplierListRecyclerView = findViewById(R.id.supplierListRecyclerView);
        supplierListRecyclerView.setLayoutManager(new LinearLayoutManager(this));




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