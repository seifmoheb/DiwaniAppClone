package com.example.diwaniclone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedPostsAdapter extends RecyclerView.Adapter<SavedPostsAdapter.SavedPostViewHolder> {

    public void setPostsList(List<SavedPost> postsList) {
        this.postsList = postsList;
        notifyDataSetChanged();
    }

    private List<SavedPost> postsList = new ArrayList<>();

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {

        this.mContext = mContext;

    }

    Context mContext;

    @NonNull
    @Override
    public SavedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SavedPostViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //holder.bookmarkedButton.setVisibility(View.VISIBLE);
        //holder.bookmarkButton.setVisibility(View.INVISIBLE);
        //holder.titleTextView.setText(postsList.get(position).getTitle());
        SQLiteDatabase db;

        DatabaseHelper myDbHelper=new DatabaseHelper(mContext);
        try {
            myDbHelper.createDataBase();
        }catch(Exception e)
        {}
        myDbHelper.openDataBase();
        db = myDbHelper.getReadableDatabase();
        Cursor c;
        c= db.rawQuery("select name,subtitle from books where id = '"+postsList.get(position).getId()+"' ", null);
        c.moveToFirst();
        String name = c.getString(c.getColumnIndex("name"));
        String title = c.getString(c.getColumnIndex("subtitle"));
            holder.name.setText(name);
            holder.title.setText(title);


        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),VersesSwipeActivity.class);
                intent.putExtra("id",postsList.get(position).getId());
                intent.putExtra("name",name);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class SavedPostViewHolder extends RecyclerView.ViewHolder {
        public TextView name,title;
        BookmarksViewModel bookmarksViewModel;
        ConstraintLayout constraintLayout;
        public SavedPostViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            title = (TextView) itemView.findViewById(R.id.title);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);}
    }
}
