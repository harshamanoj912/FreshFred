package com.teamevox.freshfred;
//IT19208718
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private LayoutInflater layoutInflater;
    private List<String> data;

    Adapter(Context context, List<String> data){

        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.rescustom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String FOODNAME = data.get(position);
        holder.foodName.setText(FOODNAME);

        String QUANTITY  = data.get(position);
        holder.quantity.setText(QUANTITY);

        String PRICE = data.get(position);
        holder.price.setText(PRICE);

        String MOBILENUMBER = data.get(position);
        holder.mobileNumber.setText(MOBILENUMBER);

        String ADDRESS = data.get(position);
        holder.address.setText(ADDRESS);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView foodName, quantity, price, mobileNumber, address;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);

           foodName = itemView.findViewById(R.id.textView);
           quantity = itemView.findViewById(R.id.textView2);
           price = itemView.findViewById(R.id.textView6);
           mobileNumber = itemView.findViewById(R.id.textView7);
           address = itemView.findViewById(R.id.textView8);

       }
   }
}
