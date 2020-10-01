package com.teamevox.freshfred.IT19208718;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

public class ForgotPassword extends AppCompatActivity {

    EditText resetPasswordNIC, resetPasswordMobile;
    Button riderGoToResetPwdPage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private AwesomeValidation awesomeValidation0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetPasswordNIC = findViewById(R.id.resetPasswordNIC);
        resetPasswordMobile = findViewById(R.id.resetPasswordMobile);
        riderGoToResetPwdPage = findViewById(R.id.GoToResetPwdPage);

        awesomeValidation0 = new AwesomeValidation(ValidationStyle.BASIC);


        awesomeValidation0.addValidation(this, R.id.resetPasswordNIC, "^[0-9]{9}[V]$", R.string.nicError1);
        awesomeValidation0.addValidation(this, R.id.resetPasswordMobile, "^[0-9]{10}$", R.string.mobileError1);

        if (awesomeValidation0.validate()){

            riderGoToResetPwdPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final String resetPasswordNicValue = resetPasswordNIC.getText().toString();
                    final String resetPasswordMobileValue = resetPasswordMobile.getText().toString();


                    final DatabaseReference userIsARider = database.getReference("riders").child(resetPasswordNicValue);
                    final DatabaseReference userIsASupplier = database.getReference("suppliers").child(resetPasswordNicValue);
                    final DatabaseReference userIsAOwner = database.getReference("owners").child(resetPasswordNicValue);
                    final DatabaseReference userIsACustomer = database.getReference("customers").child(resetPasswordNicValue);


                    userIsARider.addValueEventListener(new ValueEventListener() {       //check weather the user is a rider

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                String assignedMobileNumber = snapshot.child("riderMobile").getValue(String.class);
                                String userType = "rider";
                                validateForReset(resetPasswordNicValue, resetPasswordMobileValue, assignedMobileNumber, userType);

                            } else {  //check weather the user is a supplier


                                userIsASupplier.addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (snapshot.exists()) {

                                            String assignedMobileNumber = snapshot.child("supplierMobile").getValue(String.class);
                                            String userType = "supplier";
                                            validateForReset(resetPasswordNicValue, resetPasswordMobileValue, assignedMobileNumber, userType);

                                        } else {      //check weather the user is a owner


                                            userIsAOwner.addValueEventListener(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    if (snapshot.exists()) {

                                                        String assignedMobileNumber = snapshot.child("ownerMobile").getValue(String.class);
                                                        String userType = "owner";
                                                        validateForReset(resetPasswordNicValue, resetPasswordMobileValue, assignedMobileNumber, userType);

                                                    } else {      //check weather the user is a customer


                                                        userIsACustomer.addValueEventListener(new ValueEventListener() {

                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                if (snapshot.exists()) {

                                                                    String assignedMobileNumber = snapshot.child("customerMobile").getValue(String.class);
                                                                    String userType = "customer";
                                                                    validateForReset(resetPasswordNicValue, resetPasswordMobileValue, assignedMobileNumber, userType);

                                                                } else {
                                                                    Toast toast = Toast.makeText(ForgotPassword.this, "There is no existing user", Toast.LENGTH_SHORT);
                                                                    toast.show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            });


    }
    }

    public void validateForReset(String enteredNicByUser, String enteredMobileByUser , String userMobileFromDb, String userType ){

        if(enteredMobileByUser.equals(userMobileFromDb)){

            GlobalClass global= ( (GlobalClass) getApplicationContext() );
            global.setResetPasswordNIC(enteredNicByUser);
            global.setResetPasswordUserType(userType);

            startActivity(new Intent(this, SetNewPassword.class));

        }else{
            Toast toast = Toast.makeText(this, "Entered details are incorrect", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}