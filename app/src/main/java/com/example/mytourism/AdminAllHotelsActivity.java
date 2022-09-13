package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminAllHotelsActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private AdminHotelsRecyclerViewAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Hotels> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_hotels);
        this.setTitle("Hotels added so far");

        mRecyclerView = findViewById(R.id.admin_hotels_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("hotels");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Hotels hotel = postSnapshot.getValue(Hotels.class);
                    mUploads.add(hotel);
                }
                mAdapter = new AdminHotelsRecyclerViewAdapter(AdminAllHotelsActivity.this, mUploads, new AdminHotelsRecyclerViewAdapter.HotelRecyclerViewClickInterface() {
                    @Override
                    public void onItemClick(Hotels hotel) {
                        Toast.makeText(AdminAllHotelsActivity.this,hotel.getmHotelName(),Toast.LENGTH_SHORT).show();
                        String s = hotel.getmHotelName()+",   "+hotel.getmHotelPlaceName()+",   "+hotel.getmHotelPlaceStateName()+",   "+hotel.getmHotelBHK()+" BHK";
                        Intent intent = new Intent(AdminAllHotelsActivity.this,ViewHotelActivity.class);
                        intent.putExtra("HOTEL_PLACE_NAME", s);
                        intent.putExtra("FROM","AdminAllHotelsActivity");
                        startActivity(intent);
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                Toast.makeText(AdminAllHotelsActivity.this,"adapter is set",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminAllHotelsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(AdminAllHotelsActivity.this,"adapter is set after",Toast.LENGTH_SHORT).show();
    }
}