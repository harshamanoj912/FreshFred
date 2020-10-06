package com.teamevox.freshfred.IT19216492;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamevox.freshfred.IT19208718.GlobalClass;
import com.teamevox.freshfred.R;

import java.io.File;
import java.io.IOException;

public class UpdateSupplierDetails extends AppCompatActivity {

    EditText supplierName11, supplierNic11, supplierMobile11, supplierItem11, supplierPassword11;
    Button updateButton11, deleteSupplierAccount1;
    ImageView supplierProfilePictureUpdated1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseSupplier = database.getReference("suppliers");
    FirebaseStorage storage1;
    StorageReference storageReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_supplier_details);

        supplierName11 =  findViewById(R.id.supplierNameUP);
        supplierNic11 =  findViewById(R.id.supplierNicUP);
        supplierMobile11 =  findViewById(R.id.supplierMobileUP);
        supplierItem11 =  findViewById(R.id.supplierItemUP);
        supplierPassword11 =  findViewById(R.id.supplierPasswordUP);
        updateButton11 = findViewById(R.id.updateButton);
        deleteSupplierAccount1 = findViewById(R.id.deleteSupplierAccount);
        supplierProfilePictureUpdated1 = findViewById(R.id.supplierProfilePictureUpdated);



        storage1 = FirebaseStorage.getInstance();
        storageReference1 = storage1.getReference();


       GlobalClass global= ( (GlobalClass) getApplicationContext() );
       StorageReference storageReference = storage1.getReferenceFromUrl("gs://freshfred-sliit.appspot.com").child("suppliers").child(global.getGetLoggedSupplierUsername());

        try {
            final File file = File.createTempFile("img", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    supplierProfilePictureUpdated1.setImageBitmap(bitmap);
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }


        getValues();


        updateButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateSupplierDetails();
            }
        });

        deleteSupplierAccount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteSupplierAccount();
            }
        });

    }


    public void deleteSupplierAccount(){

        String supplierNicForDelete = supplierNic11.getText().toString();

        if( !TextUtils.isEmpty(supplierNicForDelete)  )
        {

            Query deleteQuery = databaseSupplier.orderByChild("suppliers").equalTo(supplierNicForDelete);

            deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });

            Toast toast = Toast.makeText(this, "Account is deleted", Toast.LENGTH_SHORT);
            toast.show();

            setContentView(R.layout.activity_main);


        }else {

            Toast toast = Toast.makeText(this, "Please fill all the fields..!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


    public void updateSupplierDetails(){

        String theSupplierName = supplierName11.getText().toString();
        String theSupplierNic =  supplierNic11.getText().toString();
        String theSupplierMobile = supplierMobile11.getText().toString();
        String theSupplierItem = supplierItem11.getText().toString();
        String theSupplierPassword = supplierPassword11.getText().toString();


        if(!TextUtils.isEmpty(theSupplierPassword) && !TextUtils.isEmpty(theSupplierNic) && !TextUtils.isEmpty(theSupplierName) && !TextUtils.isEmpty(theSupplierMobile) && !TextUtils.isEmpty(theSupplierItem) ) //
        {

            Supplier supplier = new Supplier(theSupplierName, theSupplierNic, theSupplierMobile, theSupplierItem, theSupplierPassword);
            databaseSupplier.child(theSupplierNic).setValue(supplier);
            Toast toast = Toast.makeText(this, "Updating", Toast.LENGTH_SHORT);
            toast.show();
        }else {

            Toast toast = Toast.makeText(this, "Please fill all the fields..!", Toast.LENGTH_SHORT);
            toast.show();
        }


    }


    public void getValues() {


        GlobalClass global= ( (GlobalClass) getApplicationContext() );

        Query deleteQuery = databaseSupplier.orderByChild("supplierNic").equalTo(global.getGetLoggedSupplierUsername());

        deleteQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot ) {

                for(DataSnapshot supplierSnapshot : snapshot.getChildren()){



                    String name = supplierSnapshot.child("supplierName").getValue().toString();
                    String nic = supplierSnapshot.child("supplierNic").getValue().toString();
                    String mobile = supplierSnapshot.child("supplierMobile").getValue().toString();
                    String item = supplierSnapshot.child("supplierItem").getValue().toString();
                    String password = supplierSnapshot.child("supplierPassword").getValue().toString();

                    supplierName11.setText(name);
                    supplierNic11.setText(nic);
                    supplierMobile11.setText(mobile);
                    supplierItem11.setText(item);
                    supplierPassword11.setText(password);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}