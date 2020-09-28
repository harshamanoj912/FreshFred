package com.teamevox.freshfred.IT19208718;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Application;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

public class DistributedOrdersByRidersAdapter extends FirebaseRecyclerAdapter<OrderListDisplayForRiderModel, DistributedOrdersByRidersAdapter.DistributedOrdersByRiderViewHolder> {

    final String loggedRiderNic;

    public DistributedOrdersByRidersAdapter(@NonNull FirebaseRecyclerOptions<OrderListDisplayForRiderModel> options, String loggedRiderNIC) {
        super(options);
        this.loggedRiderNic = loggedRiderNIC;
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final DistributedOrdersByRiderViewHolder holder, int position, @NonNull final OrderListDisplayForRiderModel model) {

        holder.ordersListFoodName19.setText("Food Name : "+model.getFoodName());
        holder.ordersListAddress19.setText("Address : "+model.getAddress());
        holder.ordersListCustomerName19.setText("Customer Name : "+model.getCustomerName());
        holder.ordersListMobileNumber19.setText("Mobile Number : "+model.getMobileNumber());
        holder.ordersListOrderId19.setText("Order ID : "+model.getOrderId());
        holder.ordersListPrice19.setText("Total Price : "+model.getTotalPrice());

        holder.markThisOrderAsShipped19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteFromCompletedHistory(loggedRiderNic, model.getFoodName(), model.getTotalPrice(), model.getCustomerName(), model.getMobileNumber(), model.getOrderId(), model.getAddress());
                Toast.makeText(view.getContext(), "Successfully mark as shipped", Toast.LENGTH_SHORT).show();

            }
        });



    }


    @NonNull
    @Override
    public DistributedOrdersByRiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_distributed_order_by_rider, parent, false);
        return new DistributedOrdersByRiderViewHolder(view);


    }

    static class DistributedOrdersByRiderViewHolder extends RecyclerView.ViewHolder{

        private TextView ordersListFoodName19, ordersListPrice19, ordersListCustomerName19, ordersListMobileNumber19, ordersListOrderId19, ordersListAddress19;
        ImageView markThisOrderAsShipped19;

        public DistributedOrdersByRiderViewHolder(@NonNull View itemView0) {
            super(itemView0);

            ordersListFoodName19 = itemView0.findViewById(R.id.distributedOrderByRiderFoodName);
            ordersListPrice19 = itemView0.findViewById(R.id.distributedOrderByRiderTotalPrice);
            ordersListCustomerName19 = itemView0.findViewById(R.id.distributedOrderByRiderCustomerName);
            ordersListMobileNumber19 = itemView0.findViewById(R.id.distributedOrderByRiderCustomerMobile);
            ordersListOrderId19 = itemView0.findViewById(R.id.distributedOrderByRiderOrderId);
            ordersListAddress19 = itemView0.findViewById(R.id.distributedOrderByRiderAddress);
            markThisOrderAsShipped19 = itemView0.findViewById(R.id.deleteThisOrderFromHistoryNow);


        }
    }


    public void deleteFromCompletedHistory(String loggedUser, String ordersListFoodName, String ordersListPrice, String ordersListCustomerName, String ordersListMobileNumber, String ordersListOrderId, String ordersListAddress){



        //delete from fresh completedOrdersByRiders
        DatabaseReference deleteFromFreshOrders = FirebaseDatabase.getInstance().getReference("completedOrdersByRiders").child(loggedUser);
        Query deleteQuery = deleteFromFreshOrders.orderByChild("orderId").equalTo(ordersListOrderId);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }









}
