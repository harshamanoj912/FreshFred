package com.teamevox.freshfred;
//IT19208718

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamevox.freshfred.ui.addrider.AddNewRider;

public class OwnerLogin extends AppCompatActivity {


    //get Your button via ID
    Button addNewRiderBtn, addNewSupplierBtn, addNewFoodsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        //findViewById

        addNewRiderBtn = findViewById(R.id.addNewRiderBtn);


        //Set new onClickListener like this

        addNewRiderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewRider.class));
            }
        });





    }
}