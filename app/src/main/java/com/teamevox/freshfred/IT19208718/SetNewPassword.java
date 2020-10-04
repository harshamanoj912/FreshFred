package com.teamevox.freshfred.IT19208718;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamevox.freshfred.R;

public class SetNewPassword extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText newPassword;
    Button resetNow, backToLogin;
    private AwesomeValidation awesomeValidation11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        newPassword = findViewById(R.id.newPassword);
        resetNow = findViewById(R.id.resetRiderPwdNow);
        backToLogin = findViewById(R.id.backToRiderLogin);

        GlobalClass global = ( (GlobalClass) getApplicationContext() );

        final String resetPasswordNic = global.getResetPasswordNIC();
        final String resetPasswordUserType = global.getResetPasswordUserType();

        awesomeValidation11 = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation11.addValidation(this, R.id.newPassword, "^[0-9A-z]{6}$", R.string.pwdError1);



        resetNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String newPasswordValue = newPassword.getText().toString();

                if (awesomeValidation11.validate()){
                    resetPassword(resetPasswordNic, newPasswordValue, resetPasswordUserType);
                }




            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetNewPassword.this, MainLogin.class));
            }
        });


    }


    public void resetPassword(String userNIC, String newPassword, String uerType){

        final DatabaseReference updatePassword;

        switch (uerType){
            case "rider":   updatePassword = database.getReference("riders").child(userNIC).child("riderPassword");
                            updatePassword.setValue(newPassword);
                            break;

            case "owner":   updatePassword = database.getReference("owners").child(userNIC).child("ownerPassword");
                            updatePassword.setValue(newPassword);
                            break;

            case "supplier":    updatePassword = database.getReference("suppliers").child(userNIC).child("supplierPassword");
                                updatePassword.setValue(newPassword);
                                break;

            case "customer":    updatePassword = database.getReference("customers").child(userNIC).child("customerPassword");
                                updatePassword.setValue(newPassword);
                                break;


        }

        Toast toast = Toast.makeText(getApplicationContext(), "Password changed, please sign in..." , Toast.LENGTH_SHORT);
        toast.show();


    }














}