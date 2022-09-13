package com.example.mytourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReviewActivity extends AppCompatActivity {
    private Button mAddReviewBtn;
    private Button mSeeReviewsBtn;
    private String placeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        this.setTitle("Reviews");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeValue = extras.getString("HOTEL_PLACE");
        }

        mAddReviewBtn = findViewById(R.id.add_reviews_button);
        mSeeReviewsBtn = findViewById(R.id.see_reviews_button);

        mAddReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this,AddReviewActivity.class);
                intent.putExtra("HOTEL_PLACE",placeValue);
                startActivity(intent);
            }
        });
        mSeeReviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this,ViewReviewsActivity.class);
                intent.putExtra("HOTEL_PLACE",placeValue);
                startActivity(intent);
            }
        });
    }
}