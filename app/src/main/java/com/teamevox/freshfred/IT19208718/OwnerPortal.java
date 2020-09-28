package com.teamevox.freshfred.IT19208718;
//IT19208718

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamevox.freshfred.IT19207650.OrderList;
import com.teamevox.freshfred.IT19207650.AddNewFood;
import com.teamevox.freshfred.R;

public class OwnerPortal extends AppCompatActivity {


    //get Your button via ID
    Button addNewRiderBtn, addNewSupplierBtn, addNewFoodsBtn, orderList, manageRiders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_portal);

        //findViewById

        addNewRiderBtn = findViewById(R.id.addNewRiderBtn);
        orderList = findViewById(R.id.orderList4);
        manageRiders = findViewById(R.id.manageRiders);
        addNewFoodsBtn = findViewById(R.id.addNewFoodsBtn);

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

    }
}