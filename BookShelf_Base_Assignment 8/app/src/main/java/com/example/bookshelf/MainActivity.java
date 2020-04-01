package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {

    FragmentManager fm;

    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPane = findViewById(R.id.container2) != null;

        fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.container1, BookListFragment.newInstance(getTestBooks()))
                .commit();

        /*
        If we have two containers available, load a single instance
        of BookDetailsFragment to display all selected books
         */
        if (twoPane) {
            bookDetailsFragment = new BookDetailsFragment();
            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();
        }


        System.out.println("begin the connection ");
        printJson();


    }

    /*
    Generate an arbitrary list of "books" for testing
     */

    private ArrayList<HashMap<String, String>> getTestBooks() {
        ArrayList<HashMap<String, String>> books = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> book;
        for (int i = 0; i < 10; i++) {
            book = new HashMap<String, String>();
            book.put("title", "Book" + i);
            book.put("author", "Author" + i);
            books.add(book);
        }
        return books;
    }

    ;


    public void printJson() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://kamorris.com/lab/abp/booksearch.php?";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("connection success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("connection fail");
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void bookSelected(int index) {

        if (twoPane)
            /*
            Display selected book using previously attached fragment
             */
            bookDetailsFragment.displayBook(getTestBooks().get(index));
        else {
            /*
            Display book using new fragment
             */
            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(getTestBooks().get(index)))
                    // Transaction is reversible
                    .addToBackStack(null)
                    .commit();
        }
    }
}
