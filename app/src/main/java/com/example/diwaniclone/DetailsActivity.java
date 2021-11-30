package com.example.diwaniclone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DetailsActivity extends AppCompatActivity {

    TreeMap<Integer,Map<String,String>> items;
    SQLiteDatabase db;
    RecyclerView rc;
    int idSent;
    DetailsRecyclerAdapter detailsRecyclerAdapter;
    TextView title;
    String sentTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        items = new TreeMap<>();
        rc = findViewById(R.id.recyclerView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        title = findViewById(R.id.title);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                idSent = 0;
                sentTitle = "";

            } else {
                idSent = extras.getInt("id");
                sentTitle = extras.getString("title");

            }
        } else {
            idSent = (Integer) savedInstanceState.getSerializable("id");
            sentTitle = (String) savedInstanceState.getSerializable("name");

        }
        title.setText(sentTitle);
        DatabaseHelper myDbHelper=new DatabaseHelper(this);
        try {
            myDbHelper.createDataBase();
        }catch(Exception e)
        {}
        myDbHelper.openDataBase();
        db = myDbHelper.getReadableDatabase();
        Cursor c;
        int startIndex = idSent *100;

        int endIndex = idSent *100;
        endIndex+=100;
        c= db.rawQuery("select name,subtitle,id from books where id > '"+startIndex+"' and id < '"+endIndex+"'", null);
        c.moveToFirst();

        while(!c.isAfterLast()) {
            HashMap <String,String> temp = new HashMap();
            temp.put(c.getString(c.getColumnIndex("name")),c.getString(c.getColumnIndex("subtitle")));
            items.put(c.getInt(c.getColumnIndex("id")), temp);
            Log.i("Added",items.toString());
            c.moveToNext();
        }
        detailsRecyclerAdapter = new DetailsRecyclerAdapter(this,items);
        detailsRecyclerAdapter.notifyDataSetChanged();


        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(detailsRecyclerAdapter);
    }

}