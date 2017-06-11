package com.example.android.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class BookActivity extends AppCompatActivity {

private int SEARCH_GOOGLE_BOOK = R.string.api_book_google;
private BookAdapter adapter;
private EditText editText;
private ProgressBar progressBar;
private TextView emptyView;
private String criteriaSearch;
private ConnectivityManager cm;
private ImageView backgroundImage;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        backgroundImage = (ImageView) findViewById(R.id.search_image);
        backgroundImage.setImageResource(R.drawable.books);
final ListView bookList = (ListView) findViewById(R.id.list);
        adapter = new BookAdapter(this, new ArrayList<Book>());
        bookList.setAdapter(adapter);
        emptyView = (TextView) findViewById(R.id.empty_view);
        bookList.setEmptyView(emptyView);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getItem
        (position).getWebBook()));
        startActivity(intent);
        }
        });
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        editText = (EditText) findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        View view = getCurrentFocus();
        view.clearFocus();
        if (view != null) {
        InputMethodManager imm = (InputMethodManager) getSystemService
        (Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        backgroundImage.setVisibility(View.GONE);
        StringBuilder builder = new StringBuilder();
        String search = editText.getText().toString();
        String apiBook = getString(SEARCH_GOOGLE_BOOK);
        builder.append(apiBook).append(search);
        criteriaSearch = builder.toString();
        progressBar.setVisibility(View.VISIBLE);
        if (progressBar.getVisibility() == View.VISIBLE) {
        emptyView.setVisibility(View.GONE);
        }
         NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        BookAsyncTask asyncTask = new BookAsyncTask();
        asyncTask.execute(criteriaSearch);
        } else {

        adapter.clear();
        progressBar.setVisibility(View.GONE);
        emptyView.setText(R.string.no_internet);
        }
        }
        });

                     }

// AsyncTask
private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

    @Override
    protected List<Book> doInBackground(String... params) {
        if (params.length < 1 || params[0] == null) {
            return null;
        }
        List<Book> books = QueryUtils.getDataBook(params[0]);

        return books;
    }

    protected void onPostExecute(List<Book> data) {
        progressBar.setVisibility(View.GONE);
        emptyView.setText(R.string.matches);
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
            Toast.makeText(BookActivity.this, R.string.more_info, Toast.LENGTH_SHORT).show();
        }
    }
}
}