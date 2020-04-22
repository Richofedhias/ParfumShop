package com.javabrother.parfumshop.post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.myViewHolder>  {
    Context context;
    List<ModelComment> commentList;
    String myUid,postId;

    public CommentAdapter(Context context, List<ModelComment> commentList, String myUid, String postId) {
        this.context = context;
        this.commentList = commentList;
        this.myUid = myUid;
        this.postId = postId;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_comment_item, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final String uid = commentList.get(position).getUid();
        String name = commentList.get(position).getuName();
        String email = commentList.get(position).getuEmail();
        String image = commentList.get(position).getuDp();
        final String cid = commentList.get(position).getcId();
        String comment = commentList.get(position).getComment();
        String timestamp = commentList.get(position).getTimestamp();

//        Calendar calendar = Calendar.getInstance(Locale.getDefault());
//        calendar.setTimeInMillis(Long.parseLong(timestamp));
//        String pTime = DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar).toString();

        holder.nameTv.setText(name);
        holder.commentTv.setText(comment);
        holder.timeTv.setText(timestamp);

        try {
            Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).into(holder.avatarIv);
        } catch (Exception e) {

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myUid.equals(uid)){
                    //comentku
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Hapus");
                    builder.setMessage("Yakin untuk menghapus komentar?");
                    builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteComment(cid);
                        }
                    });
                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                }else{
                    //bukan komen
                }
            }
        });
    }

    private void deleteComment(String cid) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post").child(postId);
        ref.child("Comments").child(cid).removeValue();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String pComments = ""+ dataSnapshot.child("pComments").getValue();
                final int newCommentVal = Integer.parseInt(pComments)- 1;
                ref.child("pComments").setValue(""+newCommentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarIv;
        TextView nameTv, commentTv, timeTv;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            commentTv= itemView.findViewById(R.id.commentTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }
    }
}
