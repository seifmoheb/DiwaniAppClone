package com.example.diwaniclone;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.List;

public class BookmarksViewModel extends AndroidViewModel {

    private PostRepository repository;
    private LiveData<List<SavedPost>> allPosts;

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
        repository = new PostRepository(application);
        allPosts = repository.getAllPosts();
    }

    public void insert(Integer id){
        SavedPost post
                = new SavedPost(id);
        repository.insert(post);
    }

    public void delete(Integer id){
        repository.delete(id);
    }

    public LiveData<List<SavedPost>> getAllPosts(){
        return allPosts;
    }

}