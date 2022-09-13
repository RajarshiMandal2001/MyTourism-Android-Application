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

public class AddNewPlaceActivity extends AppCompatActivity {
    private static final  int PICK_IMAGE_REQUEST = 1;
    private Button mButtonUpload;
    private Button mButtonChooseImages;

    private StorageTask mUploadTask;

    private EditText mPlaceStateName;
    private EditText mPlaceName;
    private EditText mCatagory;
    private EditText mGeneralInfo;
    private EditText mNoofSights;
    private EditText mSpots;
    private EditText mApproxCost;
    private EditText mDirection;
    private TextView mImage1;
    private TextView mImage2;
    private TextView mImage3;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    Uri[] imageUriArray = new Uri[3];        //these 2 arrays are required to store name of the image,max 3 images can be choosen
    String[] imageNameInmillisecArray = new String[3];

    String[] downloadUrls = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);
        this.setTitle("Add New Tourist Place");
        FirebaseMessaging.getInstance().subscribeToTopic("n");

        mPlaceStateName = findViewById(R.id.place_state_name_edit_text);
        mPlaceName = findViewById(R.id.place_name_edit_text);
        mCatagory = findViewById(R.id.place_catagory_edit_text);
        mGeneralInfo = findViewById(R.id.general_info_edit_text);
        mNoofSights = findViewById(R.id.no_of_sights_edit_text);
        mSpots = findViewById(R.id.spots_edit_text);
        mApproxCost = findViewById(R.id.approx_cost_edit_text);
        mDirection = findViewById(R.id.place_direction_edit_text);
        mImage1 = findViewById(R.id.image1_text_view);
        mImage2 = findViewById(R.id.image2_text_view);
        mImage3 = findViewById(R.id.image3_text_view);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("places");

        mButtonUpload = findViewById(R.id.upload_button);
        mButtonChooseImages = findViewById(R.id.choose_image_button);

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(AddNewPlaceActivity.this,"in progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                    //send notification that data is added
                    FcmNotificationSender notificationSender = new FcmNotificationSender("/topics/n","New place will be added soon!!",mPlaceName.getText().toString()+"    ,"+mPlaceStateName.getText().toString(),getApplicationContext(),AddNewPlaceActivity.this);
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
    private void uploadFile(){
        final int[] sf = {0};
        Toast.makeText(AddNewPlaceActivity.this,"Please Wait",Toast.LENGTH_SHORT).show();
        for(int i=0;i<3;i++){
            Toast.makeText(AddNewPlaceActivity.this,"Progress.."+i,Toast.LENGTH_SHORT).show();
            StorageReference fileReference = mStorageRef.child("places").child(imageNameInmillisecArray[i]);
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
                            Toast.makeText(AddNewPlaceActivity.this,"finalI="+finalI,Toast.LENGTH_SHORT).show();
                            if(finalI==2){  //very very very confusing control flow here !!!!
                                insertIntoDB();
                            }
                            sf[0] = 1;
                        }
                    });
                    Toast.makeText(AddNewPlaceActivity.this,"uploading successfull"+finalI,Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    Toast.makeText(AddNewPlaceActivity.this,"Uploading Image" + finalI,Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddNewPlaceActivity.this,"Error got"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
        Toast.makeText(AddNewPlaceActivity.this,"Back tracking",Toast.LENGTH_SHORT).show();

//        String uploadId = mDatabaseRef.push().getKey();  //push() generates ubique values for keys
//        places p = new places(mPlaceStateName.getText().toString().trim(),mPlaceName.getText().toString().trim(),mGeneralInfo.getText().toString(),Integer.parseInt(mNoofSights.getText().toString()),Integer.parseInt(mApproxCost.getText().toString()),mDirection.getText().toString());
//        mDatabaseRef.child(uploadId).setValue(p);
//        Toast.makeText(AddNewPlaceActivity.this,"Uploaded successfully",Toast.LENGTH_SHORT).show();
    }

    private void insertIntoDB() {
        String uploadId = mDatabaseRef.push().getKey();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        Toast.makeText(AddNewPlaceActivity.this,uploadId,Toast.LENGTH_SHORT).show();
        places place = new places(mPlaceStateName.getText().toString().trim(),
                mPlaceName.getText().toString().trim(),
                mCatagory.getText().toString().trim(),
                mGeneralInfo.getText().toString(),
                mSpots.getText().toString(),
                Integer.parseInt(mNoofSights.getText().toString()),
                Integer.parseInt(mApproxCost.getText().toString()),
                mDirection.getText().toString(),
                downloadUrls[0],downloadUrls[1],downloadUrls[2],
                dateFormat.format(date).toString(),
                uploadId,
                0);

        try {
            for (int i = 0; i < 3; i++) {
                Log.d("uriArray",downloadUrls[i]);
            }
        }
        catch (Exception e){
            Log.v("error",e.getMessage());
        }
        mDatabaseRef.child(uploadId).setValue(place);
        Toast.makeText(AddNewPlaceActivity.this,"DB updated,Place Added Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            if(data.getClipData() != null){
                int howManySelected = data.getClipData().getItemCount();
                if (howManySelected != 3){
                    Toast.makeText(AddNewPlaceActivity.this,"please choose 3 images",Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0;i<howManySelected;i++){
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUriArray[i] = imageUri;
//                    Toast.makeText(AddNewPlaceActivity.this,imageUriArray[i].toString(),Toast.LENGTH_SHORT).show();
                    imageNameInmillisecArray[i] = System.currentTimeMillis() + "." + getFileExtension(imageUri);
                }

                mImage1.setText(imageNameInmillisecArray[0]);
                mImage2.setText(imageNameInmillisecArray[1]);
                mImage3.setText(imageNameInmillisecArray[2]);
            }
            else if(data.getData() != null){
                Toast.makeText(AddNewPlaceActivity.this,"select 3 images",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileExtension(Uri uri){   //its only function is to get the extension of the image we have selected
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}