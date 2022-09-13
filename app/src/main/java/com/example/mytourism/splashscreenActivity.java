package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class splashscreenActivity extends AppCompatActivity {
    private TextView mDate;
    DatabaseReference mDatabaseRefPlace;
    DatabaseReference mDatabaseRefHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Handler handler;
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splashscreen);

        mDatabaseRefPlace = FirebaseDatabase.getInstance().getReference("places");  //upto getInstance it gives that http:// of firebase and then upto getReference it gives that parent id under which places will be kept
        mDatabaseRefHotel = FirebaseDatabase.getInstance().getReference("hotels");

//        mDatabaseRefPlace.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                places p = snapshot.getValue(places.class);
//                //push notification that new place is added
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    NotificationChannel notificationChannel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
//                    NotificationManager manager = getSystemService(NotificationManager.class);
//                    manager.createNotificationChannel(notificationChannel);
//                }
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(splashscreenActivity.this, "n");
//                builder.setContentText("New notification Hello");
//                builder.setSmallIcon(R.mipmap.ic_launcher);
//                builder.setAutoCancel(true);
//                builder.setContentText("New place to be added come and explore!!! "+p.getmPlaceName());
//                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(splashscreenActivity.this);
//                managerCompat.notify(999,builder.build());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                //no code required
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                //no code required
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                //no code required
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                //no code required
//            }
//        });
//
//        mDatabaseRefHotel.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Hotels h = snapshot.getValue(Hotels.class);
//                //push notification that new hotel is added
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    NotificationChannel notificationChannel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
//                    NotificationManager manager = getSystemService(NotificationManager.class);
//                    manager.createNotificationChannel(notificationChannel);
//                }
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(splashscreenActivity.this, "n");
//                builder.setContentText("New notification Hello");
//                builder.setSmallIcon(R.mipmap.ic_launcher);
//                builder.setAutoCancel(true);
//                builder.setContentText("New hotel added come and explore!!! "+h.getmHotelName());
//                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(splashscreenActivity.this);
//                managerCompat.notify(999,builder.build());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                //no code required
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                //no code required
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                //no code required
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                //no code required
//            }
//        });

//        FcmNotificationSender notificationSender = new FcmNotificationSender("/topics/n","hello1","hello2",getApplicationContext(),splashscreenActivity.this);
//        notificationSender.SendNotifications();

        mDate = findViewById(R.id.date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        mDate.setText(dateFormat.format(date));

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashscreenActivity.this,UserTypeActivity.class);
                startActivity(intent);
                finish();
            }
        },8000);
    }
}