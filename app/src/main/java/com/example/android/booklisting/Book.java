package com.example.android.booklisting;

import android.net.Uri;

public class Book {

    private Uri bookCover;
    private String bookTitle;
    private String authors;
    private String webBook;

    public Book(Uri coverLink, String bookTitle, String authors, String webBook) {

        this.bookCover = coverLink;
        this.bookTitle = bookTitle;
        this.authors = authors;
        this.webBook = webBook;
    }

    public Uri getBookCover() {
        return bookCover;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public String getWebBook() {
        return webBook;
    }
}
