package com.teamevox.freshfred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamevox.freshfred.ui.home.HomeFragment;

public class RiderPortal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_portal);


        Button btn1, btn2, btn3, btn4, btn5 ;

        btn1 = findViewById(R.id.OrdersForDistributionButton);
        btn2 = findViewById(R.id.DailySalesReportButton);
        btn3 = findViewById(R.id.DistributedOrdersButton);
        btn4 = findViewById(R.id.MyAccountRiderButton);
        btn5 = findViewById(R.id.riderLogOutButton);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), OrdersForDistribution.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), DailySalesReport.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), DistributedOrders.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), UpdateRiderDetails.class));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(RiderPortal.this, "Logging Out", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(getBaseContext(), RiderLogin.class));
            }
        });






    }
}