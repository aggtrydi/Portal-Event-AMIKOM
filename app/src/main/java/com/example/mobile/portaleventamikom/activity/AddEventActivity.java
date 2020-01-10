package com.example.mobile.portaleventamikom.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mobile.portaleventamikom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddEventActivity extends AppCompatActivity {
    FirebaseAuth eAuth;
    DatabaseReference dbRef;

    EditText etEventJudul, etEtEventDeskripsi;
    ImageView imgEventUplaod;
    Button btnUpload;

    //User Info
    String uId, uNama, uNim, uImage, uEmail;

    //permision contain
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    //ImagePick Constain
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    String[] cameraPermission;
    String[] storagePermission;

    Uri uri_image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eAuth =  FirebaseAuth.getInstance();
        cekStatusUser();

        etEventJudul = (EditText)findViewById(R.id.etJudul);
        etEtEventDeskripsi = (EditText)findViewById(R.id.etDeskripsi);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        imgEventUplaod = (ImageView)findViewById(R.id.imgviewPoster);

        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imgEventUplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePicker();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul= etEventJudul.getText().toString().trim();
                String deskripsi= etEtEventDeskripsi.getText().toString().trim();

                if (TextUtils.isEmpty(judul)){
                    Toast.makeText(AddEventActivity.this,"Masukkan Title...",Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(deskripsi)){
                    Toast.makeText(AddEventActivity.this,"Masukkan Deskripsi...",Toast.LENGTH_SHORT).show();
                    return;

                }
                if (uri_image==null){
                    uploadData(judul,deskripsi,"noImage");

                }
                else {
                    uploadData(judul,deskripsi,String.valueOf(uri_image));
                }

            }
        });
    }

    private void cekStatusUser() {
        FirebaseUser user = eAuth.getCurrentUser();
        if (user != null) {

//            txtUser.setText(user.getEmail());
//            txtNama.setText(user.getDisplayName());
            uEmail = user.getEmail();
            uId = user.getUid();

        } else {
            Intent i = new Intent(AddEventActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void uploadData(final String judul, final String deskripsi, String uri) {
        final String timestamp = String.valueOf(System.currentTimeMillis());

        String filePathAndName= "Posts/" + "Post_"+timestamp;
        if (!uri.equals("noImage")){
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            ref.putFile(Uri.parse(uri))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            String downUri = uriTask.getResult().toString();


                            if (uriTask.isSuccessful()) {
                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("uId", uId);
                                hashMap.put("uNama", uNama);
                                hashMap.put("uNim", uNim);
                                hashMap.put("uEmail", uEmail);
                                hashMap.put("uImage", uImage);
                                hashMap.put("eId", timestamp);
                                hashMap.put("eJudul", judul);
                                hashMap.put("eDeskripsi", deskripsi);
                                hashMap.put("eImage", downUri);
                                hashMap.put("eTimes", timestamp);

                                //path store post data

                                DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Event");

                                reff.child(timestamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddEventActivity.this, "Event di publish", Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent(AddEventActivity.this, UtamaActivity.class);
                                                startActivity(i);
                                                finish();
//                                                etEventJudul.setText("");
//                                                etEtEventDeskripsi.setText("");
//                                                imgEventUplaod.setImageURI(null);
//                                                uri_image = null;
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddEventActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                                    }
                                });
                            }
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddEventActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


                }
            });
        }else {


            HashMap<Object,String> hashMap = new HashMap<>();
            hashMap.put("uId", uId);
            hashMap.put("uNama", uNama);
            hashMap.put("uNim", uNim);
            hashMap.put("uEmail", uEmail);
            hashMap.put("uImage", uImage);
            hashMap.put("eId", timestamp);
            hashMap.put("eJudul", judul);
            hashMap.put("eDeskripsi", deskripsi);
            hashMap.put("eImage", "noImage");
            hashMap.put("eTimes", timestamp);


            //path store post data

            DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Event");

            reff.child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddEventActivity.this,"Event di publish",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AddEventActivity.this, UtamaActivity.class);
                            startActivity(i);
                            finish();

//                            etEventJudul.setText("");
//                            etEtEventDeskripsi.setText("");
//                            imgEventUplaod.setImageURI(null);
//                            uri_image = null;

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddEventActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


                }
            });

        }
    }

    private void showImagePicker() {

        String options[]={"Camera","Gallery"};

        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Gambar");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }
                }
                if (which == 1) {
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else {
                        pickFromGalery();
                    }
                }
            }

        });
        builder.create().show();
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void pickFromGalery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        uri_image = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri_image);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted= grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted= grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this,"Permission camera dan storage diperlukan... ",Toast.LENGTH_SHORT).show();
                    }
                }else {

                }

            }break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted= grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGalery();
                    }
                    else {
                        Toast.makeText(this,"Permission storage diperlukan... ",Toast.LENGTH_SHORT).show();
                    }
                }

            }break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode== RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                uri_image = data.getData();

                imgEventUplaod.setImageURI(uri_image);
                // Picasso.get().load(img_uri).into(pImage);
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //img_uri = data.getData();
                imgEventUplaod.setImageURI(uri_image);
                // Picasso.get().load(img_uri).into(pImage);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}
