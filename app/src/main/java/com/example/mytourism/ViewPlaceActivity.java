package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewPlaceActivity extends AppCompatActivity {
    private ImageSlider imageSlider;

    private TextView mPlaceName;
    private TextView mGeneralInfo;
    private TextView mNoofSights;
    private TextView mApproxCost;
    private TextView mSpots;
    private TextView mDirection;
    private String placeValue;
    private String[] placeValueSplit;
//    private ImageView mImg1;
//    private ImageView mImg2;
//    private ImageView mImg3;
    /*private FloatingActionButton mHotels;
    private FloatingActionButton mWeather;
    private FloatingActionButton mMap;
    private FloatingActionButton mReview;*/
    private Button mHotels;
    private Button mWeather;
    private Button mMap;
    private Button mReview;

    places place;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
        this.setTitle("Loving this place?");

        mPlaceName = findViewById(R.id.place_name_text_view);
        mGeneralInfo = findViewById(R.id.general_info_text_view);
        mSpots = findViewById(R.id.spots_text_view);
        mNoofSights = findViewById(R.id.no_of_sights_text_view);
        mApproxCost = findViewById(R.id.approx_cost_text_view);
        mDirection = findViewById(R.id.place_direction_text_view);
        mHotels = findViewById(R.id.button_hotel);
        mWeather = findViewById(R.id.button_weather);
        mMap = findViewById(R.id.button_map);
        mReview = findViewById(R.id.button_review);

//        mImg1 = findViewById(R.id.image_view_1);
//        mImg2 = findViewById(R.id.image_view_2);
//        mImg3 = findViewById(R.id.image_view_3);

        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> images = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeValue = extras.getString("PLACE_NAME");
            placeValueSplit = placeValue.split(",    ");
            Toast.makeText(ViewPlaceActivity.this,"got it "+placeValueSplit[0],Toast.LENGTH_SHORT).show();
        }

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("places");
        place = new places();

        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps(mPlaceName.getText().toString());
                Toast.makeText(ViewPlaceActivity.this,"Map",Toast.LENGTH_SHORT).show();
            }
        });

        mHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPlaceActivity.this,AddedHotels.class);
                intent.putExtra("HOTEL_PLACE",placeValueSplit[0]);
                intent.putExtra("HOTEL_PLACE_STATE",placeValueSplit[1]);
                startActivity(intent);
                Toast.makeText(ViewPlaceActivity.this,"Hotels",Toast.LENGTH_SHORT).show();
            }
        });

        mReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPlaceActivity.this,"Review",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewPlaceActivity.this,ReviewActivity.class);
                intent.putExtra("HOTEL_PLACE",placeValueSplit[0]);
                startActivity(intent);
            }
        });

        mWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPlaceActivity.this,"Weather",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("https://www.wunderground.com/weather/in/"+mPlaceName.getText().toString());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    place = d.getValue(places.class);
                    if ((place.getmPlaceName()).equals(placeValueSplit[0]) && (place.getmPlaceStateName()).equals(placeValueSplit[1])){
                        mPlaceName.setText(place.getmPlaceName());
                        mGeneralInfo.setText(place.getmGeneralInfo());
                        mSpots.setText(place.getmSpots());
                        mDirection.setText(place.getmDirection());
                        mNoofSights.setText(String.valueOf(place.getmNoOfSightsToExplore()));
                        mApproxCost.setText(String.valueOf(place.getmApproxCost()));

                        images.add(new SlideModel(place.getmImage1(),"loaded from url",null));
                        images.add(new SlideModel(place.getmImage2(),"loaded from url",null));
                        images.add(new SlideModel(place.getmImage3(),"loaded from url",null));
                        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);
//                        Picasso.get().load(place.getmImage1()).into(mImg1);
//                        Picasso.get().load(place.getmImage2()).into(mImg2);
//                        Picasso.get().load(place.getmImage3()).into(mImg3);
                        break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewPlaceActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMaps(String destination) {
        try{
            String demoCurrentLocation = "your loaction here";
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + demoCurrentLocation + "/" + destination);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            Toast.makeText(ViewPlaceActivity.this,"in catch",Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}