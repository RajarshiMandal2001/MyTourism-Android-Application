package com.example.mytourism;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;

public class GeneratePasswordActivity extends AppCompatActivity {
    private EditText mEmailAddress;
    private Button mPay;
    private TextView mGeneratedPassword;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password);

        mEmailAddress = findViewById(R.id.email_edit_text);
        mPay = findViewById(R.id.pay_button);
        mGeneratedPassword = findViewById(R.id.generated_password_text_view);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("RegisteredHotels");

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDB();
            }
        });
    }

    private void insertIntoDB() {
        if(mEmailAddress.getText().toString().trim().equals("")){
            Toast.makeText(GeneratePasswordActivity.this,"Email address not provided",Toast.LENGTH_SHORT).show();
            return;
        }
        String uploadId = mDatabaseRef.push().getKey();
        RegisteredHotels rh = new RegisteredHotels(mEmailAddress.getText().toString().trim(),randomPassword(5) );  // 5 here shows length of password to be generated
        mDatabaseRef.child(uploadId).setValue(rh);
        Toast.makeText(GeneratePasswordActivity.this,"db updated",Toast.LENGTH_SHORT).show();
        mGeneratedPassword.setText(rh.getmPassword());
    }

    private String randomPassword(int len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
}