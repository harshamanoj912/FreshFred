package com.teamevox.freshfred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RiderForgotPassword extends AppCompatActivity {

    EditText resetPasswordNIC, resetPasswordMobile;
    Button riderGoToResetPwdPage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_password_reset);

        resetPasswordNIC = findViewById(R.id.resetPasswordNIC);
        resetPasswordMobile = findViewById(R.id.resetPasswordMobile);
        riderGoToResetPwdPage = findViewById(R.id.riderGoToResetPwdPage);

        GlobalClass global= ( (GlobalClass) getApplicationContext() );

       // DatabaseReference databaseRider = database.getReference("riders").child(resetPasswordNIC.getText().toString());



      //  global.setResetPasswordMobile(resetPasswordMobile.getText().toString());
      //  global.setResetPasswordNIC(resetPasswordNIC.getText().toString());


        riderGoToResetPwdPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SetNewRiderPassword.class));
            }
        });




    }
}