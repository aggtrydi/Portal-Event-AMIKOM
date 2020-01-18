package com.pea.mobile.portaleventamikom.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.pea.mobile.portaleventamikom.R;
import com.pea.mobile.portaleventamikom.activity.LoginActivity;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference dr;

    StorageReference sr;

    String storagepath = "User_Profile_cover_image";

    ImageView imgProfil;
    TextView txtNama, txtNim, txtJurusan, txtEmail;
    Button btnNama, btnLogout;

    ProgressDialog pd;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    String camerapermision[];
    String storagepermision[];

    Uri uri_image;

    String photo;

    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        dr = db.getReference("User");
        sr = getInstance().getReference();

        camerapermision = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermision = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        txtNama = view.findViewById(R.id.txtNama);
        txtNim = view.findViewById(R.id.txtNIM);
        txtJurusan = view.findViewById(R.id.txtJurusan);
        txtEmail = view.findViewById(R.id.txtEmail);

        imgProfil = view.findViewById(R.id.imgProfil);

        btnNama = view.findViewById(R.id.btnNama);
        btnLogout = view.findViewById(R.id.btnLogout);

        pd = new ProgressDialog(getActivity());

        Query query = dr.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //cek untung pengambilan data firebase
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nama = "" + ds.child("nama").getValue();
                    String nim = "" + ds.child("nim").getValue();
                    String jurusan = "" + ds.child("jurusan").getValue();
                    String email = "" +ds.child("email").getValue();
                    String image = "" + ds.child("image").getValue();

                    txtNama.setText(nama);
                    txtNim.setText(nim);
                    txtJurusan.setText(jurusan);
                    txtEmail.setText(email);
                    try {
                        Picasso.get().load(image).into(imgProfil);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.logo_pea).into(imgProfil);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditNama();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogout();
            }
        });
        
        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfil();
            }
        });
    return view;
    }

    private void showEditProfil() {
        pd.setMessage("Mengupdate Foto Profil");
        photo = "image";
        showImagePictureDialogue();
    }

    private void UserLogout() {
            fAuth.signOut();
            checkUserStatus();
    }

    private void checkUserStatus() {
        FirebaseUser user = fAuth.getCurrentUser();
        if (user!=null){
        }else {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().finish();
        }
    }

    private void showEditNama() {
        pd.setMessage("Mengupdate Nama");
        showNamaUpdate("nama");
    }

    private void showNamaUpdate(final String nama) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update " + nama);
        LinearLayout linearLayout= new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Enter " + nama);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String,Object> results = new HashMap<>();
                    results.put(nama, value);
                    dr.child(user.getUid()).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(getContext(),"Updated...",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            pd.dismiss();
                            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"Plase Enter Nama",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.dismiss();
            }
        });
        builder.create().show();
    }

    private boolean checkStoraegPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requststoragepermission() {
        requestPermissions(storagepermision,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requstCamerapermission() {
        requestPermissions(camerapermision,CAMERA_REQUEST_CODE);
    }

    private void showImagePictureDialogue() {
        String options[] = {"Camera", "Galery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(), "Plase Enable Camera and Storage Permission", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Plase Enable Storage Permission", Toast.LENGTH_SHORT).show();
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
        pd.show();
        String filePathName = storagepath +""+photo +"_"+ user.getUid();
        StorageReference storageReference2nd = sr.child(filePathName);
        storageReference2nd.putFile(urii)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        if (uriTask.isSuccessful()){
                            HashMap<String,Object> results = new HashMap<>();
                            results.put(photo, downloadUri.toString());
                            dr.child(user.getUid()).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(getContext(),"Foto Update", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getContext(),"Error Update Foto!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            pd.dismiss();
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                        }
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickFroGalerry() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "temp Description");

        uri_image = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri_image);
        startActivityForResult(camera, IMAGE_PICK_CAMERA_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnLogout) {
            fAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}
