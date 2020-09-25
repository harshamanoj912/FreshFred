package com.teamevox.freshfred.IT19208718;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.teamevox.freshfred.R;

public class OrderListDisplayForRiderAdapter extends FirebaseRecyclerAdapter<OrderListDisplayForRiderModel, OrderListDisplayForRiderAdapter.OrderListDisplayForRiderVewHolder> {


    public OrderListDisplayForRiderAdapter(@NonNull FirebaseRecyclerOptions<OrderListDisplayForRiderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderListDisplayForRiderVewHolder holder, int position, @NonNull OrderListDisplayForRiderModel model) {

        holder.ordersListFoodName.setText(model.getFoodName());
        holder.ordersListAddress.setText(model.getAddress());
        holder.ordersListCustomerName.setText(model.getCustomerName());
        holder.ordersListMobileNumber.setText(model.getMobileNumber());
        holder.ordersListOrderId.setText(model.getOrderId());
        holder.ordersListPrice.setText(model.getTotalPrice());

    }

    @NonNull
    @Override
    public OrderListDisplayForRiderVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_show_for_riders, parent, false);
        return new OrderListDisplayForRiderVewHolder(view);
    }

    static class OrderListDisplayForRiderVewHolder extends RecyclerView.ViewHolder{

        private TextView ordersListFoodName, ordersListPrice, ordersListCustomerName, ordersListMobileNumber, ordersListOrderId, ordersListAddress;

        public OrderListDisplayForRiderVewHolder(@NonNull View itemView) {
            super(itemView);



            ordersListFoodName = itemView.findViewById(R.id.ordersListFoodName);
            ordersListPrice = itemView.findViewById(R.id.ordersListPrice);
            ordersListCustomerName = itemView.findViewById(R.id.ordersListCustomerName);
            ordersListMobileNumber = itemView.findViewById(R.id.ordersListMobileNumber);
            ordersListOrderId = itemView.findViewById(R.id.ordersListOrderId);
            ordersListAddress = itemView.findViewById(R.id.ordersListAddress);

        }
    }
}
