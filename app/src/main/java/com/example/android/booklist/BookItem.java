package com.example.android.booklist;

/**
 * Created by beita on 05/06/2017.
 */

public class BookItem {

    /**
     * Url for the book's thumbnail
     */
    private String mSmallThumbnailURL;

    /**
     * Url for the book's preview
     */
    private String mPreviewURL;

    /**
     * BookItem Title
     */
    private String mTitle;

    /**
     * BookItem Author
     */
    private String mAuthor;

    /**
     * Constructs a new {@link BookItem} object.
     *
     * @param smallThumbnailURL is the thumbnail which will be shown as the book's image
     * @param previewURL        is the link to the book's previewURL page
     * @param title             is the book's title
     * @param author            is the book's author
     */
    public BookItem(String smallThumbnailURL, String previewURL, String title, String author) {
        mSmallThumbnailURL = smallThumbnailURL;
        mPreviewURL = previewURL;
        mTitle = title;
        mAuthor = author;
    }

    public String getmSmallThumbnailURL() {
        return mSmallThumbnailURL;
    }

    public String getmPreviewURL() {
        return mPreviewURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

}