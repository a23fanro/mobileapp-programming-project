package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private WebView myWebView;
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=a23fanro";
    private final ArrayList<Djurskalan> djurskalans = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myWebView = findViewById(R.id.minwebviewsID);
        myWebView.setVisibility(View.GONE);

        gson = new Gson();

        new JsonTask(this).execute(JSON_URL);

        adapter = new RecyclerViewAdapter(this, djurskalans, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Djurskalan item) {
                Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView view = findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }

    public void showInternalWebPage() {
        myWebView.setVisibility(View.VISIBLE);  // Show WebView when displaying internal page
        myWebView.setWebViewClient(new WebViewClient()); // Do not open in Chrome!
        myWebView.loadUrl("file:///android_asset/internal.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_internal_web) {
            Log.d("==>", "Will display internal web page");
            showInternalWebPage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onPostExecute(String json) {
        Log.d("MainActivity", "Received JSON: " + json);

        Type type = new TypeToken<List<Djurskalan>>() {}.getType();
        ArrayList<Djurskalan> jsonDjurskalans = gson.fromJson(json, type);
        djurskalans.addAll(jsonDjurskalans);

        for (Djurskalan djurskalan : djurskalans) {
            Log.d("MainActivity", "Loaded item: " + djurskalan.getName());
        }

        adapter.notifyDataSetChanged(); // Notify adapter of data changes
    }
}
