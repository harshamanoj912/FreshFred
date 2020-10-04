package com.teamevox.freshfred.IT19208718;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Range;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamevox.freshfred.R;

public class AddNewRider extends AppCompatActivity {

    EditText riderName1, riderMobile1, riderBikeNumber1, riderCommission1, riderPassword1, riderNic1;
    Button addNewRider1;
    ImageView riderProfilePicture;
    private AwesomeValidation awesomeValidation;

    public Uri imgUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseRider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_rider);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        riderName1= findViewById(R.id.riderName);
        riderMobile1 = findViewById(R.id.riderMobile);
        riderBikeNumber1= findViewById(R.id.riderBikeNumber);
        riderCommission1 = findViewById(R.id.riderCommission);
        riderPassword1 = findViewById(R.id.riderPassword);
        addNewRider1 = findViewById(R.id.addNewRider);
        riderNic1 = findViewById(R.id.riderNic);
        riderProfilePicture = findViewById(R.id.riderProfilePicture);

        databaseRider = FirebaseDatabase.getInstance().getReference("riders");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        awesomeValidation.addValidation(this, R.id.riderName, "^[A-Za-z]{3,}$", R.string.nameError1);
        awesomeValidation.addValidation(this, R.id.riderNic, "^[0-9]{9}[V]$", R.string.nicError1);
        awesomeValidation.addValidation(this, R.id.riderMobile, "^[0-9]{10}$", R.string.mobileError1);
        awesomeValidation.addValidation(this, R.id.riderBikeNumber, "^[A-Z]{2,3}[-][0-9]{4}$", R.string.bikeNumbError1);
        awesomeValidation.addValidation(this, R.id.riderCommission, "^[0-9][0-9]$", R.string.commError1);
        awesomeValidation.addValidation(this, R.id.riderPassword, "^[0-9A-z]{6}$", R.string.pwdError1);



        addNewRider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()){
                    addRider();
                }

            }
        });

        riderProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
            }
        });


    }

    private void addRider(){

        String theRiderName = riderName1.getText().toString();
        String theRiderBikeNumber =  riderBikeNumber1.getText().toString();
        String theRiderCommission = riderCommission1.getText().toString();
        String theRiderPassword = riderPassword1.getText().toString();
        String theRiderMobile = riderMobile1.getText().toString();
        String theRiderNic = riderNic1.getText().toString();


            Rider rider = new Rider (theRiderName, theRiderMobile, theRiderBikeNumber, theRiderCommission, theRiderPassword,theRiderNic);
            databaseRider.child(theRiderNic).setValue(rider);
            uploadPicture();
            Toast toast = Toast.makeText(this, "Adding New Rider", Toast.LENGTH_SHORT);
            toast.show();


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
            riderProfilePicture.setImageURI(imgUrl);

        }
    }

    private void uploadPicture() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();
        String theRiderNic = riderNic1.getText().toString();




        StorageReference riversRef = storageReference.child("riders/" + theRiderNic);

        riversRef.putFile(imgUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Rider Successfully Added", Snackbar.LENGTH_LONG).show();
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





}