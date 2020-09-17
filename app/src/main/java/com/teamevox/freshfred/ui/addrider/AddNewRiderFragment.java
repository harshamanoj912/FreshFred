package com.teamevox.freshfred.ui.addrider;
//IT19208718 | Sraweera SMHM

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.io.File;
import java.util.UUID;

public class AddNewRiderFragment extends Fragment {

    EditText riderName1, riderMobile1, riderBikeNumber1, riderCommission1, riderPassword1, riderNic1;
    Button addNewRider1;
    ImageView riderProfilePicture;

    public Uri imgUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseRider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_add_new_rider, container, false);

        riderName1= view.findViewById(R.id.riderName);
        riderMobile1 = view.findViewById(R.id.riderMobile);
        riderBikeNumber1= view.findViewById(R.id.riderBikeNumber);
        riderCommission1 = view.findViewById(R.id.riderCommission);
        riderPassword1 = view.findViewById(R.id.riderPassword);
        addNewRider1 = view.findViewById(R.id.addNewRider);
        riderNic1 = view.findViewById(R.id.riderNic);
        riderProfilePicture = view.findViewById(R.id.riderProfilePicture);

        databaseRider = FirebaseDatabase.getInstance().getReference("riders");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        addNewRider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addRider();
            }
        });

        riderProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
            }
        });

        return view;
    }



    private void addRider(){

        String theRiderName = riderName1.getText().toString();
        String theRiderBikeNumber =  riderBikeNumber1.getText().toString();
        String theRiderCommission = riderCommission1.getText().toString();
        String theRiderPassword = riderPassword1.getText().toString();
        String theRiderMobile = riderMobile1.getText().toString();
        String theRiderNic = riderNic1.getText().toString();




        if(!TextUtils.isEmpty(theRiderBikeNumber) ||!TextUtils.isEmpty(theRiderMobile) || !TextUtils.isEmpty(theRiderName) || !TextUtils.isEmpty(theRiderCommission) || !TextUtils.isEmpty(theRiderPassword) ) //
        {

            Rider rider = new Rider (theRiderName, theRiderMobile, theRiderBikeNumber, theRiderCommission, theRiderPassword,theRiderNic);
            databaseRider.child(theRiderNic).setValue(rider);
            uploadPicture();
            Toast toast = Toast.makeText(getContext(), "Adding New Rider", Toast.LENGTH_SHORT);
            toast.show();

        }else {

            Toast toast = Toast.makeText(getContext(), "Please fill all the fields..!", Toast.LENGTH_SHORT);
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
            riderProfilePicture.setImageURI(imgUrl);
            //
        }
    }

    private void uploadPicture() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();
        String theRiderNic = riderNic1.getText().toString();


        //rename the image with rider name
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("riders/" + theRiderNic);

        riversRef.putFile(imgUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Rider Successfully Added", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Failed to upload..!", Toast.LENGTH_SHORT).show();
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
