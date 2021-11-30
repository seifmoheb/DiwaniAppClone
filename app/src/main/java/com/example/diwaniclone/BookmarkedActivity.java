package com.example.diwaniclone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BookmarkedActivity extends AppCompatActivity {
    Map<Integer, Map<String,String>> items;
    SQLiteDatabase db;
    RecyclerView rc;
    int idSent;
    DetailsRecyclerAdapter detailsRecyclerAdapter;
    BookmarksViewModel bookmarksViewModel;
    SavedPostsAdapter savedPostsAdapter;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bookmarked);
        bookmarksViewModel = bookmarksViewModel =
                ViewModelProviders.of(this).get(BookmarksViewModel.class);
        items = new HashMap<>();
        rc = findViewById(R.id.recyclerView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        savedPostsAdapter = new SavedPostsAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rc.setLayoutManager(linearLayoutManager);

        bookmarksViewModel.getAllPosts().observe((LifecycleOwner) this, new Observer<List<SavedPost>>() {
            @Override
            public void onChanged(List<SavedPost> savedPosts) {
                savedPostsAdapter.setPostsList(savedPosts);
                savedPostsAdapter.setmContext(BookmarkedActivity.this);
            }
        });
        savedPostsAdapter.notifyDataSetChanged();
        rc.setAdapter(savedPostsAdapter);

    }
}