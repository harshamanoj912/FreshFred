package com.teamevox.freshfred.IT19216492;

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
import com.teamevox.freshfred.IT19208718.GlobalClass;
import com.teamevox.freshfred.IT19208718.MainLogin;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19216492.RecyclerView2.RequestSupplyOrderHome;

import java.io.File;
import java.io.IOException;


public class SupplierInterface extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;
    ImageView DbSupplierImg;


    FirebaseStorage theStorage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_interface);

        btn1 = findViewById(R.id.receivedRequestItem8);
        btn2 = findViewById(R.id.UpdateSupplierAccount);
        btn3 = findViewById(R.id.sypplierMyAccount);
        btn4 = findViewById(R.id.supplierLogOut);
        DbSupplierImg = findViewById(R.id.DbSupplierImg);

        //lad logedi user's image
        GlobalClass global= ( (GlobalClass) getApplicationContext() );
        StorageReference storageReference = theStorage.getReferenceFromUrl("gs://freshfred-sliit.appspot.com").child("suppliers").child(global.getGetLoggedSupplierUsername());


        try {
            final File file = File.createTempFile("img", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    DbSupplierImg.setImageBitmap(bitmap);
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(getApplicationContext(), RequestSupplyOrderHome.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(getBaseContext(), UpdateSupplierDetails.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   startActivity(new Intent(getBaseContext(), DistributedOrders.class));
            }
        });


       btn4.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                Toast toast = Toast.makeText(SupplierInterface.this, "Logging Out", Toast.LENGTH_SHORT);
               toast.show();
                startActivity(new Intent(getBaseContext(), MainLogin.class));
            }
        });






    }




}