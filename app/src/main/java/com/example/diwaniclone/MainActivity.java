package com.example.diwaniclone;

import static android.view.View.VISIBLE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<String> mDataset;
    RecyclerView recyclerView;
    MainRecyclerAdapter mainRecyclerAdapter;
    Animation rotateOpen,rotateClose,fromBottom,toBottom;
    FloatingActionButton compass,star,search,info,manage,bookmark;
    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        compass = findViewById(R.id.compass);
        star = findViewById(R.id.star);
        search = findViewById(R.id.search);
        info = findViewById(R.id.info);
        manage = findViewById(R.id.manage);
        bookmark = findViewById(R.id.bookmark);
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation(clicked);
                setVisibility(clicked);
                if(!clicked) {


                    clicked = true;
                }
                else{

                    clicked = false;
                }
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookmarkedActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDataset = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        mainRecyclerAdapter = new MainRecyclerAdapter(this,mDataset);
        DatabaseHelper myDbHelper=new DatabaseHelper(this);
        try {
            myDbHelper.createDataBase();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        myDbHelper.openDataBase();
        db = myDbHelper.getReadableDatabase();
        Cursor c;
        c= db.rawQuery("select name from books where id > 0 and id < 101", null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            mDataset.add(c.getString(c.getColumnIndex("name"))); //add the item
            c.moveToNext();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerAdapter.notifyDataSetChanged();

       // recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
    }

    private void setVisibility(boolean clicked) {
        if(clicked) {
            star.setVisibility(View.INVISIBLE);
            search.setVisibility(View.INVISIBLE);
            info.setVisibility(View.INVISIBLE);
            manage.setVisibility(View.INVISIBLE);
            bookmark.setVisibility(View.INVISIBLE);

        }else{
            star.setVisibility(VISIBLE);
            search.setVisibility(VISIBLE);
            info.setVisibility(VISIBLE);
            manage.setVisibility(VISIBLE);
            bookmark.setVisibility(VISIBLE);

        }
    }

    private void startAnimation(boolean clicked) {
        if(!clicked) {
            star.startAnimation(fromBottom);
            search.startAnimation(fromBottom);
            info.startAnimation(fromBottom);
            manage.startAnimation(fromBottom);
            bookmark.startAnimation(fromBottom);
            compass.startAnimation(rotateOpen);
        }
        else{
            star.startAnimation(toBottom);
            search.startAnimation(toBottom);
            info.startAnimation(toBottom);
            manage.startAnimation(toBottom);
            bookmark.startAnimation(toBottom);
            compass.startAnimation(rotateClose);
        }
    }
}