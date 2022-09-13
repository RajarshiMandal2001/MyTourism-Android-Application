package com.example.mytourism;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Reviews> mUploads;

    public ListViewAdapter(Context context, List<Reviews> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.review_element, parent, false);  //integretes layout resource with java files ,since it doesnt has its own
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Reviews uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getmCustomerName());
        Log.d("customerN",uploadCurrent.getmCustomerName());
        holder.textViewReview.setText(uploadCurrent.getmReview());
        Log.d("customerR",uploadCurrent.getmReview());
        holder.textViewRating.setText(String.valueOf(uploadCurrent.getmRating()));
        Log.d("customerRa",holder.textViewRating.getText().toString());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewReview;
        public TextView textViewRating;
        public ConstraintLayout constraintLayout;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.customer_name_text_view);
            textViewReview = itemView.findViewById(R.id.customer_review_text_view);
            textViewRating = itemView.findViewById(R.id.customer_rating_text_view);
            constraintLayout = itemView.findViewById(R.id.review_item_constraint_layout);
        }
    }
}

//    private List<Reviews> reviewList;
//    private TextView customerName;
//    public ListViewAdapter(@NonNull Context context, int resource, List<Reviews> reviewsList) {
//        super(context,resource);
//        this.reviewList = reviewsList;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Reviews review = new Reviews();
//        if (convertView == null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_element,parent,false);
//        }
//
//        customerName = convertView.findViewById(R.id.customer_name_text_view);
//        TextView customerReviews= convertView.findViewById(R.id.customer_review_text_view);
//        TextView customerRating = convertView.findViewById(R.id.customer_rating_text_view);
//
////        customerName.setText(reviewList.get(position).getmCustomerName());
////        customerReviews.setText(reviewList.get(position).getmReview());
////        customerRating.setText(reviewList.get(position).getmRating());
//        customerName.setText(review.getmCustomerName());
//
//
//        return convertView;
//    }
//}
