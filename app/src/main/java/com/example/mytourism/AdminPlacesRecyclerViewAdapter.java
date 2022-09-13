package com.example.mytourism;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminPlacesRecyclerViewAdapter extends RecyclerView.Adapter<AdminPlacesRecyclerViewAdapter.ImageViewHolder>{
    private Context mContext;
    private List<places> mUploads;
    private PlaceRecyclerViewClickInterface placeRecyclerViewClickInterface;

    public AdminPlacesRecyclerViewAdapter(Context context, List<places> uploads,PlaceRecyclerViewClickInterface placeRecyclerViewClickInterface) {
        mContext = context;
        mUploads = uploads;
        this.placeRecyclerViewClickInterface = placeRecyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AdminPlacesRecyclerViewAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.admin_view_place_name, parent, false);  //integretes layout resource with java files ,since it doesnt has its own
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPlacesRecyclerViewAdapter.ImageViewHolder holder, int position) {
        places uploadCurrent = mUploads.get(position);
        holder.textViewPlaceName.setText(uploadCurrent.getmPlaceName());
//        Log.d("customerN",uploadCurrent.getmCustomerName());
        holder.textViewStateName.setText(uploadCurrent.getmPlaceStateName());
//        Log.d("customerR",uploadCurrent.getmReview());
        holder.textViewDate.setText(String.valueOf(uploadCurrent.getmDate()));
//        Log.d("customerRa",holder.textViewRating.getText().toString());
        if(uploadCurrent.getIsApproved() == 1){
            holder.mApproval.setText("Approved");
            holder.mApproval.setTextColor(Color.parseColor("#00BFFF"));
        }

        holder.itemView.setOnClickListener(View ->{  //itemClickListner in recyclerview is rediculus,better use listView compromising with performance
            placeRecyclerViewClickInterface.onItemClick(mUploads.get(position),position);
//            if(View.getId() == holder.deletebtn.getId()){
//                Toast.makeText(View.getContext(),"item"+View.getId(),Toast.LENGTH_SHORT).show();
//                Log.d("customerRa","item pressed");
//            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("places").child(uploadCurrent.getmPushKey()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(),"successfully deleted "+uploadCurrent.getmPlaceName(),Toast.LENGTH_SHORT).show();
//                                ((AdminAllPlacesActivity)mContext).finish();  //go out of AllHotels
                                Intent i= new Intent(((AdminAllPlacesActivity)mContext),AdminAllPlacesActivity.class);
                                ((AdminAllPlacesActivity)mContext).finish();
                                ((AdminAllPlacesActivity)mContext).startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.mApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("places").child(uploadCurrent.getmPushKey()).child("isApproved").setValue(1)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent i= new Intent(((AdminAllPlacesActivity)mContext),AdminAllPlacesActivity.class);
                                ((AdminAllPlacesActivity)mContext).finish();
                                ((AdminAllPlacesActivity)mContext).startActivity(i);
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

    public interface PlaceRecyclerViewClickInterface{
        void onItemClick(places place,int position);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPlaceName;
        public TextView textViewStateName;
        public TextView textViewDate;
        public TextView deletebtn;
        public Button mApproval;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlaceName = itemView.findViewById(R.id.admin_place_name);
            textViewStateName = itemView.findViewById(R.id.admin_state_name);
            textViewDate = itemView.findViewById(R.id.admin_date);
            deletebtn = itemView.findViewById(R.id.admin_delete);
            mApproval = itemView.findViewById(R.id.admin_approval);
        }
    }
}
