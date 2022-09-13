package com.example.mytourism;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewHotelActivity extends AppCompatActivity {
    private static final  int PICK_IMAGE_REQUEST = 1;
    private EditText mHotelPlaceStateName;
    private EditText mHotelPlaceName;
    private EditText mHotelBHK;
    private EditText mHotelCost;
    private EditText mHotelName;
    private EditText mHotelDescription;
    private EditText mHotelWebsiteLink;
    private TextView mImage1;
    private TextView mImage2;
    private TextView mImage3;

    private StorageTask mUploadTask;

    private Button mButtonUpload;
    private Button mButtonChooseImages;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    Uri[] imageUriArray = new Uri[3];        //these 2 arrays are required to store name of the image,max 3 images can be choosen
    String[] imageNameInmillisecArray = new String[3];
    String[] downloadUrls = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_hotel);
        this.setTitle("Add New Hotel");
        FirebaseMessaging.getInstance().subscribeToTopic("n");

        mHotelPlaceStateName = findViewById(R.id.hotel_place_state_name_edit_text);
        mHotelPlaceName = findViewById(R.id.hotel_place_name_edit_text);
        mHotelName = findViewById(R.id.hotel_name_edit_text);
        mHotelBHK = findViewById(R.id.hotel_bhk_edit_text);
        mHotelCost = findViewById(R.id.hotel_cost_edit_text);
        mHotelDescription = findViewById(R.id.hotel_description_edit_text);
        mHotelWebsiteLink = findViewById(R.id.hotel_link_edit_text);
        mImage1 = findViewById(R.id.hotel_image1_text_view);
        mImage2 = findViewById(R.id.hotel_image2_text_view);
        mImage3 = findViewById(R.id.hotel_image3_text_view);

        mButtonChooseImages = findViewById(R.id.choose_hotel_image_button);
        mButtonUpload = findViewById(R.id.hotel_upload_button);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("hotels");

        mButtonChooseImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(AddNewHotelActivity.this,"in progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                    FcmNotificationSender notificationSender = new FcmNotificationSender("/topics/n","New hotel will be added soon!!",mHotelPlaceName.getText().toString()+"     ,"+mHotelPlaceStateName.getText().toString(),getApplicationContext(),AddNewHotelActivity.this);
                    notificationSender.SendNotifications();
                }
            }
        });
        mButtonChooseImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
    }
    public void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent,"Select Hotel Pictures"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            if(data.getClipData() != null){
                int howManySelected = data.getClipData().getItemCount();
                if (howManySelected != 3){
                    Toast.makeText(AddNewHotelActivity.this,"please choose 3 images",Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0;i<howManySelected;i++){
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUriArray[i] = imageUri;
                    imageNameInmillisecArray[i] = System.currentTimeMillis() + "." + getFileExtension(imageUri);
                }

                mImage1.setText(imageNameInmillisecArray[0]);
                mImage2.setText(imageNameInmillisecArray[1]);
                mImage3.setText(imageNameInmillisecArray[2]);
            }
            else if(data.getData() != null){  //if seleccted only one image
                Toast.makeText(AddNewHotelActivity.this,"select 3 images",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileExtension(Uri uri){   //its only function is to get the extension of the image we have selected
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile(){
        final int[] sf = {0};
        Toast.makeText(AddNewHotelActivity.this,"Please Wait",Toast.LENGTH_SHORT).show();
        for(int i=0;i<3;i++){
            Toast.makeText(AddNewHotelActivity.this,"Processing"+i,Toast.LENGTH_SHORT).show();
            StorageReference fileReference = mStorageRef.child("hotels").child(imageNameInmillisecArray[i]);
            int finalI = i;
            mUploadTask = fileReference.putFile(imageUriArray[i]).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUri = uri.toString();
                            Log.d("url","url="+downloadUri);
                            downloadUrls[finalI] = downloadUri;
                            Log.d("url","captured url="+downloadUrls[finalI]);
                            Toast.makeText(AddNewHotelActivity.this,"finalI="+finalI,Toast.LENGTH_SHORT).show();
                            if(finalI==2){  //very very very confusing control flow here !!!!
                                insertIntoDB();
                            }
                            sf[0] = 1;
                        }
                    });

                    Toast.makeText(AddNewHotelActivity.this,"Uploading successfull"+finalI,Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    Toast.makeText(AddNewHotelActivity.this,"Uploading Image"+finalI,Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddNewHotelActivity.this,"error got"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        Toast.makeText(AddNewHotelActivity.this,"Back tracking",Toast.LENGTH_SHORT).show();
    }

    private void insertIntoDB() {
        String uploadId = mDatabaseRef.push().getKey();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        Toast.makeText(AddNewHotelActivity.this,uploadId,Toast.LENGTH_SHORT).show();
        Hotels hotel = new Hotels(mHotelPlaceStateName.getText().toString().trim(),
                mHotelPlaceName.getText().toString().trim(),
                mHotelName.getText().toString(),
                mHotelBHK.getText().toString(),
                mHotelCost.getText().toString(),
                mHotelDescription.getText().toString(),
                mHotelWebsiteLink.getText().toString(),
                downloadUrls[0],downloadUrls[1],downloadUrls[2],
                dateFormat.format(date),uploadId,0);
        try {
            for (int i = 0; i < 3; i++) {
                Log.d("uriArrayHotels",downloadUrls[i]);
            }
        }
        catch (Exception e){
            Log.v("error",e.getMessage());
        }
        mDatabaseRef.child(uploadId).setValue(hotel);
        Toast.makeText(AddNewHotelActivity.this,"DB updated,Hotel Added successfully",Toast.LENGTH_SHORT).show();
        finish();
    }
}