package com.teamevox.freshfred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RiderLogin extends AppCompatActivity {


    EditText loginNic, loginPwd;
    Button loginBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_login);

        loginNic =  findViewById(R.id.loginNic);
        loginPwd =  findViewById(R.id.loginPwd);
        loginBtn = findViewById(R.id.loginBTN);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getRelevantPassword();
            }
        });


    }


    public void getRelevantPassword(){


        final String enteredNic = loginNic.getText().toString();
        final String enteredPwd = loginPwd.getText().toString();

        DatabaseReference databaseRider = database.getReference("riders").child(enteredNic);

        if(TextUtils.isEmpty(enteredNic)|| TextUtils.isEmpty(enteredPwd) ){
            fillAll();
        }else {

            databaseRider.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String password = snapshot.child("riderPassword").getValue(String.class);

                    assert password != null;
                    validateLogin(password, enteredPwd, enteredNic);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void validateLogin(String relaPwd, String entPwd , String nic){



        if(relaPwd.equals(entPwd)){

            Toast toast = Toast.makeText(this, "Logged in as " + nic, Toast.LENGTH_SHORT);
            toast.show();

            startActivity(new Intent(getActivity(), RiderPortal.class));

            GlobalClass global= ( (GlobalClass) getApplicationContext() );
            global.setLoggedRiderNIC(nic);



        }else {
            Toast toast = Toast.makeText(this, "Please check again", Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    private RiderLogin getActivity() {
        return  this;
    }

    public void fillAll(){
        Toast toast = Toast.makeText(this, "NIC and password should be filled", Toast.LENGTH_SHORT);
        toast.show();
    }
}