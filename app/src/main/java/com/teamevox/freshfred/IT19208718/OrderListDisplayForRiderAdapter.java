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

public class OrderListDisplayForRiderAdapter extends FirebaseRecyclerAdapter<OrderListDisplayForRiderModel, OrderListDisplayForRiderAdapter.OrderListDisplayForRiderVewHolder> {

    final String loggedRiderNic;

    public OrderListDisplayForRiderAdapter(@NonNull FirebaseRecyclerOptions<OrderListDisplayForRiderModel> options, String loggedRiderNIC) {
        super(options);
        this.loggedRiderNic = loggedRiderNIC;
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final OrderListDisplayForRiderVewHolder holder, int position, @NonNull final OrderListDisplayForRiderModel model) {

        holder.ordersListFoodName.setText("Food Name : "+model.getFoodName());
        holder.ordersListAddress.setText("Address : "+model.getAddress());
        holder.ordersListCustomerName.setText("Customer Name : "+model.getCustomerName());
        holder.ordersListMobileNumber.setText("Mobile Number : "+model.getMobileNumber());
        holder.ordersListOrderId.setText("Order ID : "+model.getOrderId());
        holder.ordersListPrice.setText("Total Price : "+model.getTotalPrice());

        holder.assignThisOrderForMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setAsAssignedForMe(loggedRiderNic, model.getFoodName(), model.getTotalPrice(), model.getCustomerName(), model.getMobileNumber(), model.getOrderId(), model.getAddress());
                Toast.makeText(view.getContext(), "Successfully assigned for you", Toast.LENGTH_SHORT).show();

            }
        });



    }


    @NonNull
    @Override
    public OrderListDisplayForRiderVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_show_for_riders, parent, false);
        return new OrderListDisplayForRiderVewHolder(view);


    }

    static class OrderListDisplayForRiderVewHolder extends RecyclerView.ViewHolder{

        private TextView ordersListFoodName, ordersListPrice, ordersListCustomerName, ordersListMobileNumber, ordersListOrderId, ordersListAddress;
        ImageView assignThisOrderForMe;

        public OrderListDisplayForRiderVewHolder(@NonNull View itemView) {
            super(itemView);

            ordersListFoodName = itemView.findViewById(R.id.foodNameTXTOrderQueue);
            ordersListPrice = itemView.findViewById(R.id.foodPriceTXTOrderQueue);
            ordersListCustomerName = itemView.findViewById(R.id.customerNameTXTOrderQueue);
            ordersListMobileNumber = itemView.findViewById(R.id.customerMobileTXTOrderQueue);
            ordersListOrderId = itemView.findViewById(R.id.orderIDTXTOrderQueue);
            ordersListAddress = itemView.findViewById(R.id.addressTXTOrderQueue);
            assignThisOrderForMe = itemView.findViewById(R.id.assignThisOrderForMe);


        }
    }


    public void setAsAssignedForMe(String loggedUser, String ordersListFoodName, String ordersListPrice, String ordersListCustomerName, String ordersListMobileNumber, String ordersListOrderId, String ordersListAddress){

        TempOrderAssign temp = new TempOrderAssign( ordersListFoodName,  ordersListPrice,  ordersListCustomerName,  ordersListMobileNumber,  ordersListOrderId,  ordersListAddress);

        //add to assignedOrders table
        Task<Void>  databaseAssignedOrder = FirebaseDatabase.getInstance().getReference("assignedOrders").child(loggedUser).child(ordersListOrderId).setValue(temp);

        //add to allOrderHistory table
        Task<Void>  databaseAllAssignedOrder = FirebaseDatabase.getInstance().getReference("allOrdersHistory").child(ordersListOrderId).setValue(temp);

        //delete from fresh orders
        DatabaseReference deleteFromFreshOrders = FirebaseDatabase.getInstance().getReference("orders");
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

    //temp model class
    public static class TempOrderAssign{

        String foodName, totalPrice, customerName,mobileNumber, orderId, address;

        public TempOrderAssign(String ordersListFoodName, String ordersListPrice, String ordersListCustomerName, String ordersListMobileNumber, String ordersListOrderId, String ordersListAddress) {
            this.foodName = ordersListFoodName;
            this.totalPrice = ordersListPrice;
            this.customerName = ordersListCustomerName;
            this.mobileNumber = ordersListMobileNumber;
            this.orderId = ordersListOrderId;
            this.address = ordersListAddress;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }







}
