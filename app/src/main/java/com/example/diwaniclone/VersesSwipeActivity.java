package com.example.diwaniclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class VersesSwipeActivity extends AppCompatActivity {

    TreeMap<Integer, ArrayList<String>> items;
    SQLiteDatabase db;
    RecyclerView rc;
    int idSent;
    String nameSent;
    TextView title;
    FloatingActionButton menu;
    SwipeViewRecyclerAdapter swipeViewRecyclerAdapter;
    BookmarksViewModel bookmarksViewModel;
    boolean checkIfExists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verses_swipe);
        title = findViewById(R.id.title);
        menu = findViewById(R.id.menu);
        items = new TreeMap<>();
        rc = findViewById(R.id.recyclerView);
        bookmarksViewModel =
                ViewModelProviders.of( VersesSwipeActivity.this).get(BookmarksViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                idSent = 0;
                nameSent = "";
            } else {
                idSent = extras.getInt("id");
                nameSent = extras.getString("name");

            }
        } else {
            idSent = (Integer) savedInstanceState.getSerializable("id");
            nameSent = (String) savedInstanceState.getSerializable("name");
        }
        Log.i("IdSent",String.valueOf(idSent));
        DatabaseHelper myDbHelper=new DatabaseHelper(this);
        try {
            myDbHelper.createDataBase();
        }catch(Exception e)
        {}
        myDbHelper.openDataBase();
        db = myDbHelper.getReadableDatabase();
        Cursor c;
        title.setText(nameSent);
        c= db.rawQuery("select verse_text_ar,book_id from verses where book_id = '"+idSent+"'", null);
        c.moveToFirst();
        ArrayList<String> temp = new ArrayList<>();
        Integer columnIndex = c.getInt(c.getColumnIndex("book_id"));
        while(!c.isAfterLast()) {

            temp.add(c.getString(c.getColumnIndex("verse_text_ar")));

            c.moveToNext();
        }
        items.put(columnIndex, temp);
        Log.i("ItemsTotal",items.toString());
        swipeViewRecyclerAdapter = new SwipeViewRecyclerAdapter(this,items,idSent);
        swipeViewRecyclerAdapter.notifyDataSetChanged();


        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(swipeViewRecyclerAdapter);
        menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                final PopupMenu menuItems = new PopupMenu(VersesSwipeActivity.this, v);

              //  menuItems.getMenu().add(1, 1, 1, "Play");
                menuItems.getMenu().add(1, 2, 1, "Add to Playlist");
                menuItems.getMenu().add(1, 3, 2, "Favourite");
                List<SavedPost> savedPosts = bookmarksViewModel.getAllPosts().getValue();

                bookmarksViewModel.getAllPosts().observe(VersesSwipeActivity.this, new Observer<List<SavedPost>>() {


                    @Override
                    public void onChanged(List<SavedPost> savedPosts) {
                        for (int i = 0; i < savedPosts.size(); i++) {
                            if (savedPosts.get(i).getId().equals(idSent)) {
                                menuItems.getMenu().removeItem(3);
                                menuItems.getMenu().add(1, 4, 2, "Remove from favourites");
                                break;
                            }
                            else{
                            }
                        }                    }
                });



                menuItems.show();
                menuItems.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == 1) {

                            return true;
                        }
                        if (menuItem.getItemId() == 2) {

                            return true;
                        }
                        if (menuItem.getItemId() == 3) {

                            favourite();
                            return true;
                        }
                        if(menuItem.getItemId() == 4){
                            removeFromFavourites();
                            return true;
                        } else
                            return false;
                    }
                });

            }
        });

    }





    private void removeFromFavourites() {
        bookmarksViewModel.delete(idSent);
    }

    private void favourite() {
        bookmarksViewModel.insert(idSent);
    }

    private void addToPlaylist() {

    }

    private void play() {
    }
}