package com.javabrother.parfumshop.post;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.javabrother.parfumshop.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.myViewHolder> {
    private Context mContext;
    private List<PostList> PostList;
    private View mEmptyView;

    String myUid;

    private DatabaseReference likesRef;
    private DatabaseReference postRef;

    boolean mProccesLike = false;

    public PostAdapter(Context mContext, List<PostList> postlist, View mEmptyView) {
        this.mContext = mContext;
        PostList = postlist;
        this.mEmptyView = mEmptyView;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        likesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        postRef = FirebaseDatabase.getInstance().getReference().child("Post");
    }

    public void updateEmptyView() {
        if (PostList.size() == 0)
            mEmptyView.setVisibility(View.VISIBLE);
        else
            mEmptyView.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_post_item, parent, false);
        return new PostAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        final String uid = PostList.get(position).getUid();
        String uEmail = PostList.get(position).getuEmail();
        String uName = PostList.get(position).getuName();
        String uPict = PostList.get(position).getuDp();
        final String pId = PostList.get(position).getpId();
        final String pTitle = PostList.get(position).getmTitle();
        final String pDesc = PostList.get(position).getmDesc();
        final String pImage = PostList.get(position).getpImage();
        String pTimeStamp = PostList.get(position).getpTime();
        String pLikes = PostList.get(position).getpLikes();
        String pComments = PostList.get(position).getpComments();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        final String pTime = DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar).toString();

        holder.uNameTv.setText(uName);
        holder.pTimeTv.setText(pTime);
        holder.pTitleTv.setText(pTitle);
        holder.pDescrTv.setText(pDesc);
        holder.pLikeTv.setText(pLikes + " Suka");
//        holder.pCommentTv.setText(pComments + " Komentar");

        setLikes(holder,pId);

        try {
            Picasso.get().load(uPict).into(holder.uPictIv);
        } catch (Exception e) {

        }

        if (pImage.equals("noImage")) {
            holder.pImageIv.setVisibility(View.GONE);
        } else {
            holder.pImageIv.setVisibility(View.VISIBLE);
            try {
                Picasso.get().load(pImage).into(holder.pImageIv);
            } catch (Exception e) {

            }
        }


        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showMoreOptions(holder.moreBtn, uid, myUid, pId, pImage);
            }
        });

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int pLikes = Integer.parseInt(PostList.get(position).getpLikes());
                mProccesLike = true;

                final String postIde = PostList.get(position).getpId();
                likesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mProccesLike){
                            if (dataSnapshot.child(postIde).hasChild(myUid)){

                                postRef.child(postIde).child("pLikes").setValue(""+(pLikes-1));
                                likesRef.child(postIde).child(myUid).removeValue();
                                mProccesLike = false;
                            }
                            else{
                                postRef.child(postIde).child("pLikes").setValue(""+(pLikes+1));
                                likesRef.child(postIde).child(myUid).setValue("Liked");
                                mProccesLike = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void setLikes(final myViewHolder holder, final String postKey) {
        likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(postKey).hasChild(myUid)){

                    holder.likeBtn.setImageResource(R.drawable.ic_like);
                    //setText
                }
                else{
                    holder.likeBtn.setImageResource(R.drawable.ic_like);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showMoreOptions(ImageButton moreBtn, String uid, String myUid, final String pId, final String pImage) {

        PopupMenu popupMenu = new PopupMenu(mContext, moreBtn, Gravity.END);

        if (uid.equals(myUid)) {

            popupMenu.getMenu().add(Menu.NONE, 0, 0, "Menghapus");

        }

        popupMenu.show();
    }

    private void beginDelete(String pId, String pImage) {

        if (pImage.equals("noImage")) {

            deleteWithoutImage(pId);
        } else {
            deleteWithImage(pId, pImage);
        }
    }

    private void deleteWithImage(final String pId, String pImage) {

        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("Menghapus...");

        StorageReference pickRef = FirebaseStorage.getInstance().getReferenceFromUrl(pImage);
        pickRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Query fquery = FirebaseDatabase.getInstance().getReference("Post").orderByChild("pId").equalTo(pId);
                        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(mContext, "Menghapus Berhasil", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteWithoutImage(String pId) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("Menghapus...");

        Query fquery = FirebaseDatabase.getInstance().getReference("Post").orderByChild("pId").equalTo(pId);
        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
                Toast.makeText(mContext, "Menghapus Berhasil", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView uNameTv, pTimeTv, pTitleTv, pDescrTv, pLikeTv, pCommentTv;
        ImageView uPictIv, pImageIv;
        ImageButton moreBtn, likeBtn, commentBtn, shareBtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            uNameTv = itemView.findViewById(R.id.tV_userQ);
            pTimeTv = itemView.findViewById(R.id.tV_TimeQ);
            pTitleTv = itemView.findViewById(R.id.tV_TitlePost);
            pDescrTv = itemView.findViewById(R.id.tV_descriptionPost);
            pLikeTv = itemView.findViewById(R.id.tv_Like);
            pCommentTv = itemView.findViewById(R.id.tv_comment);
            pImageIv = itemView.findViewById(R.id.iV_imagePost);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            likeBtn = itemView.findViewById(R.id.btn_like);
//            commentBtn = itemView.findViewById(R.id.btn_comment);
            shareBtn = itemView.findViewById(R.id.btn_share);
        }
    }
}
