package com.teamevox.freshfred.IT19208718;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamevox.freshfred.R;

import java.io.File;
import java.io.IOException;

public class RiderPortal extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6 ;
    ImageView loadRiderDP1;


    FirebaseStorage theStorage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_portal);

        btn1 = findViewById(R.id.OrdersForDistributionButton);
        btn2 = findViewById(R.id.earningHistory);
        btn3 = findViewById(R.id.DistributedOrdersButton);
        btn4 = findViewById(R.id.MyAccountRiderButton);
        btn5 = findViewById(R.id.riderLogOutButton);
        btn6 = findViewById(R.id.assignedOrdersForMe);
        loadRiderDP1 = findViewById(R.id.loadRiderDP);

        //lad logged user's image
        final GlobalClass global= ( (GlobalClass) getApplicationContext() );
        StorageReference storageReference = theStorage.getReferenceFromUrl("gs://freshfred-sliit.appspot.com").child("riders").child(global.getLoggedRiderUsername());


        try {
            final File file = File.createTempFile("img", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    loadRiderDP1.setImageBitmap(bitmap);
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), OrdersForDistribution.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), RiderEarningHistory.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), DistributedOrders.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), UpdateRiderDetails.class));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(RiderPortal.this, "Logging Out", Toast.LENGTH_SHORT);
                toast.show();
                global.setLoggedRiderUsername(null);
                startActivity(new Intent(getBaseContext(), MainLogin.class));
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), AssignedOrdersForRiders.class));
            }
        });




    }




}