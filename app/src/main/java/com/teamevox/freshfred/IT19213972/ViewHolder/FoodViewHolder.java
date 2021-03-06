//IT19213972-----------I.K.S.S.Nawarathne
package com.teamevox.freshfred.IT19213972.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19213972.Interface.ItemClickListner;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName, txtFoodDescription, txtFoodPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public FoodViewHolder(View itemView) {
        super(itemView);

        imageView =(ImageView) itemView.findViewById(R.id.food_image);
        txtFoodName =(TextView) itemView.findViewById(R.id.food_name);
        txtFoodDescription =(TextView) itemView.findViewById(R.id.food_description);
        txtFoodPrice =(TextView) itemView.findViewById(R.id.food_price);

    }

    public void  setItemClickListner(ItemClickListner listner){

        this.listner =listner;
    }

    @Override
    public void onClick(View view)
    {

        listner.onClick(view,getAdapterPosition(),false);

    }
}

