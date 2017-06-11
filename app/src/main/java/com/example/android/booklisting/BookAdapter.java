package com.example.android.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);

            Book currentBook = getItem(position);
            ImageView cover = (ImageView) listItemView.findViewById(R.id.cover);
            Picasso.with(getContext()).load(currentBook.getBookCover()).into(cover);
            TextView title = (TextView) listItemView.findViewById(R.id.title);
            title.setText(currentBook.getBookTitle());
            TextView authors1 = (TextView) listItemView.findViewById(R.id.authors);
            authors1.setText(currentBook.getAuthors().toUpperCase());
                    }
        return listItemView;
    }
}