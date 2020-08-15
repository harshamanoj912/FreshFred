package com.teamevox.freshfred;
//IT19208718 recycler view
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    LayoutInflater layoutInflater;

    String[] arrayfoodName;
    String[] arrayfoodQty;
    String[] arrayfoodPrice;
    String[] arrayfoodMobile;
    String[] arrayfoodAddress;
    int[] arrayorderedFoodImg;
    Context arraycontext;

    public MyAdapter(Context ct, String[] food_Name, String[] food_Qty, String[] food_Price, String[] food_Mobile, String[] food_Address, int[] ordered_Food_Img){

        this.arraycontext = ct;
        this.arrayfoodName = food_Name;
        this.arrayfoodPrice = food_Price;
        this.arrayfoodMobile = food_Mobile;
        this.arrayfoodAddress  = food_Address;
        this.arrayorderedFoodImg = ordered_Food_Img;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(arraycontext);
        View view =inflater.inflate(R.layout.rescustom, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.rfoodName.setText(arrayfoodName[position]);
        holder.rquantity.setText(arrayfoodQty[position]);
        holder.rprice.setText(arrayfoodPrice[position]);
        holder.rmobileNumber.setText(arrayfoodMobile[position]);
        holder.raddress.setText(arrayfoodAddress[position]);
        holder.rimgView.setImageResource(arrayorderedFoodImg[position]);


    }

    @Override
    public int getItemCount() {

        return arrayfoodName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rfoodName, rquantity, rprice, rmobileNumber, raddress;
        ImageView rimgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rfoodName = itemView.findViewById(R.id.orderedListFoodName);
            rquantity = itemView.findViewById(R.id.orderedListFoodQty);
            rprice = itemView.findViewById(R.id.orderedListFoodPrice);
            rmobileNumber = itemView.findViewById(R.id.orderedListFoodMobile);
            raddress = itemView.findViewById(R.id.orderedListFoodAddress);
            rimgView = itemView.findViewById(R.id.orderedListFoodImage);
        }
    }
}
