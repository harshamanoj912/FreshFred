package com.teamevox.freshfred.IT19208718;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.StringValue;
import com.teamevox.freshfred.R;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class RiderListDisplayAdapter extends FirebaseRecyclerAdapter <Rider, RiderListDisplayAdapter.RiderListDisplayViewHolder> {


    public RiderListDisplayAdapter (@NonNull FirebaseRecyclerOptions<Rider> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final RiderListDisplayViewHolder holder, int position, @NonNull final Rider model) {

        holder.riderListRiderName.setText( "Rider Name : " + model.getRiderName());
        holder.riderListRiderNIC.setText("NIC Number : " + model.getRiderNic());
        holder.riderListRiderMobile.setText("Mobile Number : " + model.getRiderMobile());
        holder.riderListRiderCommission.setText("Fixed Commission : " + model.getRiderCommission() + "%");
        holder.riderListRiderBikeNumber.setText("Bike Number: " + model.getRiderBikeNumber());

        final Context context = holder.itemView.getContext();

        final FirebaseStorage theStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = theStorage.getReferenceFromUrl("gs://freshfred-sliit.appspot.com").child("riders").child(model.getRiderNic());


        try {
            final File file = File.createTempFile("img", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.riderListRiderImage.setImageBitmap(bitmap);
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }


        holder.riderRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRiderAccount(model.getRiderNic());
                Toast.makeText(context,"Rider Removed", Toast.LENGTH_SHORT).show();
            }
        });


        holder.updateCommission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewRiderCommissionDialog newRiderCommissionDialog = new NewRiderCommissionDialog();

                newRiderCommissionDialog.show(  ((AppCompatActivity)context).getSupportFragmentManager(), "sample dialog");

                newRiderCommissionDialog.setEditNIC(model.getRiderNic());


            }
        });



    }

    @NonNull
    @Override
    public RiderListDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_rider_show_for_owner, parent, false);
        return new RiderListDisplayViewHolder(view);
    }


    static class RiderListDisplayViewHolder extends RecyclerView.ViewHolder{

        private TextView riderListRiderName, riderListRiderNIC, riderListRiderMobile, riderListRiderCommission, riderListRiderBikeNumber, pwd;
        private ImageView riderListRiderImage;
        private ImageView riderRemove, updateCommission;
        public RiderListDisplayViewHolder(@NonNull View itemView01) {
            super(itemView01);



            riderListRiderName = itemView01.findViewById(R.id.riderListRiderNameTxt);
            riderListRiderNIC = itemView01.findViewById(R.id.riderListRiderNICTxt);
            riderListRiderMobile = itemView01.findViewById(R.id.riderListRiderMobileTxt);
            riderListRiderCommission = itemView01.findViewById(R.id.riderListRiderCommissionTxt);
            riderListRiderBikeNumber = itemView01.findViewById(R.id.riderListRiderBikeNumberTxt);
            riderListRiderImage = itemView01.findViewById(R.id.riderListRiderImage);
            riderRemove = itemView01.findViewById(R.id.riderRemove);
            updateCommission = itemView01.findViewById(R.id.updateCommision);

        }


    }


    public void deleteRiderAccount(String editNIC ){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRider = database.getReference("riders");

        if( !TextUtils.isEmpty(editNIC)  )
        {

            Query deleteQuery = databaseRider.orderByChild("riderNic").equalTo(editNIC);

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


    public static class NewRiderCommissionDialog extends AppCompatDialogFragment {

        private EditText newCommission;

        public String theNewCommission , editNIC;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = requireActivity().getLayoutInflater();


            View view = inflater.inflate(R.layout.layout_rider_commission, null);
            newCommission = view.findViewById(R.id.newRiderCommission);

            builder.setView(view).setTitle("Enter New Commission")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String newCommissionVal = newCommission.getText().toString();

                            setTheNewCommission(newCommissionVal);

                            showToast();

                        }
                    });

            return  builder.create();

        }

        public void setTheNewCommission(String theNewCommission) {
            this.theNewCommission = theNewCommission;
        }

        public String getTheNewCommission() {
            return theNewCommission;
        }

        public String getEditNIC() {
            return editNIC;
        }

        public void setEditNIC(String editNIC) {
            this.editNIC = editNIC;
        }

        public void showToast(){
            Toast.makeText(getContext(), "Commission updated as "+this.theNewCommission + "%", Toast.LENGTH_SHORT).show();
            doUpdate();
        }

        public void doUpdate(){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            Task<Void> databaseRider = database.getReference("riders").child(this.getEditNIC()).child("riderCommission").setValue(this.getTheNewCommission());
        }



    }






}
