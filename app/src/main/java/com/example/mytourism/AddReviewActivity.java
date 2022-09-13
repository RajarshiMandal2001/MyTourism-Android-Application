package com.example.mytourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddReviewActivity extends AppCompatActivity {
    private Button mSubmit;
    private String placeValue;

    private EditText mCustomerName;
    private EditText mCustomerReview;
    private EditText mCustomerRating;

    Reviews review;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        this.setTitle("Your Reviews Matter");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeValue = extras.getString("HOTEL_PLACE");
        }

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("reviews");

        mCustomerName = findViewById(R.id.customer_name_edit_text);
        mCustomerRating = findViewById(R.id.customer_rating_edit_text);
        mCustomerReview = findViewById(R.id.customer_review_edit_text);
        mSubmit = findViewById(R.id.submit_review_button);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uploadId = mDatabaseRef.push().getKey();
                review = new Reviews(placeValue,mCustomerName.getText().toString(),mCustomerReview.getText().toString(),Integer.parseInt(mCustomerRating.getText().toString()));
                mDatabaseRef.child(uploadId).setValue(review);
                Toast.makeText(AddReviewActivity.this,"review added to db",Toast.LENGTH_SHORT).show();
            }
        });
    }
}