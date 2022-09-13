package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class AdminAllPlacesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdminPlacesRecyclerViewAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<places> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_places);
        this.setTitle("Places added so far");

        mRecyclerView = findViewById(R.id.admin_places_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("places");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    places place = postSnapshot.getValue(places.class);
                    mUploads.add(place);
                }
                mAdapter = new AdminPlacesRecyclerViewAdapter(AdminAllPlacesActivity.this, mUploads, new AdminPlacesRecyclerViewAdapter.PlaceRecyclerViewClickInterface() {
                    @Override
                    public void onItemClick(places place, int position) {
//                        Toast.makeText(AdminAllPlacesActivity.this,"position"+position,Toast.LENGTH_SHORT).show();
                        //.................................method 1 to get widget in a recycler view row, method 2(easy one) is implemented in Hotel......................
                        RecyclerView.ViewHolder id = mRecyclerView.findViewHolderForAdapterPosition(position);  //below 3 lines helps to access any widget inside that row in recycler view
                        View view = id.itemView;
                        TextView del = (TextView) view.findViewById(R.id.admin_delete);
                        TextView stat = (TextView) view.findViewById(R.id.admin_approval);
//                        del.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(AdminAllPlacesActivity.this,"pressed",Toast.LENGTH_SHORT).show();
//                                stat.setText("Deleted");
//                            }
//                        });
                        String s = place.getmPlaceName()+",    "+place.getmPlaceStateName();
                        Intent intent = new Intent(AdminAllPlacesActivity.this,ViewPlaceActivity.class);
                        intent.putExtra("PLACE_NAME", s);
                        startActivity(intent);
                    }

//                    @Override
//                    public void onItemClick(places place) {
//                        Toast.makeText(AdminAllPlacesActivity.this,place.getmPlaceName(),Toast.LENGTH_SHORT).show();
//                        String s = place.getmPlaceName()+","+place.getmPlaceStateName();
//                        Intent intent = new Intent(AdminAllPlacesActivity.this,ViewPlaceActivity.class);
//                        intent.putExtra("PLACE_NAME", s);
//                        startActivity(intent);
//                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                Toast.makeText(AdminAllPlacesActivity.this,"adapter is set",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminAllPlacesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(AdminAllPlacesActivity.this,"adapter is set after",Toast.LENGTH_SHORT).show();
    }
}