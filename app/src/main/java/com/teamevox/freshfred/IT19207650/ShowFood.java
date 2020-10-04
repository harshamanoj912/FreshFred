package com.teamevox.freshfred.IT19207650;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

import java.util.ArrayList;
import java.util.List;

public class ShowFood extends AppCompatActivity {

    ListView listViewItem;
    DatabaseReference databaseFoods ;
    List<Food> foodList;

   // foodList = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();

        databaseFoods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                foodList.clear();

                for( DataSnapshot foodSnapshot : dataSnapshot.getChildren()){

                    Food food = foodSnapshot.getValue(Food.class) ;

                    foodList.add(food);
                }

                FoodList adapter = new FoodList(ShowFood.this, foodList);
                listViewItem.setAdapter(adapter);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food);

        listViewItem = (ListView) findViewById(R.id.listViewItem) ;




    }
}