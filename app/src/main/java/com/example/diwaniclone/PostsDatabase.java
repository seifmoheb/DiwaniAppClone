package com.example.diwaniclone;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = SavedPost.class, version = 3)
public abstract class PostsDatabase extends RoomDatabase {
    private static PostsDatabase instance;
    public abstract PostDao postDao();
    public static synchronized PostsDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    PostsDatabase.class, "bookmarked_posts")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static Callback roomCallBack = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
    /*private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private PostDao postDao;

        private PopulateDbAsyncTask(PostsDatabase db){
            postDao = db.postDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postDao.insert(new SavedPost());
            return null;
        }
    }*/
}
