package com.example.mytourism;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdminHotelsRecyclerViewAdapter extends RecyclerView.Adapter<AdminHotelsRecyclerViewAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Hotels> mUploads;
    private HotelRecyclerViewClickInterface hotelRecyclerViewClickInterface;

    public AdminHotelsRecyclerViewAdapter(Context context, List<Hotels> uploads,HotelRecyclerViewClickInterface hotelRecyclerViewClickInterface) {
        mContext = context;
        mUploads = uploads;
        this.hotelRecyclerViewClickInterface = hotelRecyclerViewClickInterface;
    }


    @NonNull
    @Override
    public AdminHotelsRecyclerViewAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.admin_view_hotel_name, parent, false);  //integretes layout resource with java files ,since it doesnt has its own
        return new AdminHotelsRecyclerViewAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHotelsRecyclerViewAdapter.ImageViewHolder holder, int position) {
        Hotels uploadCurrent = mUploads.get(position);

        holder.textViewHotelName.setText(uploadCurrent.getmHotelName());
        holder.textViewHotelPlaceName.setText(uploadCurrent.getmHotelPlaceName());
//        Log.d("customerN",uploadCurrent.getmCustomerName());
        holder.textViewHotelPlaceStateName.setText(uploadCurrent.getmHotelPlaceStateName());
//        Log.d("customerR",uploadCurrent.getmReview());
        holder.textViewHotelBHK.setText(uploadCurrent.getmHotelBHK());
        holder.textViewDate.setText(uploadCurrent.getmDate());
//        Log.d("customerRa",holder.textViewRating.getText().toString());
        if(uploadCurrent.getIsApproved() == 1){
            holder.mApproval.setText("Approved");
            holder.mApproval.setTextColor(Color.parseColor("#00BFFF"));
        }

        holder.itemView.setOnClickListener(View ->{  //itemClickListner in recyclerview is rediculus,better use listView compromising with performance
            hotelRecyclerViewClickInterface.onItemClick(mUploads.get(position));
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"item at"+position,Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("hotels").child(uploadCurrent.getmPushKey()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(),"successfully deleted"+uploadCurrent.getmPushKey(),Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(((AdminAllPlacesActivity)mContext),AdminAllHotelsActivity.class);
                                ((AdminAllHotelsActivity)mContext).finish();
                                ((AdminAllHotelsActivity)mContext).startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
//                Toast.makeText(v.getContext(),s.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.mApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("hotels").child(uploadCurrent.getmPushKey()).child("isApproved").setValue(1)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent i= new Intent(((AdminAllHotelsActivity)mContext),AdminAllHotelsActivity.class);
                                ((AdminAllHotelsActivity)mContext).finish();
                                ((AdminAllHotelsActivity)mContext).startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public interface HotelRecyclerViewClickInterface{
        void onItemClick(Hotels hotel);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHotelName;
        public TextView textViewHotelPlaceName;
        public TextView textViewHotelPlaceStateName;
        public TextView textViewHotelBHK;
        public TextView textViewDate;
        public TextView mDelete;
        public TextView mApproval;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHotelName = itemView.findViewById(R.id.admin_hotel_name);
            textViewHotelPlaceName = itemView.findViewById(R.id.admin_hotel_place_name);
            textViewHotelPlaceStateName = itemView.findViewById(R.id.admin_hotel_state_name);
            textViewHotelBHK = itemView.findViewById(R.id.admin_hotel_bhk);
            textViewDate = itemView.findViewById(R.id.admin_hotel_date);
            mDelete = itemView.findViewById(R.id.admin_delete_hotel);
            mApproval = itemView.findViewById(R.id.admin_hotel_status);
        }
    }
}
