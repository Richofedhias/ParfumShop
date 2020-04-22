package com.javabrother.parfumshop.post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.javabrother.parfumshop.R;

import java.util.HashMap;

public class NewPostActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    EditText title, desc;
    ImageView images;
    Button btn_post;

    ImageButton btn_images;

    ProgressDialog pd;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;


    String[] cameraPermissions;
    String[] storagePermissions;

    //User Info
    String nama, email, uid, dp;

    //benar
    Uri image_rui = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        title = findViewById(R.id.titlePost);
        desc = findViewById(R.id.DescPost);
        images = findViewById(R.id.imagePost);
        btn_post = findViewById(R.id.btn_post);
        btn_images = findViewById(R.id.btn_Images);

//        getSupportActionBar().setTitle("Tambahkan Posting");
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        pd = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

//        getSupportActionBar().setSubtitle(email);

        reference = FirebaseDatabase.getInstance().getReference("User");
        Query query = reference.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    nama = "" + ds.child("nama").getValue();
                    email = "" + ds.child("email").getValue();
                    dp = "" + ds.child("image").getValue();
                    //Sampe sini
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //benar
        btn_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });

        //benar
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTitle = title.getText().toString().trim();
                String mDesc = desc.getText().toString().trim();

                if (TextUtils.isEmpty(mTitle)) {
                    Toast.makeText(NewPostActivity.this, "Masukkan Judul", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mDesc)) {
                    Toast.makeText(NewPostActivity.this, "Masukan Deskripsi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (image_rui == null) {
                    uploadData(mTitle, mDesc, "noImage");
                } else {
                    uploadData(mTitle, mDesc, String.valueOf(image_rui));
                }

                startActivity(new Intent(NewPostActivity.this, PostActivity.class));
            }
        });
    }

    private void uploadData(final String mTitle, final String mDesc, String uri) {
        pd.setMessage("Mengirim Posting");
        pd.show();

        final String timeStamp = String.valueOf(System.currentTimeMillis());

        String filepath = "Posts/" + "post_" + timeStamp;

        if (!uri.equals("noImage")) {
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(filepath);
            ref.putFile(Uri.parse(uri))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());

                            String downloadUri = uriTask.getResult().toString();

                            if (uriTask.isSuccessful()) {

                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("uid", uid);
                                hashMap.put("uName", nama);
                                hashMap.put("uEmail", email);
                                hashMap.put("uDp", dp);
                                hashMap.put("pId", timeStamp);
                                hashMap.put("mTitle", mTitle);
                                hashMap.put("mDesc", mDesc);
                                hashMap.put("pImage", downloadUri);
                                hashMap.put("pTime", timeStamp);
                                hashMap.put("pLikes", "0");
                                hashMap.put("pComments", "0");

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post");

                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pd.dismiss();
                                                Toast.makeText(NewPostActivity.this, "Posting Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

                                                title.setText("");
                                                desc.setText("");
                                                images.setImageURI(null);
                                                image_rui = null;
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(NewPostActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(NewPostActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {

            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("uid", uid);
            hashMap.put("uName", nama);
            hashMap.put("uEmail", email);
            hashMap.put("uDp", dp);
            hashMap.put("pId", timeStamp);
            hashMap.put("mTitle", mTitle);
            hashMap.put("mDesc", mDesc);
            hashMap.put("pImage", "noImage");
            hashMap.put("pTime", timeStamp);
            hashMap.put("pLikes", "0");
            hashMap.put("pComments", "0");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post");

            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(NewPostActivity.this, "Posting Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewPostActivity.this, PostActivity.class);
                            startActivity(intent);
                            title.setText("");
                            desc.setText("");
                            images.setImageURI(null);
                            image_rui = null;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(NewPostActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    //benar
    private void showImagePickDialog() {
        String[] options = {"Kamera", "Galeri"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Gambar Dari");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {

                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                }

                if (i == 1) {

                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }

                }
            }
        });

        builder.create().show();
    }

    //benar
    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    //benar
    private void pickFromCamera() {

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Temp Desc");
        image_rui = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    //benar
    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    //benar
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    //benar
    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    //benar
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }


    //benar
    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
    }

    //benar
    @Override
    protected void onResume() {
        super.onResume();
        checkUserStatus();
    }

    //benar
    public void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();

        } else {
            startActivity(new Intent(NewPostActivity.this, PostActivity.class));
            finish();
        }
    }

    //benar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }



    //benar
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Kamera dan Penyimpanan membutuhkan izin", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Izin penyimpanan yang diperlukan", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {

                image_rui = data.getData();

                images.setImageURI(image_rui);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {

                images.setImageURI(image_rui);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
