package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HotelSubscriptionActivity extends AppCompatActivity {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mEnterButton;
    private Button mTakeSubscription;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_subscription);
        this.setTitle("Hotel Subscription");

        mEmailEditText = findViewById(R.id.hotel_email_edit_text);
        mPasswordEditText = findViewById(R.id.hotel_password_editText);
        mEnterButton = findViewById(R.id.hotel_password_button);
        mTakeSubscription = findViewById(R.id.hotel_subscription_button);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("RegisteredHotels");

        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] flag = {0};
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d:snapshot.getChildren()){
                            RegisteredHotels rh = d.getValue(RegisteredHotels.class);
                            if((rh.getmEmailAddress()).equals(mEmailEditText.getText().toString().trim()) && (rh.getmPassword()).equals(mPasswordEditText.getText().toString().trim())){
                                Intent intent = new Intent(HotelSubscriptionActivity.this, AddNewHotelActivity.class);
                                startActivity(intent);
                                flag[0] = 1;
                                break;
                            }
                        }
                        if(flag[0] == 0){
                            Toast.makeText(HotelSubscriptionActivity.this,"Password didn't match",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HotelSubscriptionActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mTakeSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelSubscriptionActivity.this,GeneratePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}