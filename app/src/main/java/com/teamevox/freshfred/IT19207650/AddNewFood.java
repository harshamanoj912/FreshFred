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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamevox.freshfred.R;

import java.util.Objects;
import java.util.Random;


public class AddNewFood extends AppCompatActivity {

    EditText editTextFoodName, editTextFoodDes,editTextFoodPrice;
    Button buttonaddFood;
    ImageView foodProfilePicture;
    String url ;
    String image_link;
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
        String foodDes = editTextFoodDes.getText().toString();
        String foodPrice = editTextFoodPrice.getText().toString();

        Random r = new Random();
        int low = 10000;
        int high = 100000;
        int tempFoodID = r.nextInt(high-low) + low;
        String foodID = String.valueOf(tempFoodID);

        //pass the uploaded image's URI on here,otherwise it will not loads

        uploadPicture(foodID);
        Food food = new Food(foodID,foodName,foodDes,foodPrice, "dh" ) ;


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

    private void uploadPicture(final String foodID) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();



        final StorageReference foodRef = storageReference67.child("foods/" + foodID);

        final UploadTask uploadTask = foodRef.putFile(imgUrlll);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "food Successfully Added", Snackbar.LENGTH_LONG).show();

                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if(!task.isSuccessful()){
                                    throw Objects.requireNonNull(task.getException());

                                }

                                image_link = foodRef.getDownloadUrl().toString();
                                return foodRef.getDownloadUrl();


                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){

                                    image_link = Objects.requireNonNull(task.getResult()).toString();

                                    databaseFoods.child(foodID).child("foodImage").setValue(image_link);
                                }

                            }
                        });
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


}
