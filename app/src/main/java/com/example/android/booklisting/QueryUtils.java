package com.example.android.booklisting;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {


    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {
    }

    public static List<Book> getDataBook(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<Book> books = extractBookInfo(jsonResponse);

        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Book> extractBookInfo(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        List<Book> bookList = new ArrayList<>();


        try {
            JSONObject list = new JSONObject(bookJSON);
            JSONArray listArray = list.getJSONArray("items");

            for (int i = 0; i < listArray.length(); i++) {

                JSONObject bookItem = listArray.getJSONObject(i);
                JSONObject bookAttributes = bookItem.getJSONObject("volumeInfo");
                JSONObject bookCover = bookAttributes.getJSONObject("imageLinks");
                String uriLinkBook = bookCover.getString("thumbnail");
                Uri imgUri = Uri.parse(uriLinkBook);
                String title = bookAttributes.getString("title");
                JSONArray authors = bookAttributes.getJSONArray("authors");
                String authors1 = "";
                for (int x = 0; x < authors.length(); x++) {
                    if (authors.getString(x).trim().equals("")) {
                    } else {
                        authors1 = authors1.concat(authors.getString(x) + "\n");
                    }
                }
                String web = bookAttributes.getString("canonicalVolumeLink");

                if (title.equals("") || authors1.equals("")) {
                } else {
                    Book currentBook = new Book(imgUri, title, authors1, web);
                    bookList.add(currentBook);
                }
            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
        return bookList;
    }

}