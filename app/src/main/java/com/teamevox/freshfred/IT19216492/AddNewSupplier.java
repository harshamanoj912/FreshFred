package com.teamevox.freshfred.IT19216492;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamevox.freshfred.R;

import java.util.UUID;

public class AddNewSupplier extends AppCompatActivity {

    EditText supplierName1, supplierNic1, supplierMobile1, supplierItem1, supplierPassword1;
    Button addNewSupplier1;
    ImageView supplierProfilePicture;

    public Uri imgUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_supplier);

        supplierName1 =  findViewById(R.id.supplierName);
        supplierNic1 =  findViewById(R.id.supplierNic);
        supplierMobile1 =  findViewById(R.id.supplierMobile);
        supplierItem1 =  findViewById(R.id.supplierItem);
        supplierPassword1 =  findViewById(R.id.supplierPassword);
        addNewSupplier1 = findViewById(R.id.addNewSupplier);
        supplierProfilePicture = findViewById(R.id.supplierProfilePicture);

        databaseSupplier = FirebaseDatabase.getInstance().getReference("suppliers");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        addNewSupplier1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUserName() | !validatePassword() | !validateNic() | !validateMobile()){
                    return;
                }

                 addSupplier();
            }
        });

        supplierProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
            }
        });







    }

    private void addSupplier(){

        String theSupplierName = supplierName1.getText().toString();
        String theSupplierNic =  supplierNic1.getText().toString();
        String theSupplierMobile = supplierMobile1.getText().toString();
        String theSupplierItem = supplierItem1.getText().toString();
        String theSupplierPassword = supplierPassword1.getText().toString();




        if(!TextUtils.isEmpty(theSupplierPassword) && !TextUtils.isEmpty(theSupplierNic) && !TextUtils.isEmpty(theSupplierName) && !TextUtils.isEmpty(theSupplierMobile) && !TextUtils.isEmpty(theSupplierItem) ) //
        {

            Supplier supplier = new Supplier (theSupplierName, theSupplierNic, theSupplierMobile , theSupplierItem, theSupplierPassword);
            databaseSupplier.child(theSupplierNic).setValue(supplier);
            uploadPicture();
            Toast toast = Toast.makeText(this, "Adding New Supplier", Toast.LENGTH_SHORT);
            toast.show();

        }else {

            Toast toast = Toast.makeText(this, "Please fill all the fields..!", Toast.LENGTH_SHORT);
            toast.show();
        }



    }


    private void chooseProfilePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode== Activity.RESULT_OK && data!=null && data.getData()!=null){
            imgUrl = data.getData();
            supplierProfilePicture.setImageURI(imgUrl);

        }
    }

    private void uploadPicture() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();
        String theSupplierNic = supplierNic1.getText().toString();


        //rename the image with supplier name
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("suppliers/" + theSupplierNic);

        riversRef.putFile(imgUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Supplier Successfully Added", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload..!", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double prograssPrecentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Progress : " + (int) prograssPrecentage + "%");
                    }
                });


    }
    private boolean validateNic() {
        String nic = supplierNic1.getText().toString();
        String NicPattern = "^[0-9]{9}[vVxX]$";

        if (nic.isEmpty()) {
            supplierNic1.setError("Field Cannot be empty");
            return false;
        }
        else if(!nic.matches(NicPattern)){
            supplierNic1.setError("Nic Pattern Doesn't Match");
            return false;
        }
        else {
            supplierNic1.setError(null);
            return true;
        }
    }

    private boolean validateUserName() {
        String un = supplierName1.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (un.isEmpty()) {
            supplierName1.setError("Field Cannot be empty");
            return false;
        }
        else if(un.length() >= 15){
            supplierName1.setError("Field Must use only 15 characters");
            return false;
        }
        else if(!un.matches(noWhiteSpace)){
            supplierName1.setError("No Space allowed");
            return false;
        }
        else {
            supplierName1.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String pass = supplierPassword1.getText().toString();
        String passwordVal = "^"+ "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";

        if (pass.isEmpty()) {
            supplierPassword1.setError("Field Cannot be empty");
            return false;
        }
        else if(!pass.matches(passwordVal)){
            supplierPassword1.setError("Please Enter Strong Password");
            return false;
        }
        else {
            supplierPassword1.setError(null);
            return true;
        }
    }

    private boolean validateMobile() {
        String mob = supplierMobile1.getText().toString();
        String mobileNum = "[0-9]{10}";

        if (mob.isEmpty()) {
            supplierMobile1.setError("Field Cannot be empty");
            return false;
        }
        else if(!mob.matches(mobileNum)){
            supplierMobile1.setError("Please Enter valid Mobile Number ");
            return false;
        }
        else {
            supplierMobile1.setError(null);
            return true;
        }
    }

}