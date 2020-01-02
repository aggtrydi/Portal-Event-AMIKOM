package com.example.mobile.portaleventamikom.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.portaleventamikom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    FirebaseAuth uAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference dbref;

    StorageReference strgRef;

    String strgPath = "User_Profil_cover_Image/";

    ImageView imgViewUser, imgviewCover;
    TextView txtViewNama, txtViewNim, txtViewProdi;

    Button btnLogoutUser;
    Button btnAddEvent;
    FloatingActionButton fab;

    String coverorPhoto;
    Uri uri_image;
    String camerapermision[];
    String storagepermision[];

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("user");
        strgRef = FirebaseStorage.getInstance().getReference();

        imgViewUser = findViewById(R.id.imgViewProfil);
        imgviewCover = findViewById(R.id.imgViewCover);
        txtViewNama = findViewById(R.id.txtViewNama);
        txtViewNim = findViewById(R.id.txtViewNim);
        txtViewProdi = findViewById(R.id.txtViewProdi);

        camerapermision = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermision = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        btnLogoutUser = findViewById(R.id.btnLogoutUser);

        fab= findViewById(R.id.fabEdit);



        try{this.getSupportActionBar().hide();}catch(NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Query query = dbref.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String nama = "" + ds.child("nama").getValue();
                    String nim = "" + ds.child("nim").getValue();
                    String jurusan = ""+ds.child("jurusan").getValue();
                    String image = ""+ds.child("image").getValue();

                    txtViewNama.setText(nama);
                    txtViewNim.setText(nim);
                    txtViewProdi.setText(jurusan);
                    try {
                        Picasso.get().load(image).into(imgViewUser);
                    } catch (Exception e){
                        Picasso.get().load(R.drawable.ic_account_circle_black_24dp).into(imgViewUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnLogoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uAuth.signOut();
                checkStatusUser();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfilDialog();
            }
        });

    }
    private void showNameNimProdiUpdate(final String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update "+ key );
        LinearLayout linearLayout= new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        final EditText editText = new EditText(this);
        editText.setHint("Enter" + key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)){
                    HashMap<String,Object> results = new HashMap<>();
                    results.put(key, value);

                    dbref.child(user.getUid()).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(ProfilActivity.this,"Updated...",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ProfilActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

                }else {
                    Toast.makeText(ProfilActivity.this,"Plase Enter" + key+"",Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }

    private void showEditProfilDialog() {
        String options[] = {"Edit  Foto","Edit Cover", "Edit Nim", "Edit Nama", "Edit Prodi"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih aksi");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    coverorPhoto = "image";
                    showImagePictureDialogue();

                } else if (which == 1) {
                    coverorPhoto = "cover";
                    showImagePictureDialogue();

                } else if (which == 2) {
                    showNameNimProdiUpdate("nim");

                } else if (which == 3) {
                    showNameNimProdiUpdate("nama");


                }else if (which==4){
                    showNameNimProdiUpdate("prodi");
                }

            }
        });
        builder.create().show();
    }

    private void showImagePictureDialogue() {

        String options[] = {"Camera", "Galery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Gambar");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requstCamerapermission();
                    } else {
                        pickFromCamera();
                    }

                } else if (which == 1) {
                    if (!checkStoraegPermission()) {
                        requststoragepermission();
                    } else {
                        pickFroGalerry();
                    }

                }

            }
        });
        builder.create().show();

    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "temp Description");

        //put image url
        uri_image = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //startCamera
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri_image);
        startActivityForResult(camera, IMAGE_PICK_CAMERA_CODE);
    }
    private void pickFroGalerry(){
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, IMAGE_PICK_GALLERY_CODE);

    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(ProfilActivity.this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(ProfilActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private boolean checkStoraegPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requstCamerapermission(){
        requestPermissions(camerapermision,CAMERA_REQUEST_CODE);

    }
    private void requststoragepermission(){
        requestPermissions(storagepermision,STORAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    boolean cameraAcc = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAcc = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAcc && writeStorageAcc) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Plase Enable Camera and Storage Permission", Toast.LENGTH_SHORT).show();
                    }


                }


            }
            break;
            case STORAGE_REQUEST_CODE: {

                if (grantResults.length > 0) {
                    boolean writeStorageAcc = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAcc) {
                        pickFroGalerry();
                    } else {
                        Toast.makeText(this, "Plase Enable Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                uri_image = data.getData();
                uploadProfileConvertPhoto(uri_image);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                uploadProfileConvertPhoto(uri_image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileConvertPhoto(Uri urii) {
        String filePathName = strgPath +""+coverorPhoto +"_"+ user.getUid();

        StorageReference storageReference2nd = strgRef.child(filePathName);
        storageReference2nd.putFile(urii)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        if (uriTask.isSuccessful()){

                            HashMap<String,Object> results = new HashMap<>();
                            results.put(coverorPhoto, downloadUri.toString());
                            dbref.child(user.getUid()).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfilActivity.this,"Foto Update", Toast.LENGTH_SHORT).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfilActivity.this,"Error Update Foto!"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                        }else {
                            Toast.makeText(ProfilActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }

                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfilActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkStatusUser() {

        FirebaseUser user = uAuth.getCurrentUser();
        if(user!=null){

        }else {
            Intent logoutUser = new Intent(ProfilActivity.this, LoginActivity.class);
            startActivity(logoutUser);
            finish();
        }
    }
}
