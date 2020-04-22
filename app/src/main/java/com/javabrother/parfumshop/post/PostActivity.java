package com.javabrother.parfumshop.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javabrother.parfumshop.R;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    RecyclerView rV_list;
    PostAdapter adapter;
    List<PostList> list;
    FloatingActionButton fab1;
    ProgressDialog pg;
    private Boolean a = false;
    FirebaseAuth firebaseAuth;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        firebaseAuth = FirebaseAuth.getInstance();
        rV_list = findViewById(R.id.rV_QnA);
        rV_list.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        list = new ArrayList<>();
        rV_list.setLayoutManager(lm);
        pg = new ProgressDialog(this);
        pg.setMessage("Tunggu");
        loadPosts();
        floatingbutton();

//        adapter = new QnAAdapter(list);
//        rV_list.setAdapter(adapter);
        pg.show();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void floatingbutton() {
        fab1 = findViewById(R.id.fabpost);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, NewPostActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loadPosts() {
        final View emptyView = findViewById(R.id.empty_view);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PostList model = ds.getValue(PostList.class);
                    list.add(model);
                    adapter = new PostAdapter(PostActivity.this, list, emptyView);
                    rV_list.setAdapter(adapter);
                    if (model == null) {
                        pg.show();
                        adapter.updateEmptyView();
                    } else {
                        pg.dismiss();
                        adapter.updateEmptyView();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void searchPost(final String searchQuery) {
        final View emptyView = findViewById(R.id.empty_view);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PostList model = ds.getValue(PostList.class);
                    if (model.getmTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                            model.getmDesc().toLowerCase().contains(searchQuery.toLowerCase())) {
                        list.add(model);
                    }
                    adapter = new PostAdapter(PostActivity.this, list, emptyView);
                    rV_list.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
