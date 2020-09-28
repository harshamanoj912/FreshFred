package com.teamevox.freshfred.IT19207650;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.StringValue;
import com.teamevox.freshfred.R;

import java.util.Random;

public class AddNewFood extends AppCompatActivity {

    EditText editTextFoodName, editTextFoodDes,editTextFoodPrice;
    Button buttonaddFood;
    ImageView foodProfilePicture;
    String url;
    public Uri imgUrlll;
    FirebaseStorage storage;
    StorageReference storageReference67;
    DatabaseReference  databaseFoods ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);

        databaseFoods = FirebaseDatabase.getInstance().getReference("food");
        storage = FirebaseStorage.getInstance();
        storageReference67 = storage.getReference();

        editTextFoodName = (EditText) findViewById(R.id.editTextFoodName);
        editTextFoodDes = (EditText) findViewById(R.id.editTextFoodDes);
        editTextFoodPrice = (EditText) findViewById(R.id.editTextFoodPrice);
        foodProfilePicture = findViewById(R.id.foodProfilePicture);
        buttonaddFood = (Button) findViewById(R.id.buttonaddFood);

        foodProfilePicture = findViewById(R.id.foodProfilePicture);

        buttonaddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
            }
        });

        foodProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
            }
        });





    }
    private void addFood() {

        String foodName = editTextFoodName.getText().toString();
        String foodDes = editTextFoodPrice.getText().toString();
        String foodPrice = editTextFoodPrice.getText().toString();

       Random r = new Random();
       int low = 10000;
       int high = 100000;
       int tempFoodID = r.nextInt(high-low) + low;
       String foodID = String.valueOf(tempFoodID);

       //pass the uploaded image's URI on here,otherwise it will not loads 

        Food food = new Food(foodID,foodName,foodDes,foodPrice, String.valueOf("https://firebasestorage.googleapis.com/v0/b/freshfred-sliit.appspot.com/o/foods%2F42489?alt=media&token=7aff2885-183c-4deb-bf53-92e8f029ec45")) ;
        uploadPicture(foodID);

        databaseFoods.child(foodID).setValue(food);


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
            imgUrlll = data.getData();
            foodProfilePicture.setImageURI(imgUrlll);

        }
    }

    private void uploadPicture(String foodID) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();



        StorageReference foodRef = storageReference67.child("foods/" + foodID);



        foodRef.putFile(imgUrlll)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "food Successfully Added", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload..!", Toast.LENGTH_SHORT).show();
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


    public void setUrl(String url){
        this.url = url;
    }


}
