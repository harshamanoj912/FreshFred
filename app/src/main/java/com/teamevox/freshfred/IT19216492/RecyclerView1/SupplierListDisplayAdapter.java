package com.teamevox.freshfred.IT19216492.RecyclerView1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamevox.freshfred.R;
import com.teamevox.freshfred.IT19216492.RequestSupply;
import com.teamevox.freshfred.IT19216492.Supplier;

import java.io.File;
import java.io.IOException;

public class SupplierListDisplayAdapter extends FirebaseRecyclerAdapter<Supplier, SupplierListDisplayAdapter.SupplierListDisplayViewHolder> {

    public SupplierListDisplayAdapter (@NonNull FirebaseRecyclerOptions<Supplier> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final SupplierListDisplayViewHolder holder, int position, @NonNull final Supplier model) {

        holder.supplierListSupplierName.setText( "Supplier Name : " + model.getSupplierName());
        holder.supplierListSupplierNIC.setText("NIC Number : " + model.getSupplierNic());
        holder.supplierListSupplierMobile.setText("Mobile Number : " + model.getSupplierMobile());

        final Context context = holder.itemView.getContext();

        final FirebaseStorage theStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = theStorage.getReferenceFromUrl("gs://freshfred-sliit.appspot.com").child("suppliers").child(model.getSupplierNic());


        try {
            final File file = File.createTempFile("img", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.supplierListSupplierImage.setImageBitmap(bitmap);
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }


        holder.supplierRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteSupplierAccount(model.getSupplierNic());
                Toast.makeText(context,"Supplier Removed", Toast.LENGTH_SHORT).show();
            }
        });


        holder.sendSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RequestSupply.class);
                intent.putExtra("nic001", model.getSupplierNic());
                v.getContext().startActivity(intent);
            }
        });



    }

    @NonNull
    @Override
    public SupplierListDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_home, parent, false);
        return new SupplierListDisplayViewHolder(view);
    }


    static class SupplierListDisplayViewHolder extends RecyclerView.ViewHolder{

        private TextView supplierListSupplierName, supplierListSupplierNIC, supplierListSupplierMobile;
        private ImageView supplierListSupplierImage;
        private ImageView supplierRemove, sendSupplier;
        public SupplierListDisplayViewHolder(@NonNull View itemView01) {
            super(itemView01);



            supplierListSupplierName = itemView01.findViewById(R.id.supplierListSupplierNameTxt);
            supplierListSupplierNIC = itemView01.findViewById(R.id.supplierListSupplierNICTxt);
            supplierListSupplierMobile = itemView01.findViewById(R.id.supplierListSupplierMobileTxt);
            supplierListSupplierImage = itemView01.findViewById(R.id.supplierListSupplierImage);
            supplierRemove = itemView01.findViewById(R.id.supplierRemove);
            sendSupplier = itemView01.findViewById(R.id.sendSupplier);

        }


    }


    public void deleteSupplierAccount(String editNIC ){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseSuppliers = database.getReference("suppliers");

        if( !TextUtils.isEmpty(editNIC)  )
        {

            Query deleteQuery = databaseSuppliers.orderByChild("supplierNic").equalTo(editNIC);

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



}
