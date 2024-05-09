package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";
    private final String JSON_FILE = "tests.json";
    private final ArrayList<TestClass> testClasses = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JsonFile(this, this).execute(JSON_FILE);
        //new JsonTask(this).execute(JSON_URL);
        //mountains.add(new Mountain("hej"));

        //Log.d("heeeej", "HEJ" + mountains.size());

        for (TestClass m : testClasses) {
            Log.d("ngt", "HEJ" + m.toString());
        }

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
        //  adapter.notifyDataSetChanged();
    }
}
