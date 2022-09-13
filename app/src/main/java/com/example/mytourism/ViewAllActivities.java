package com.example.mytourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewAllActivities extends AppCompatActivity {
    private Button mPlacesList;
    private Button mHotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_activities);
        this.setTitle("Activities");

        mPlacesList = findViewById(R.id.all_places_button);
        mHotelList = findViewById(R.id.all_hotels_button);

        mPlacesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllActivities.this,AdminAllPlacesActivity.class);
                startActivity(intent);
            }
        });

        mHotelList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllActivities.this,AdminAllHotelsActivity.class);
                startActivity(intent);
            }
        });
    }
}