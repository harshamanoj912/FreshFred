package com.teamevox.freshfred.ui.rideraccount;
//IT19208718

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teamevox.freshfred.DailySalesReprt;
import com.teamevox.freshfred.DistributedOrders;
import com.teamevox.freshfred.OrdersForDistribution;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.UpdateRiderDetails;
import java.util.zip.Inflater;

public class RiderAccount extends Fragment  {
    Button b1, b2, b3, b4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_rider_interface, container, false);

        b1 = view.findViewById(R.id.riderMyAccount);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        startActivity(new Intent(getActivity(), UpdateRiderDetails.class));
            }
        });


        b2 = view.findViewById(R.id.riderOrdersForDistribution);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrdersForDistribution.class));
            }
        });


        b3 = view.findViewById(R.id.riderDistributedOrders);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DistributedOrders.class));
            }
        });

        b4 = view.findViewById(R.id.riderDailySalesReport);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DailySalesReprt.class));
            }
        });



        return view;


    }


}