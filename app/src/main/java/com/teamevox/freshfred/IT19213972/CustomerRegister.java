package com.teamevox.freshfred.IT19213972;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.IT19208718.MainLogin;
import com.teamevox.freshfred.R;

import java.util.HashMap;

public class CustomerRegister extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputNic, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);


        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputNic = (EditText) findViewById(R.id.register_nic_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();

            }
        });
    }
        private void CreateAccount(){
        String customerName = InputName.getText().toString();
        String customerNic = InputNic.getText().toString();
        String customerMobile = InputPhoneNumber.getText().toString();
        String customerPassword = InputPassword.getText().toString();

            if (TextUtils.isEmpty(customerName)) {

                Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(customerNic)) {
                Toast.makeText(this, "Please write your NIC...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(customerMobile)) {
                Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(customerPassword)) {
                Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
            } else {
                loadingBar.setTitle("Create Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                ValidatecustomerNic(customerName,customerNic,customerMobile,customerPassword);
            }
        }

    private void ValidatecustomerNic(final String customerName, final String customerNic, final String customerMobile, final String customerPassword)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("customers").child(customerNic).exists())){

                    HashMap<String,Object> customerdataMap = new HashMap<>();
                    customerdataMap.put("customerName", customerName);
                    customerdataMap.put("customerNic",customerNic);
                    customerdataMap.put("customerMobile",customerMobile);
                    customerdataMap.put("customerPassword",customerPassword);

                    RootRef.child("customers").child(customerNic).updateChildren(customerdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                   if(task.isSuccessful()){
                                       Toast.makeText(CustomerRegister.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                       loadingBar.dismiss();

                                       Intent intent = new Intent(CustomerRegister.this, MainLogin.class);
                                       startActivity(intent);
                                   }
                                   else {
                                       Toast.makeText(CustomerRegister.this, "Something went wrong..please try again later...", Toast.LENGTH_SHORT).show();
                                       loadingBar.dismiss();
                                   }

                                }
                            });

                }else {
                    Toast.makeText(CustomerRegister.this, "This " + customerNic + " already exists!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(CustomerRegister.this, "Please try again using another NIC.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CustomerRegister.this,MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

