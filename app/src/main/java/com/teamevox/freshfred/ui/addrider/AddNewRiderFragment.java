package com.teamevox.freshfred.ui.addrider;
//IT19208718

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamevox.freshfred.R;

public class AddNewRiderFragment extends Fragment {

    EditText riderName1, riderMobile1, riderBikeNumber1, riderCommission1, riderPassword1, riderNic1;
    Button addNewRider1;


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
       // uploadPhoto1 = view.findViewById(R.id.uploadPhoto);

        databaseRider = FirebaseDatabase.getInstance().getReference("riders");



        addNewRider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addRider();
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
            //String id = databaseRider.push().getKey();
            Rider rider = new Rider (theRiderName, theRiderMobile, theRiderBikeNumber, theRiderCommission, theRiderPassword,theRiderNic);
            databaseRider.child(theRiderNic).setValue(rider);
            Toast toast = Toast.makeText(getContext(), "Added New Rider", Toast.LENGTH_SHORT);
            toast.show();
        }else {

            Toast toast = Toast.makeText(getContext(), "Please fill all the fields..!", Toast.LENGTH_SHORT);
            toast.show();
        }



    }



}
