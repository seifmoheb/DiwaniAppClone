package com.example.diwaniclone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import com.example.diwaniclone.pojo.PostModel;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    public void setPostsList(List<PostModel> postsList) {
        for (int i = 0; i < postsList.size(); i++)
            this.postsList.add(postsList.get(i));
        notifyDataSetChanged();
    }

    private List<PostModel> postsList = new ArrayList<>();
    private LiveData<List<SavedPost>> savedPostsList = null;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    Context mContext;

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_view_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        savedPostsList = holder.bookmarksViewModel.getAllPosts();
        savedPostsList.observe((LifecycleOwner) getmContext(), new Observer<List<SavedPost>>() {
            @Override
            public void onChanged(List<SavedPost> savedPosts) {
                for (int i = 0; i < savedPosts.size(); i++) {
                    if (savedPosts.get(i).getId().equals(postsList.get(position).getId())) {
                       // holder.bookmarkButton.setVisibility(View.INVISIBLE);
                        //holder.bookmarkedButton.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
        /*

        holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Bookmarked", Toast.LENGTH_SHORT).show();
                holder.bookmarksViewModel.insert(postsList.get(position).getTitle(), postsList.get(position).getDescription(), postsList.get(position).getContent(), postsList.get(position).getUrlToImage(), postsList.get(position).getAuthor(), postsList.get(position).getPublishedAt(), postsList.get(position).getUrl());
                holder.bookmarkButton.setVisibility(View.INVISIBLE);
                holder.bookmarkedButton.setVisibility(View.VISIBLE);
            }
        });
        holder.bookmarkedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Bookmark removed", Toast.LENGTH_SHORT).show();
                holder.bookmarksViewModel.delete(postsList.get(position).getTitle());
                holder.bookmarkButton.setVisibility(View.VISIBLE);
                holder.bookmarkedButton.setVisibility(View.INVISIBLE);
            }
        });*/
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DetailsActivity.class);
                intent.putExtra("title",postsList.get(position).getTitle());
                intent.putExtra("subtitle",postsList.get(position).getSubtitle());
                 /*if(holder.bookmarkButton.getVisibility() == View.VISIBLE){
                    intent.putExtra("bookmarkStatus","not bookmarked");
                }else{
                    intent.putExtra("bookmarkStatus","bookmarked");
                }*/
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        BookmarksViewModel bookmarksViewModel;
        ConstraintLayout constraintLayout;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            bookmarksViewModel =
                    ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(BookmarksViewModel.class);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }
}
