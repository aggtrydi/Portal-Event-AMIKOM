package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobile.portaleventamikom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddEvent extends AppCompatActivity {

    FirebaseAuth uAuth;
    DatabaseReference dbRef;

    EditText etJudul,etDeskripsi;
    ImageView imgviewPoster;
    Button btnUpload;

    //UserInfo
    String nama,nim,uid,dp,email;



    ProgressDialog pd;
    //permision contain
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    //ImagePick Constain
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    //permission array
    String[] cameraPermission;
    String[] storagePermission;

    Uri img_uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        pd = new ProgressDialog(this);

        uAuth = FirebaseAuth.getInstance();
        checkUserStatus();



        etJudul= (EditText)findViewById(R.id.etJudul);
        etDeskripsi = (EditText) findViewById(R.id.etDeskripsi);
        imgviewPoster = (ImageView) findViewById(R.id.imgviewPoster);
        btnUpload = (Button)findViewById(R.id.btnUpload);

        //init permmision array
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};



        //mendapatkab beberapa info user yang dimasukan
        dbRef = FirebaseDatabase.getInstance().getReference("Use");
        Query query = dbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    nama = ""+ds.child("nama").getValue();
                    email = ""+ds.child("email").getValue();
                    nim = ""+ds.child("nim").getValue();
                    dp = ""+ds.child("image").getValue();

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        imgviewPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul= etJudul.getText().toString().trim();
                String deskripsi= etDeskripsi.getText().toString().trim();

                if (TextUtils.isEmpty(judul)){
                    Toast.makeText(AddEvent.this,"Masukkan Title...",Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(deskripsi)){
                    Toast.makeText(AddEvent.this,"Masukkan Deskripsi...",Toast.LENGTH_SHORT).show();
                    return;

                }
                if (img_uri==null){
                    uploadData(judul,deskripsi,"noImage");

                }
                else {
                    uploadData(judul,deskripsi,String.valueOf(img_uri));
                }

            }
        });
    }

    private void uploadData(final String judul, final String deskcripsi, String uri) {

        pd.setMessage("Upload Postingan...");
        pd.show();


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
                                hashMap.put("uid", uid);
                                hashMap.put("nama", nama);
                                hashMap.put("nim", nim);
                                hashMap.put("email", email);
                                hashMap.put("dp", dp);
                                hashMap.put("pId", timestamp);
                                hashMap.put("pJudul", judul);
                                hashMap.put("pDescr", deskcripsi);
                                hashMap.put("pImage", downUri);
                                hashMap.put("pTimes", timestamp);

                                //path store post data

                                DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Postingan");

                                reff.child(timestamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pd.dismiss();
                                                Toast.makeText(AddEvent.this, "Postingan di publish", Toast.LENGTH_SHORT).show();

                                                etJudul.setText("");
                                                etDeskripsi.setText("");
                                                imgviewPoster.setImageURI(null);
                                                img_uri = null;
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(AddEvent.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                                    }
                                });
                            }
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(AddEvent.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


                }
            });
        }else {


            HashMap<Object,String> hashMap = new HashMap<>();
            hashMap.put("uid",uid);
            hashMap.put("nama",nama);
            hashMap.put("nim",nim);
            hashMap.put("email",email);
            hashMap.put("dp",dp);
            hashMap.put("pId",timestamp);
            hashMap.put("pJudul",judul);
            hashMap.put("pDescr",deskcripsi);
            hashMap.put("pImage","noImage");
            hashMap.put("pTimes",timestamp);

            //path store post data

            DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Postingan");

            reff.child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(AddEvent.this,"Postingan di publish",Toast.LENGTH_SHORT).show();

                            etJudul.setText("");
                            etDeskripsi.setText("");
                            imgviewPoster.setImageURI(null);
                            img_uri = null;

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(AddEvent.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


                }
            });

        }

    }

    private void showImagePickDialog() {

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

    private void pickFromGalery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        img_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cv);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,img_uri);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);


    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//goto menu sebelumnya
        return super.onSupportNavigateUp();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private void checkUserStatus() {
        FirebaseUser user = uAuth.getCurrentUser();
        if (user != null) {

//            txtUser.setText(user.getEmail());
//            txtNama.setText(user.getDisplayName());
            email = user.getEmail();
            uid = user.getUid();

        } else {
            Intent i = new Intent(AddEvent.this, MenuActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.btnLogoutUser) {
            uAuth.signOut();
        }

        checkUserStatus();
        return super.onOptionsItemSelected(item);
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
                img_uri = data.getData();

                imgviewPoster.setImageURI(img_uri);
                // Picasso.get().load(img_uri).into(pImage);
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //img_uri = data.getData();
                imgviewPoster.setImageURI(img_uri);
                // Picasso.get().load(img_uri).into(pImage);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    protected void onPostResume() {
        checkUserStatus();
        super.onPostResume();
    }
}



