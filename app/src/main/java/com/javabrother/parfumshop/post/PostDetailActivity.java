package com.javabrother.parfumshop.post;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.javabrother.parfumshop.HomeActivity;
import com.javabrother.parfumshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {
    String myUid,myEmail,myName,myDp,postId,pLikes,hisDp,hisName, pImage;
    String hisUid;
    boolean mProcessComment = false;
    boolean mProcessLike = false;

    ImageView uPictureIv,pImageIv;
    TextView nameTv,pTimeTv,pTitleTv,pDescTv,pLikesTv, pCommentsTv;
    ImageButton morebtn;
    Button likebtn,sharebtn;

    RecyclerView rv_comment;

    List<ModelComment> commentList;
    CommentAdapter adapter;

    ProgressDialog pd;
    String pTitle;
    String pDesc;


    //add comment view
    EditText commentEt;
    ImageButton sendBtn;
    ImageView cAvatarIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");




        //init
        pImageIv = findViewById(R.id.iV_imagePost);
        nameTv = findViewById(R.id.tV_userQ);
        pTimeTv = findViewById(R.id.tV_TimeQ);
        pTitleTv = findViewById(R.id.tV_TitlePost);
        pDescTv = findViewById(R.id.tV_descriptionPost);
        pLikesTv = findViewById(R.id.tv_Like);
        pCommentsTv = findViewById(R.id.tv_comment);
        morebtn = findViewById(R.id.moreBtn);
        likebtn = findViewById(R.id.btn_like);
        sharebtn = findViewById(R.id.btn_share);
//        profileLayout = findViewById(R.id.profileLayout);
        rv_comment = findViewById(R.id.rv_comment);

        commentEt = findViewById(R.id.commentEt);
        sendBtn = findViewById(R.id.sendBtn);
        cAvatarIv = findViewById(R.id.cAvatarIV);

        loadPostInfo();

        checkUserStatus();

        loadUserInfo();

        setLikes();

        loadComments();


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
            }
        });

        likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likePost();
            }
        });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTexOnly(pTitle,pDesc);
            }
        });


        morebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showMoreOptions();
            }
        });

    }

    private void loadComments() {
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        rv_comment.setLayoutManager(lm);

        commentList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post").child(postId).child("Comments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelComment model = ds.getValue(ModelComment.class);

                    commentList.add(model);
                    adapter = new CommentAdapter(getApplicationContext(), commentList,myUid,postId);
                    rv_comment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showMoreOptions() {
        PopupMenu popupMenu = new PopupMenu(this, morebtn, Gravity.END);

        if (hisUid.equals(myUid)) {
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "Hapus");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == 0) {

                    beginDelete();
                }else if (id == 1){
                    Intent intent = new Intent(PostDetailActivity.this, NewPostActivity.class);
                    intent.putExtra("key", "editPost");
                    intent.putExtra("postId",postId);
                    startActivity(intent);
                }

                return false;
            }
        });
        popupMenu.show();
    }

    private void beginDelete() {
        if (pImage.equals("noImage")) {

            deleteWithoutImage();
        } else {
            deleteWithImage();
        }
    }

    private void deleteWithImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Menghapus");

        StorageReference pickRef = FirebaseStorage.getInstance().getReferenceFromUrl(pImage);
        pickRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Query fquery = FirebaseDatabase.getInstance().getReference("Post").orderByChild("pId").equalTo(postId);
                        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(PostDetailActivity.this, "Menghapus Sukses", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PostDetailActivity.this,PostActivity.class));
                                finish();
                                pd.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(PostDetailActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteWithoutImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Deleting...");

        Query fquery = FirebaseDatabase.getInstance().getReference("Post").orderByChild("pId").equalTo(postId);
        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
                Toast.makeText(PostDetailActivity.this, "Menghapus Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PostDetailActivity.this,PostActivity.class));
                finish();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setLikes() {
        final DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(postId).hasChild(myUid)){

                    likebtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like,0,0,0);
                    likebtn.setText("Disukai");
                }
                else{
                    likebtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like,0,0,0);
                    likebtn.setText("Suka");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void likePost() {
        mProcessLike = true;
        final DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference().child("Post");
        likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mProcessLike){
                    if (dataSnapshot.child(postId).hasChild(myUid)){

                        postRef.child(postId).child("pLikes").setValue(""+(Integer.parseInt(pLikes)-1));
                        likesRef.child(postId).child(myUid).removeValue();
                        mProcessLike = false;

                    }
                    else{
                        postRef.child(postId).child("pLikes").setValue(""+(Integer.parseInt(pLikes)+1));
                        likesRef.child(postId).child(myUid).setValue("Suka");
                        mProcessLike = false;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void postComment() {
        pd = new ProgressDialog(this);
        pd.setMessage("Komentar ditambahkan");

        String comment = commentEt.getText().toString().trim();
        if (TextUtils.isEmpty(comment)){
            Toast.makeText(this, "Kolom Komentar Kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        String timeStamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post").child(postId).child("Comments");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("cId", timeStamp);
        hashMap.put("comment", comment);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("uid", myUid);
        hashMap.put("uEmail", myEmail);
        hashMap.put("uDp", myDp);
        hashMap.put("uName", myName);

        reference.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                commentEt.setText("");
                updateCommentCount();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                    }
                });
    }

    private void updateCommentCount() {
        mProcessComment = true;
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post").child(postId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mProcessComment) {
                    String pComments = ""+ dataSnapshot.child("pComments").getValue();
                    final int newCommentVal = Integer.parseInt(pComments)+ 1;
                    ref.child("pComments").setValue(""+newCommentVal);
                    mProcessComment = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void shareTexOnly(String pTitle, String pDesc) {

        String shareBody = pTitle +"\n" + pDesc;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        this.startActivity(Intent.createChooser(intent, "Kirim Lewat"));
    }

    private void loadUserInfo() {
        Query query = FirebaseDatabase.getInstance().getReference("User");
        query.orderByChild("uid").equalTo(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    myName = ""+ds.child("nama").getValue();
                    myDp = ""+ds.child("image").getValue();

                    try {
                        Picasso.get().load(myDp).placeholder(R.drawable.ic_launcher_background).into(cAvatarIv);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.ic_launcher_background).into(cAvatarIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadPostInfo() {
        //get Post Using the id
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        Query query = reference.orderByChild("pId").equalTo(postId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    pTitle = ""+ds.child("mTitle").getValue();
                    pDesc = ""+ds.child("mDesc").getValue();
                    pLikes = ""+ds.child("pLikes").getValue();
                    String pTimeStamp = ""+ds.child("pTime").getValue();
                    pImage = ""+ds.child("pImage").getValue();
                    hisDp =""+ds.child("uDp").getValue();
                    hisUid = ""+ds.child("uid").getValue();
                    hisName = ""+ds.child("uName").getValue();
                    String commentCount = ""+ds.child("pComments").getValue();

                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
                    final String pTime = DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar).toString();

                    if (!hisUid.equals(myUid)){
                        morebtn.setVisibility(View.GONE);
                    }

                    //set data
                    pTitleTv.setText(pTime);
                    pDescTv.setText(pDesc);
                    pLikesTv.setText(pLikes + " Suka");
                    pTimeTv.setText(pTime);
                    pCommentsTv.setText(commentCount + " Komentar");

                    nameTv.setText(hisName);

                    if (pImage.equals("noImage")) {
                        pImageIv.setVisibility(View.GONE);
                    } else {
                        pImageIv.setVisibility(View.VISIBLE);
                        try {
                            Picasso.get().load(pImage).into(pImageIv);
                        } catch (Exception e) {

                        }
                    }

                    try{
                        Picasso.get().load(hisDp).placeholder(R.mipmap.ic_launcher).into(uPictureIv);
                    }catch (Exception e){
                        Picasso.get().load(R.mipmap.ic_launcher).into(uPictureIv);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkUserStatus(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            //user is login
            myEmail = user.getEmail();
            myUid = user.getUid();

        }
        else {
            //user not login
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post,menu);
        menu.findItem(R.id.action_post).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Logout

        return super.onOptionsItemSelected(item);
    }
}
