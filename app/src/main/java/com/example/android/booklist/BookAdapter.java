package com.example.android.booklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * An {@link BookAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link BookItem} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter<BookItem> {

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context   of the app
     * @param bookItems is the list of bookItems, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<BookItem> bookItems) {
        super(context, 0, bookItems);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Find the book at the given position in the list of books
        BookItem currentBookItem = getItem(position);

        /// Find the ImageView in the book_list_item.xml layout with the ID title.
        ImageView thumbnailImageView = (ImageView) listItemView.findViewById(R.id.book_thumbnail);
        Picasso.with(getContext()).load(currentBookItem.getmSmallThumbnailURL()).into(thumbnailImageView);

        // Find the TextView in the book_list_item.xml layout with the ID book_title.
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.booktitle);
        // Get the Title from the currentBookItem object and set this text on the TextView.
        titleTextView.setText(currentBookItem.getmTitle());

        // Find the TextView in the book_list_item.xml layout with the ID book_author.
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        // Get the Author from the currentBookItem object and set this text on the TextView.
        authorTextView.setText(currentBookItem.getmAuthor());

        return listItemView;
    }

}