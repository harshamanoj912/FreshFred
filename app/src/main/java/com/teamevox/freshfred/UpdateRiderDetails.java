package com.teamevox.freshfred;

//IT19208718
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.ui.addrider.Rider;
import com.teamevox.freshfred.ui.rideraccount.RiderAccount;

public class UpdateRiderDetails extends AppCompatActivity {


    EditText riderName11, riderMobile11, riderBikeNumber11, riderCommission11, riderPassword11, riderNic11;
    Button updateButton11, deleteRiderAccount1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRider = database.getReference("riders");


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

        getValues();


        updateButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateRiderDetails();
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

        if( !TextUtils.isEmpty(riderNicForDelete)  ) //
        {

            Query deleteQuery = databaseRider.orderByChild("riderNic").equalTo(riderNicForDelete);

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


    public void updateRiderDetails(){

        String theRiderName = riderName11.getText().toString();
        String theRiderBikeNumber =  riderBikeNumber11.getText().toString();
        String theRiderCommission = riderCommission11.getText().toString();
        String theRiderPassword = riderPassword11.getText().toString();
        String theRiderMobile = riderMobile11.getText().toString();
        String theRiderNic = riderNic11.getText().toString();


        if(!TextUtils.isEmpty(theRiderBikeNumber) || !TextUtils.isEmpty(theRiderNic) ||!TextUtils.isEmpty(theRiderMobile) || !TextUtils.isEmpty(theRiderName) || !TextUtils.isEmpty(theRiderCommission) || !TextUtils.isEmpty(theRiderPassword) ) //
        {
            //String id = databaseRider.push().getKey();
            Rider rider = new Rider (theRiderName, theRiderMobile, theRiderBikeNumber, theRiderCommission, theRiderPassword, theRiderNic);
            databaseRider.child(theRiderNic).setValue(rider);
            Toast toast = Toast.makeText(this, "Updating", Toast.LENGTH_SHORT);
            toast.show();
        }else {

            Toast toast = Toast.makeText(this, "Please fill all the fields..!", Toast.LENGTH_SHORT);
            toast.show();
        }


    }


    public void getValues() {


        databaseRider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot ) {

                for(DataSnapshot riderSnapshot : snapshot.getChildren()){



                    String name = riderSnapshot.child("riderName").getValue().toString();
                    String mobile = riderSnapshot.child("riderMobile").getValue().toString();
                    String bikeNumber = riderSnapshot.child("riderBikeNumber").getValue().toString();
                    String commission = riderSnapshot.child("riderCommission").getValue().toString();
                    String password = riderSnapshot.child("riderPassword").getValue().toString();
                    String nic = riderSnapshot.child("riderNic").getValue().toString();

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