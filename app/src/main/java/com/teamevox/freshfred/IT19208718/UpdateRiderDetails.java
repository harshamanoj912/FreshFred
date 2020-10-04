package com.teamevox.freshfred.IT19208718;

//IT19208718
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.teamevox.freshfred.R;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UpdateRiderDetails extends AppCompatActivity {


    EditText riderName11, riderMobile11, riderBikeNumber11, riderCommission11, riderPassword11, riderNic11;
    Button updateButton11, deleteRiderAccount1;
    ImageView riderProfilePictureUpdated1;
    public Uri imgUrl1;
    private AwesomeValidation awesomeValidation5;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRider = database.getReference("riders");
    FirebaseStorage storage1;
    StorageReference storageReference1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rider_details);

        riderName11 =  findViewById(R.id.riderNameUP);
        riderMobile11 =  findViewById(R.id.riderMobileUP);
        riderBikeNumber11 =  findViewById(R.id.riderBikeNumberUP);
        riderCommission11 =  findViewById(R.id.riderCommissionUP);
        riderPassword11 =  findViewById(R.id.riderPasswordUP);
        updateButton11 = findViewById(R.id.updateButton);
        riderNic11 = findViewById(R.id.riderNicUP);
        deleteRiderAccount1 = findViewById(R.id.deleteRiderAccount);
        riderProfilePictureUpdated1 = findViewById(R.id.riderProfilePictureUpdated);


        storage1 = FirebaseStorage.getInstance();
        storageReference1 = storage1.getReference();

        awesomeValidation5 = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation5.addValidation(this, R.id.riderNameUP, "^[A-Za-z]{3,}$", R.string.nameError1);
        awesomeValidation5.addValidation(this, R.id.riderMobileUP, "^[0-9]{10}$", R.string.mobileError1);
        awesomeValidation5.addValidation(this, R.id.riderBikeNumberUP, "^[A-Z]{2,3}[-][0-9]{4}$", R.string.bikeNumbError1);

        GlobalClass global= ( (GlobalClass) getApplicationContext() );
        StorageReference storageReference = storage1.getReferenceFromUrl("gs://freshfred-sliit.appspot.com").child("riders").child(global.getLoggedRiderUsername());



        try {
            final File file = File.createTempFile("img", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    riderProfilePictureUpdated1.setImageBitmap(bitmap);
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }

        //load logged rider details
        getValues();



        updateButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation5.validate()){
                    updateRiderDetails();
                }
            }
        });





        deleteRiderAccount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRiderAccount();
            }
        });



    }


    public void deleteRiderAccount(){

        String riderNicForDelete = riderNic11.getText().toString();

        if( !TextUtils.isEmpty(riderNicForDelete)  )
        {

            Query deleteQuery = databaseRider.orderByChild("riderNic").equalTo(riderNicForDelete);

            deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                        Toast toast = Toast.makeText(getApplicationContext(), "Account is deleted", Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(new Intent(getApplicationContext(), MainLogin.class));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }else {

            Toast toast = Toast.makeText(this, "Please fill all the fields..!", Toast.LENGTH_SHORT);
            toast.show();
        }
        
        

    }


    public void updateRiderDetails(){

        String theRiderName = riderName11.getText().toString();
        String theRiderBikeNumber =  riderBikeNumber11.getText().toString();
        String theRiderCommission = riderCommission11.getText().toString();
        String theRiderPassword = riderPassword11.getText().toString();
        String theRiderMobile = riderMobile11.getText().toString();
        String theRiderNic = riderNic11.getText().toString();




            Rider rider = new Rider (theRiderName, theRiderMobile, theRiderBikeNumber, theRiderCommission, theRiderPassword, theRiderNic);
            databaseRider.child(theRiderNic).setValue(rider);

            Toast toast = Toast.makeText(this, "Updating", Toast.LENGTH_SHORT);
            toast.show();






    }


    public void getValues() {

        GlobalClass global= ( (GlobalClass) getApplicationContext() );

        Query deleteQuery = databaseRider.orderByChild("riderNic").equalTo(global.getLoggedRiderUsername());

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot riderSnapshot: dataSnapshot.getChildren()) {

                    String name = Objects.requireNonNull(riderSnapshot.child("riderName").getValue()).toString();
                    String mobile = Objects.requireNonNull(riderSnapshot.child("riderMobile").getValue()).toString();
                    String bikeNumber = Objects.requireNonNull(riderSnapshot.child("riderBikeNumber").getValue()).toString();
                    String commission = Objects.requireNonNull(riderSnapshot.child("riderCommission").getValue()).toString();
                    String password = Objects.requireNonNull(riderSnapshot.child("riderPassword").getValue()).toString();
                    String nic = Objects.requireNonNull(riderSnapshot.child("riderNic").getValue()).toString();

                    riderName11.setText(name);
                    riderMobile11.setText(mobile);
                    riderBikeNumber11.setText(bikeNumber);
                    riderCommission11.setText(commission);
                    riderPassword11.setText(password);
                    riderNic11.setText(nic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }






}