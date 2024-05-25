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

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    public void showInternalWebPage(){
        findViewById(R.id.minwebviewsID);
        myWebView = findViewById(R.id.minwebviewsID);
        myWebView.setWebViewClient(new WebViewClient()); // Do not open in Chrome!
        myWebView.loadUrl("///android_asset/internal.html");
    }

    WebView myWebView;
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=a23fanro";
    private final String JSON_FILE = "tests.json";
    private final ArrayList<TestClass> testClasses = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //new JsonFile(this, this).execute(JSON_FILE);
        new JsonTask(this).execute(JSON_URL);
        //testClasses.add(new TestClass("hej"));

        //Log.d("heeeej", "HEJ" + testClasses.size());

        //for (TestClass m : testClasses) {
            //Log.d("ngt", "HEJ" + m.toString());
        //}

        adapter = new RecyclerViewAdapter(this, testClasses, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(TestClass item) {
                Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView view = findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_internal_web) {
            Log.d("==>","Will display internal web page");

            showInternalWebPage();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onPostExecute(String json) {

        Log.d("MainActivity", "" + json);

        Gson gson = new Gson();

        Type type = new TypeToken<List<TestClass>>() {
        }.getType();
        ArrayList<TestClass> jsonTestClass = gson.fromJson(json, type);
        testClasses.addAll(jsonTestClass);

        //mountains = gson.fromJson(json, type);


        for (int i = 0; i < testClasses.size(); i++) {
            Log.d("HEJ", testClasses.get(i).getName());
        }
        adapter.notifyDataSetChanged();
    }
}
