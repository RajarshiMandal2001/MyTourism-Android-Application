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

public class AuthenticateExplorer extends AppCompatActivity {
    private EditText mPassword;
    private Button mEnter;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_explorer);

        mPassword = findViewById(R.id.explorer_password_edit_text);
        mEnter = findViewById(R.id.enter_explorer_password_button);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("ExplorerPassword");
//        DatabaseReference pass = ref.child("password");

        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] flag = {0};
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(AuthenticateExplorer.this,"Processing password",Toast.LENGTH_SHORT).show();
                        for(DataSnapshot d:snapshot.getChildren()){
                            Toast.makeText(AuthenticateExplorer.this,"checking",Toast.LENGTH_SHORT).show();
                            String password = (String) d.getValue();
//                            Toast.makeText(AuthenticateExplorer.this,password,Toast.LENGTH_SHORT).show();
                            if (password.equals(mPassword.getText().toString().trim())){
                                Intent intent = new Intent(AuthenticateExplorer.this,AddNewPlaceActivity.class);
                                startActivity(intent);
                                finish();
                                flag[0] = 1;
                                break;
                            }
                        }
                        if(flag[0] == 0){
                            Toast.makeText(AuthenticateExplorer.this,"Password didn't match",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AuthenticateExplorer.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}