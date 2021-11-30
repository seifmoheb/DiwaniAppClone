package com.example.diwaniclone;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(SavedPost post);

    @Query("DELETE FROM bookmarked_posts WHERE id = :id")
    void delete(Integer id);

    @Query("SELECT * FROM bookmarked_posts")
    LiveData<List<SavedPost>> getAllPosts();

}