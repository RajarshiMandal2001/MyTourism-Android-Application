package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewReviewsActivity extends AppCompatActivity {

//    ListView listView;
//
//    FirebaseDatabase database;
//    DatabaseReference ref;
//
//    List<Reviews> reviewsArrayList;
//
//    Reviews review;

    private RecyclerView mRecyclerView;
    private ListViewAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Reviews> mUploads;
    String placeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        this.setTitle("Traveller's reviews");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeValue = extras.getString("HOTEL_PLACE");
        }

        mRecyclerView = findViewById(R.id.added_reviews_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("reviews");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int empty=0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Reviews review = postSnapshot.getValue(Reviews.class);
                    if(review.getmPlaceName().equals(placeValue)){
                        mUploads.add(review);
                        empty = 1;
                    }
                }
                if (empty == 0){
                    Intent intent = new Intent(ViewReviewsActivity.this,NoValueActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    mAdapter = new ListViewAdapter(ViewReviewsActivity.this, mUploads);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewReviewsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(ViewReviewsActivity.this,"adapter is set after",Toast.LENGTH_SHORT).show();


//        listView =findViewById(R.id.added_reviews_list_view);
//
//        reviewsArrayList = new ArrayList<>();
//
//        database = FirebaseDatabase.getInstance();
//        ref = database.getReference("reviews");
//
//        review = new Reviews();
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d:snapshot.getChildren()){
//                    review = d.getValue(Reviews.class);
//                    Toast.makeText(ViewReviewsActivity.this,review.getmReview(),Toast.LENGTH_SHORT).show();
//                    reviewsArrayList.add(review);
//                    Log.d("list",reviewsArrayList.toString());
//                }
//                ListViewAdapter listAdapter = new ListViewAdapter(ViewReviewsActivity.this, R.layout.review_element,reviewsArrayList);
//
//                listView.setAdapter(listAdapter);
//                Toast.makeText(ViewReviewsActivity.this,"adapter set",Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ViewReviewsActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
//if(place.getmPlaceStateName().equals(state_name)){
//        placeList.add(place.getmPlaceName()+","+place.getmPlaceStateName());  //see only added place names and on click on them see all info in ViewPlaceActivity
//        // .add() sets the text of the widget with text given as its arguments whose id is given in adapter here R.id.placeName
//        }