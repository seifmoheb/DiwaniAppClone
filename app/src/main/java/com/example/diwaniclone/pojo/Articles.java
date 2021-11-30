package com.example.diwaniclone.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Articles {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<PostModel> getArticles() {
        return articles;
    }

    public void setArticles(List<PostModel> articles) {
        this.articles = articles;
    }

    @SerializedName("articles")
    @Expose
    private List<PostModel> articles = new ArrayList<>();


}
