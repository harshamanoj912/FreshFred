package com.teamevox.freshfred.IT19208718;
//IT19208718

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamevox.freshfred.IT19207650.OrderList;
import com.teamevox.freshfred.IT19207650.AddNewFood;
import com.teamevox.freshfred.IT19216492.AddNewSupplier;
import com.teamevox.freshfred.IT19216492.RecyclerView1.SupplierHome;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19208718.TodayIncomeForShop;

public class OwnerPortal extends AppCompatActivity {


    //get Your button via ID
    Button addNewRiderBtn, addNewSupplierBtn, addNewFoodsBtn, orderList, manageRiders, ownerLogout, todayIncome,manageSuppliers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_portal);

        //findViewById
        final GlobalClass global= ( (GlobalClass) getApplicationContext() );
        addNewRiderBtn = findViewById(R.id.addNewRiderBtn);
        orderList = findViewById(R.id.orderList4);
        manageRiders = findViewById(R.id.manageRiders);
        addNewFoodsBtn = findViewById(R.id.addNewFoodsBtn);
        ownerLogout = findViewById(R.id.ownerLogout);
        todayIncome = findViewById(R.id.todayIncome);
        addNewSupplierBtn = findViewById(R.id.addNewSupplierBtn);
        manageSuppliers = findViewById(R.id.manageSuppliers);

        //Set new onClickListener like this

        addNewRiderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewRider.class));
            }
        });


        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderList.class));
            }
        });

        manageRiders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManageRiders.class));
            }
        });


        addNewFoodsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewFood.class));
            }
        });

        ownerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_SHORT).show();
                global.setLoggedRiderUsername(null);

                startActivity(new Intent(getApplicationContext(), MainLogin.class));
            }
        });

        todayIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TodayIncomeForShop.class));
            }
        });

        addNewSupplierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewSupplier.class));
            }
        });

        manageSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SupplierHome.class));
            }
        });



    }
}