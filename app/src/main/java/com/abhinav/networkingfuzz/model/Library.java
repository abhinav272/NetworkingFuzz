package com.abhinav.networkingfuzz.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abhinav.sharma on 11/1/2016.
 */
public class Library {
    private String title;
    @SerializedName("items")
    private List<Book> books;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
