package com.teamevox.freshfred.IT19207650;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.teamevox.freshfred.R;

import java.util.List;

public class FoodList extends ArrayAdapter <Food>{

    private Activity context ;
    private List<Food> foodList;

    public FoodList(Activity context, List<Food> foodList){
        super(context, R.layout.activity_list_of_foods);
        this.context = context ;
        this.foodList = foodList;

    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater Inflater = context.getLayoutInflater();

        View listViewItem = Inflater.inflate(R.layout.activity_list_of_foods,null,true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Food food =  foodList.get(position);

        textViewName.setText(food.getFoodName());
        textViewPrice.setText(food.getFoodPrice());

        return listViewItem ;

    }
}

