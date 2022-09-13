package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddedPlaces extends AppCompatActivity {
    private ProgressBar progressBar;
    SearchView searchView;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> placeList;
    ArrayAdapter<String> adapter;
    places place;
    private String state_name;
    String catagory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_places);
        this.setTitle("Tourist Spots");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            state_name = extras.getString("STATE_NAME");
            catagory = extras.getString("CATAGORY");
        }

        place = new places();
        progressBar = findViewById(R.id.places_progress_bar);
        listView = findViewById(R.id.added_places_list_view);
        searchView = findViewById(R.id.search_place_search_view);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("places");
        placeList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.place_name,R.id.placeName,placeList);

        progressBar.setVisibility(View.VISIBLE);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int empty = 0;
                for(DataSnapshot d:snapshot.getChildren()){
                    place = d.getValue(places.class);
                    if(place.getmPlaceStateName().equals(state_name) && place.getmCatagory().equals(catagory) && (place.getIsApproved() == 1)){
                        placeList.add(place.getmPlaceName()+",    "+place.getmPlaceStateName()+",    "+place.getmDirection());  //see only added place names and on click on them see all info in ViewPlaceActivity
                        // .add() sets the text of the widget with text given as its arguments whose id is given in adapter here R.id.placeName
                        empty = 1;
                    }
                }
                if (empty == 0){
                    Intent intent = new Intent(AddedPlaces.this,NoValueActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddedPlaces.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.placeName);
//                Toast.makeText(AddedPlaces.this,textView.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddedPlaces.this,ViewPlaceActivity.class);
                intent.putExtra("PLACE_NAME", textView.getText());
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //works only when whole text is typed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //keeps filtering items on entering characters
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}