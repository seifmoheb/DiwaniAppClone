package com.example.diwaniclone;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PostRepository {
    private PostDao postDao;
    private static LiveData<List<SavedPost>> allPosts;

    public PostRepository(Application application){
        PostsDatabase database = PostsDatabase.getInstance(application);
        postDao = database.postDao();
        allPosts = postDao.getAllPosts();
    }

    public static List<SavedPost>  get(){
        return allPosts.getValue();
    }
    public void insert(SavedPost post){
        new InsertPostAsyncTask(postDao).execute(post);
    }

    public void delete(Integer id){
        new DeletePostAsyncTask(postDao).execute(id);
    }

    public LiveData<List<SavedPost>> getAllPosts(){
        return allPosts;
    }

    private static class InsertPostAsyncTask extends AsyncTask<SavedPost, Void, Void>{
        private  PostDao postDao;

        private InsertPostAsyncTask(PostDao postDao){
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(SavedPost... savedPosts) {
            postDao.insert(savedPosts[0]);
            return null;
        }
    }

    private static class DeletePostAsyncTask extends AsyncTask<Integer, Void, Void>{
        private  PostDao postDao;

        private DeletePostAsyncTask(PostDao postDao){
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(Integer... strings) {
            postDao.delete(strings[0]);
            return null;
        }
    }

}
