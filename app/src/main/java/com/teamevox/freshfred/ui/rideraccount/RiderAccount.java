package com.teamevox.freshfred.ui.rideraccount;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teamevox.freshfred.R;
import com.teamevox.freshfred.UpdateRiderDetails;

import java.util.zip.Inflater;

public class RiderAccount extends Fragment  {
    Button b1;

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

        return view;


    }


}