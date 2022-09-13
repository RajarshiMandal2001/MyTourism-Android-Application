package com.example.mytourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserTypeActivity extends AppCompatActivity {
    private ImageSlider imageSlider1;
    private ImageSlider imageSlider2;
    private ImageSlider imageSlider3;

    private Button mCustomer;
    private Button mExplorer;
    private Button mAdmin;
    private Button mHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        mCustomer = findViewById(R.id.customer);
        mExplorer = findViewById(R.id.explorer);
        mAdmin = findViewById(R.id.admin);
        mHotel = findViewById(R.id.hotel);

        imageSlider1 = findViewById(R.id.image_slider_1);
        ArrayList<SlideModel> images1 = new ArrayList<>();
        imageSlider2 = findViewById(R.id.image_slider_2);
        ArrayList<SlideModel> images2 = new ArrayList<>();
        imageSlider3 = findViewById(R.id.image_slider_3);
        ArrayList<SlideModel> images3 = new ArrayList<>();

        images1.add(new SlideModel(R.drawable.taj_mahal,"Taj Mahal",null));
        images1.add(new SlideModel(R.drawable.khajuraho,"Khajuraho",null));
        images1.add(new SlideModel(R.drawable.golden_temple,"Golden Temple",null));
        imageSlider1.setImageList(images1, ScaleTypes.CENTER_CROP);
        images2.add(new SlideModel(R.drawable.tea_field,"Tea Garden",null));
        images2.add(new SlideModel(R.drawable.temple,"Old Temples",null));
        images2.add(new SlideModel(R.drawable.waterfall,"7 Sisters",null));
        imageSlider2.setImageList(images2, ScaleTypes.CENTER_CROP);
        images3.add(new SlideModel(R.drawable.rhyno,"Asian Rhyno",null));
        images3.add(new SlideModel(R.drawable.tiger,"Bengal Tiger",null));
        images3.add(new SlideModel(R.drawable.deers,"Golden Deers",null));
        imageSlider3.setImageList(images3, ScaleTypes.CENTER_CROP);


        mCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mExplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this,AuthenticateExplorer.class);
                startActivity(intent);
            }
        });
        mAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this,AuthenticateAdminActivity.class);
                startActivity(intent);
            }
        });
        mHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this,HotelSubscriptionActivity.class);
                startActivity(intent);
            }
        });
    }
}