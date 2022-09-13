package com.example.mytourism;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddedHotels extends AppCompatActivity {
    SearchView searchView;
    ProgressBar progressBar;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> hotelList;
    ArrayAdapter<String> adapter;
    Hotels hotel;
    private String state_name;
    private String place_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_hotels);
        this.setTitle("Hotels");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            state_name = extras.getString("HOTEL_PLACE_STATE");
            place_name = extras.getString("HOTEL_PLACE");
        }

        hotel = new Hotels();  //this is why empty constructor was needed
        searchView = findViewById(R.id.search_hotels_search_view);
        progressBar = findViewById(R.id.progress_bar_hotels);
        listView = findViewById(R.id.added_hotels_list_view);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("hotels");
        hotelList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.hotel_name,R.id.hotelName,hotelList);

        progressBar.setVisibility(View.VISIBLE);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int empty = 0;
                for(DataSnapshot d:snapshot.getChildren()){
                    Toast.makeText(AddedHotels.this,"wtf",Toast.LENGTH_SHORT).show();
                    hotel = d.getValue(Hotels.class);
                    if(hotel.getmHotelPlaceStateName().equals(state_name) && hotel.getmHotelPlaceName().equals(place_name)){
                        hotelList.add(hotel.getmHotelName()+",   "+hotel.getmHotelPlaceName()+",   "+hotel.getmHotelPlaceStateName()+",   "+hotel.getmHotelBHK()+" BHK");  //see only added place names and on click on them see all info in ViewPlaceActivity
                        // .add() sets the text of the widget with text given as its arguments whose id is given in adapter here R.id.placeName
                        empty = 1;
                    }
                }
                if (empty == 0){
                    Toast.makeText(AddedHotels.this,"no value",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddedHotels.this,NoValueActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddedHotels.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.hotelName);
//                Toast.makeText(AddedPlaces.this,textView.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddedHotels.this,ViewHotelActivity.class);
                intent.putExtra("HOTEL_PLACE_NAME", textView.getText());
                intent.putExtra("FROM","AddedHotels");
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}