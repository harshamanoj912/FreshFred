package com.teamevox.freshfred.IT19216492.RecyclerView2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

import com.teamevox.freshfred.IT19216492.RequestSupplyOrders;

public class RequestSupplyOrderHomeAdapter extends FirebaseRecyclerAdapter<RequestSupplyOrders, RequestSupplyOrderHomeAdapter.SupplierListDisplayViewHolder> {

    final String loggedSupplierNIC;

   RequestSupplyOrderHomeAdapter (@NonNull FirebaseRecyclerOptions<RequestSupplyOrders> options, String loggedSupplierNIC) {
       super(options);
       this.loggedSupplierNIC = loggedSupplierNIC;
   }


    @SuppressLint("SetTextI18n")
   @Override
   protected void onBindViewHolder(@NonNull final SupplierListDisplayViewHolder holder, int position, @NonNull final RequestSupplyOrders model) {

       holder.supplierItemName.setText("Item Name : " + model.getSupplierItemName());
       holder.supplierOrderQuantity.setText("Quantity Number : " + model.getSupplierOrderQuantity());
       holder.supplierOrderMobile.setText("Mobile Number : " + model.getSupplierOrderMobile());
       holder.supplierOrderAddress.setText("Address : " + model.getSupplierOrderAddress());

       final Context context = holder.itemView.getContext();



     holder.supplierRequestRemove.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View view) {

              deleteOrderAccount(loggedSupplierNIC, model.getSupplierItemName(), model.getSupplierOrderQuantity(), model.getSupplierOrderMobile(), model.getSupplierOrderAddress());
               Toast.makeText(context, "Order Removed", Toast.LENGTH_SHORT).show();
   }
       });



   }


    @NonNull
   @Override
   public SupplierListDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_supply_order_home, parent, false);
       return new SupplierListDisplayViewHolder(view);
   }


   static class SupplierListDisplayViewHolder extends RecyclerView.ViewHolder {

       private TextView supplierItemName, supplierOrderQuantity, supplierOrderMobile, supplierOrderAddress;
       private ImageView supplierRequestRemove;

       public SupplierListDisplayViewHolder(@NonNull View itemView001) {
           super(itemView001);


           supplierItemName = itemView001.findViewById(R.id.supplierOrderSupplierItemNameTxt);
           supplierOrderQuantity = itemView001.findViewById(R.id.supplierOrderSupplierQuantityTxt);
           supplierOrderMobile = itemView001.findViewById(R.id.supplierOrderSupplierMobileTxt);
           supplierOrderAddress = itemView001.findViewById(R.id.supplierOrderSupplierAddressTxt);
           supplierRequestRemove = itemView001.findViewById(R.id.supplierRequestRemove);



       }


   }


 public void deleteOrderAccount(String loggedUser, String supplierItemName, String supplierOrderQuantity, String supplierOrderMobile, String supplierOrderAddress) {


     DatabaseReference deleteFromFreshOrders = FirebaseDatabase.getInstance().getReference("supplierOrderRequests").child(loggedUser);
     Query deleteQuery = deleteFromFreshOrders.orderByChild("supplierItemName").equalTo(supplierItemName);


           deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                       appleSnapshot.getRef().removeValue();
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

       }

   }




