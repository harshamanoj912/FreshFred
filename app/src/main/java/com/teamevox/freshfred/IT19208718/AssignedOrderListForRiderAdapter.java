package com.teamevox.freshfred.IT19208718;

import android.annotation.SuppressLint;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.teamevox.freshfred.R;

public class AssignedOrderListForRiderAdapter extends FirebaseRecyclerAdapter<OrderListDisplayForRiderModel, AssignedOrderListForRiderAdapter.OrdersAssignedForRiderVewHolder> {

    final String loggedRiderNic;

    public AssignedOrderListForRiderAdapter(@NonNull FirebaseRecyclerOptions<OrderListDisplayForRiderModel> options, String loggedRiderNIC) {
        super(options);
        this.loggedRiderNic = loggedRiderNIC;
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final OrdersAssignedForRiderVewHolder holder, int position, @NonNull final OrderListDisplayForRiderModel model) {

        holder.ordersListFoodName9.setText("Food Name : "+model.getFoodName());
        holder.ordersListAddress9.setText("Address : "+model.getAddress());
        holder.ordersListCustomerName9.setText("Customer Name : "+model.getCustomerName());
        holder.ordersListMobileNumber9.setText("Mobile Number : "+model.getMobileNumber());
        holder.ordersListOrderId9.setText("Order ID : "+model.getOrderId());
        holder.ordersListPrice9.setText("Total Price : "+model.getTotalPrice());

        holder.markThisOrderAsShipped9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                markAsShipped(loggedRiderNic, model.getFoodName(), model.getTotalPrice(), model.getCustomerName(), model.getMobileNumber(), model.getOrderId(), model.getAddress());
                Toast.makeText(view.getContext(), "Successfully mark as shipped", Toast.LENGTH_SHORT).show();

            }
        });



    }


    @NonNull
    @Override
    public OrdersAssignedForRiderVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_assigned_for_rider, parent, false);
        return new OrdersAssignedForRiderVewHolder(view);


    }

    static class OrdersAssignedForRiderVewHolder extends RecyclerView.ViewHolder{

        private TextView ordersListFoodName9, ordersListPrice9, ordersListCustomerName9, ordersListMobileNumber9, ordersListOrderId9, ordersListAddress9;
        ImageView markThisOrderAsShipped9;

        public OrdersAssignedForRiderVewHolder(@NonNull View itemView0) {
            super(itemView0);

            ordersListFoodName9 = itemView0.findViewById(R.id.assignedOrderForMeFoodName);
            ordersListPrice9 = itemView0.findViewById(R.id.assignedOrderForMeTotalPrice);
            ordersListCustomerName9 = itemView0.findViewById(R.id.assignedOrderForMeCustomerName);
            ordersListMobileNumber9 = itemView0.findViewById(R.id.assignedOrderForMeCustomerMobile);
            ordersListOrderId9 = itemView0.findViewById(R.id.assignedOrderForMeOrderId);
            ordersListAddress9 = itemView0.findViewById(R.id.assignedOrderForMeAddress);
            markThisOrderAsShipped9 = itemView0.findViewById(R.id.markThisOrderAsShipped);


        }
    }


    public void markAsShipped(final String loggedUser, String ordersListFoodName, String ordersListPrice, String ordersListCustomerName, String ordersListMobileNumber, final String ordersListOrderId, String ordersListAddress){

        TempOrderAssign tempOrder = new TempOrderAssign( ordersListFoodName,  ordersListPrice,  ordersListCustomerName,  ordersListMobileNumber,  ordersListOrderId,  ordersListAddress);

        //add to completedOrdersByRiders table
        Task<Void>  databaseAssignedOrder = FirebaseDatabase.getInstance().getReference("completedOrdersByRiders").child(loggedUser).child(ordersListOrderId).setValue(tempOrder);

        //save order value for calculate the commission
        final double totalPriceForTheOrder = Double.parseDouble(ordersListPrice);

        Task<Void>  calculateCommission = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(loggedUser).child("totalCostOfDeliveredItems").setValue(ServerValue.increment(totalPriceForTheOrder));

        //set Delivered Item Count
        Task<Void>  totalItemDelivered = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(loggedUser).child("totalItemDelivered").setValue(ServerValue.increment(1));



        //get commission
        FirebaseDatabase database99 = FirebaseDatabase.getInstance();
        DatabaseReference getCommissionRateNow = database99.getReference("loggedRiderCommission").child("tempRate");

        getCommissionRateNow.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String tempRate = snapshot.getValue(String.class);

                //calculate the commission
                assert tempRate != null;
                double tmpVal = Double.parseDouble(tempRate);

                Calc calc =  new Calc();

                double finalCommissionValueForThisOrder =  Calc.calculateCommission(tmpVal, totalPriceForTheOrder);

                //calculate price for owner
                double priceForOwner = (totalPriceForTheOrder - finalCommissionValueForThisOrder);

                //insert finalCommissionValueForThisOrder to firebase
                Task<Void>  finalCommissionInsertForDb = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(loggedUser).child("totalCommission").setValue(ServerValue.increment(finalCommissionValueForThisOrder));


                //insert ownerValue to firebase
                Task<Void>  giveThisMoneyForOwner = FirebaseDatabase.getInstance().getReference("commissionForRiders").child(loggedUser).child("forOwner").setValue(ServerValue.increment(priceForOwner));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //delete from fresh assignedOrders
        DatabaseReference deleteFromFreshOrders = FirebaseDatabase.getInstance().getReference("assignedOrders").child(loggedUser);
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