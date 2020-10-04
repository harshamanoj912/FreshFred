package com.teamevox.freshfred.IT19216492;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamevox.freshfred.R;

import java.util.Random;

public class RequestSupply extends AppCompatActivity {

    EditText supplierItemName1, supplierOrderQuantity1, supplierOrderAddress1, supplierOrderMobile1;
    Button sendToSuppliers1;
    
String niccc;
    public Uri imgUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseRequestSupply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_supply);


        supplierItemName1 =  findViewById(R.id.itemName);
        supplierOrderQuantity1 =  findViewById(R.id.orderQuantity);
        supplierOrderAddress1 =  findViewById(R.id.orderAddress);
        supplierOrderMobile1 =  findViewById(R.id.orderMobile);
        sendToSuppliers1 = findViewById(R.id.sendToSuppliers);


        Intent intent = getIntent();
        niccc = intent.getStringExtra("nic001");
        

        databaseRequestSupply = FirebaseDatabase.getInstance().getReference("supplierOrderRequests");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        sendToSuppliers1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequestSupplyOrders();
            }
        });
        

    }

    private void addRequestSupplyOrders(){

        String thesupplierItemName = supplierItemName1.getText().toString();
        String thesupplierOrderQuantity =  supplierOrderQuantity1.getText().toString();
        String thesupplierOrderAddress = supplierOrderAddress1.getText().toString();
        String thesupplierOrderMobile = supplierOrderMobile1.getText().toString();


                Random r = new Random();
                int low = 10000;
                int high = 100000;
                int result = r.nextInt(high-low) + low;

        

        if(!TextUtils.isEmpty(thesupplierOrderQuantity) && !TextUtils.isEmpty(thesupplierItemName) && !TextUtils.isEmpty(thesupplierOrderAddress) && !TextUtils.isEmpty(thesupplierOrderMobile) )
        {

            RequestSupplyOrders requestSupplyOrders = new RequestSupplyOrders (thesupplierItemName, thesupplierOrderQuantity, thesupplierOrderAddress , thesupplierOrderMobile);
            databaseRequestSupply.child(niccc).child(String.valueOf(result)).setValue(requestSupplyOrders);
          //  uploadPicture();
            Toast toast = Toast.makeText(this, "sending New Order", Toast.LENGTH_SHORT);
            toast.show();

        }else {

            Toast toast = Toast.makeText(this, "Please fill all the fields..!", Toast.LENGTH_SHORT);
            toast.show();
        }



    }




}