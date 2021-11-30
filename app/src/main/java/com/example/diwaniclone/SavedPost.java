package com.example.diwaniclone;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarked_posts")
public class SavedPost {

    @PrimaryKey(autoGenerate = true)


    private Integer id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SavedPost(Integer id) {

        this.id = id;
    }


}
