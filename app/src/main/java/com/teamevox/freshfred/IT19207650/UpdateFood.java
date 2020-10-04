/*package com.teamevox.freshfred.IT19207650;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamevox.freshfred.R;

import java.io.File;
import java.io.IOException;

public class UpdateFood extends AppCompatActivity {


    EditText editTextFoodName11, editTextFoodDes11,editTextFoodPrice11;
    Button updateButton11 , deletefood11;
    ImageView foodProfilePicture11;
    public Uri imgUrl11;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseFoods = database.getReference("food");
    FirebaseStorage storage;
    StorageReference storageReference67;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        editTextFoodName11 = findViewById(R.id.editTextFoodNameupdated);
        editTextFoodDes11 = findViewById(R.id.editTextFoodDesupdated);
        editTextFoodPrice11 = findViewById(R.id.editTextFoodPriceupdated);
        updateButton11 = findViewById(R.id.updateButton);
        deletefood11 = findViewById(R.id.deletefood);
        foodProfilePicture11 = findViewById(R.id.foodProfilePictureupdated);


        storage = FirebaseStorage.getInstance();
        storageReference67 = storage.getReference();


    }


}


*/