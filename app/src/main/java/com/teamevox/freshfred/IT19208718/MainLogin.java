package com.teamevox.freshfred.IT19208718;


//validate the logging user in four types

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.IT19213972.CustomerRegister;
import com.teamevox.freshfred.IT19213972.FoodHome;
import com.teamevox.freshfred.IT19216492.SupplierPortal;
import com.teamevox.freshfred.R;

public class MainLogin extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;
    TextView forgotPassword;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);


        usernameEditText = findViewById(R.id.loginUsername);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){

                    Toast toast = Toast.makeText(getApplicationContext(), "Username or password can't be empty" , Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    checkUserType(username, password);
                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CustomerRegister.class));
            }
        });


       // forgotPassword
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });








    }


    public void checkUserType(final String username, final String password){

        final DatabaseReference userIsARider = database.getReference("riders").child(username);
        final DatabaseReference userIsASupplier = database.getReference("suppliers").child(username);
        final DatabaseReference userIsAOwner = database.getReference("owners").child(username);
        final DatabaseReference userIsACustomer = database.getReference("customers").child(username);


        userIsARider.addValueEventListener(new ValueEventListener() {       //check weather the user is a rider

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    String correctPassword = snapshot.child("riderPassword").getValue(String.class);
                    String userType = "rider";
                    assert correctPassword != null;
                    validateLogin(correctPassword, password, username, userType);

                }else{  //check weather the user is a supplier


                    userIsASupplier.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()){

                                String correctPassword = snapshot.child("supplierPassword").getValue(String.class);
                                String userType = "supplier";
                                assert correctPassword != null;
                                validateLogin(correctPassword, password, username, userType);

                            }else{      //check weather the user is a owner


                                userIsAOwner.addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (snapshot.exists()){

                                            String correctPassword = snapshot.child("ownerPassword").getValue(String.class);
                                            String userType = "owner";
                                            assert correctPassword != null;
                                            validateLogin(correctPassword, password, username, userType);

                                        }else{      //check weather the user is a customer


                                            userIsACustomer.addValueEventListener(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    if (snapshot.exists()){

                                                        String correctPassword = snapshot.child("customerPassword").getValue(String.class);
                                                        String userType = "customer";
                                                        assert correctPassword != null;
                                                        validateLogin(correctPassword, password, username, userType);

                                                    }else{
                                                        Toast toast = Toast.makeText(MainLogin.this, "There is no existing user", Toast.LENGTH_SHORT);
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


    public void validateLogin(String correctPassword, String enteredPassword , String enteredUsername, String userType){

        if(correctPassword.equals(enteredPassword)){

            Toast toast = Toast.makeText(MainLogin.this, "Logged as " + userType, Toast.LENGTH_SHORT);
            toast.show();

            loadPortal(userType, enteredUsername);


        }else {
            Toast toast = Toast.makeText(MainLogin.this, "Please check again", Toast.LENGTH_SHORT);
            toast.show();

        }

    }


    public void loadPortal(String type, String enteredUsername){

        GlobalClass global= ( (GlobalClass) getApplicationContext() );


        switch (type){
            case "rider":   startActivity(new Intent(getApplicationContext(), RiderPortal.class));
                            global.setLoggedRiderUsername(enteredUsername);
                            break;

            case "owner":   startActivity(new Intent(getApplicationContext(), OwnerPortal.class));
                            global.setGetLoggedOwnerUsername(enteredUsername);
                            break;

            case "supplier":    startActivity(new Intent(getApplicationContext(), SupplierPortal.class));
                                global.setGetLoggedSupplierUsername(enteredUsername);
                                break;

            case "customer":    startActivity(new Intent(getApplicationContext(), FoodHome.class));
                                global.setGetLoggedCustomerUsername(enteredUsername);
                                break;


    }



    }


}