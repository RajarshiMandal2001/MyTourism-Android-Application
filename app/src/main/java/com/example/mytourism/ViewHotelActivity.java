package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewHotelActivity extends AppCompatActivity {
    private ImageSlider imageSlider;

    private TextView mHotelName;
    private TextView mHotelBHK;
    private TextView mHotelCost;
    private String placeValue;
    private String[] placeValueSplit;
    private TextView mHotelDescription;
    private TextView mHotelWebsiteLink;
    private TextView mBookNowLink;
    String[] bhkSplit;
    String previousActivity;

    Hotels hotel;

    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel);
        this.setTitle("Love this Hotel?");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeValue = extras.getString("HOTEL_PLACE_NAME");
            placeValueSplit = placeValue.split(",");   //hotelname,placename,statename
            bhkSplit = (placeValueSplit[3].trim()).split(" ");  //bhk sent in format 3 BHK
            previousActivity = extras.getString("FROM");
            Toast.makeText(ViewHotelActivity.this,"from "+previousActivity,Toast.LENGTH_SHORT).show();
        }

        mHotelName = findViewById(R.id.hotel_name_text_view);
        mHotelBHK = findViewById(R.id.bhk_text_view);
        mHotelCost = findViewById(R.id.hotel_cost_text_view);
        mHotelDescription = findViewById(R.id.hotel_general_info_text_view);
        mHotelWebsiteLink = findViewById(R.id.hotel_website_link_text_view);
        mBookNowLink = findViewById(R.id.book_hotel_now_link_text_view);

        imageSlider = findViewById(R.id.hotel_image_slider);

        ArrayList<SlideModel> images = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("hotels");
        hotel = new Hotels();

        mBookNowLink.setText("https://docs.google.com/forms/d/e/1FAIpQLSdreY_xpDLpIn-6F7c8jGKRjltJAP8TST5I8Yx24PicP50bEA/" +
                "viewform?usp=pp_url&entry." +
                "2031159419="+hotel.getmHotelName()+"&entry.337825363="+hotel.getmHotelBHK()+"&entry.1201739429="+hotel.getmHotelPlaceName()+"&entry.973441556="+hotel.getmHotelPlaceStateName());

        mHotelWebsiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mHotelWebsiteLink.getText().toString());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        mBookNowLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSdreY_xpDLpIn-6F7c8jGKRjltJAP8TST5I8Yx24PicP50bEA/" +
                        "viewform?usp=pp_url&entry." +
                        "2031159419="+hotel.getmHotelName()+"&entry.337825363="+hotel.getmHotelBHK()+"&entry.1201739429="+hotel.getmHotelPlaceName()+"&entry.973441556="+hotel.getmHotelPlaceStateName());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    hotel = d.getValue(Hotels.class);
                    if ((hotel.getmHotelName()).equals(placeValueSplit[0].trim()) &&
                            (hotel.getmHotelPlaceName()).equals(placeValueSplit[1].trim()) &&
                            (hotel.getmHotelPlaceStateName()).equals(placeValueSplit[2].trim()) &&
                            (hotel.getmHotelBHK()).equals(bhkSplit[0]) &&
                            (((hotel.getIsApproved()==1) || previousActivity.equals("AdminAllHotelsActivity")))){

                        mHotelName.setText(hotel.getmHotelName());
                        mHotelBHK.setText(hotel.getmHotelBHK());
                        mHotelCost.setText(hotel.getmHotelCost());
                        mHotelDescription.setText((hotel.getmHotelDescription()));
                        mHotelWebsiteLink.setText(hotel.getmHotelWebsiteLink());
                        mBookNowLink.setText("Book this room Now!!!");
                        images.add(new SlideModel(hotel.getmImage1(),"loaded from url",null));
                        images.add(new SlideModel(hotel.getmImage2(),"loaded from url",null));
                        images.add(new SlideModel(hotel.getmImage3(),"loaded from url",null));
                        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);
                        break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewHotelActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}