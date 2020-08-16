package com.teamevox.freshfred;
//IT19208718
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SampleOrdersForDistributing extends AppCompatActivity {

    RecyclerView recyclerView;

    String[] foodName_;
    String[] foodQty_;
    String[] foodPrice_;
    String[] foodMobile_;
    String[] foodAddress_;
    int[] orderedFoodImg_ = {R.drawable.cdburger, R.drawable.lugerburger, R.drawable.chicenrce, R.drawable.gkburger};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_orders_for_distribuation);

        recyclerView = findViewById(R.id.recycler_View);

        foodName_ = getResources().getStringArray(R.array.ordered_food_names);
        foodQty_ = getResources().getStringArray(R.array.ordered_food_qty);
        foodPrice_ = getResources().getStringArray(R.array.ordered_food_price);
        foodMobile_ = getResources().getStringArray(R.array.ordered_food_mobile);
        foodAddress_ = getResources().getStringArray(R.array.ordered_food_address);

        MyAdapter adapter = new MyAdapter(this, foodName_, foodQty_, foodPrice_, foodMobile_, foodAddress_, orderedFoodImg_);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }



}