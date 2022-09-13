package com.example.mytourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PlaceCategoryActivity extends AppCompatActivity {
    private ImageView mMountains;
    private ImageView mCostal;
    private ImageView mAdventure;
    private ImageView mCultural;
    private ImageView mHeritage;
    String state_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_category);
        this.setTitle("Place Category");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            state_name = extras.getString("STATE_NAME");
        }

        mAdventure = findViewById(R.id.adventure_imageView);
        mCostal = findViewById(R.id.costal_imageView);
        mCultural = findViewById(R.id.cultural_imageView);
        mHeritage = findViewById(R.id.heritage_imageView);
        mMountains = findViewById(R.id.mountains_imageView);

        mMountains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PlaceCategoryActivity.this,AddedPlaces.class);
                intent.putExtra("STATE_NAME",state_name);
                intent.putExtra("CATAGORY","Mountains");
                startActivity(intent);
            }
        });
        mCostal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PlaceCategoryActivity.this,AddedPlaces.class);
                intent.putExtra("STATE_NAME",state_name);
                intent.putExtra("CATAGORY","Coastal");
                startActivity(intent);
            }
        });
        mCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PlaceCategoryActivity.this,AddedPlaces.class);
                intent.putExtra("STATE_NAME",state_name);
                intent.putExtra("CATAGORY","Cultural");
                startActivity(intent);
            }
        });
        mHeritage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PlaceCategoryActivity.this,AddedPlaces.class);
                intent.putExtra("STATE_NAME",state_name);
                intent.putExtra("CATAGORY","Heritage");
                startActivity(intent);
            }
        });
        mAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PlaceCategoryActivity.this,AddedPlaces.class);
                intent.putExtra("STATE_NAME",state_name);
                intent.putExtra("CATAGORY","Adventure");
                startActivity(intent);
            }
        });
    }
}