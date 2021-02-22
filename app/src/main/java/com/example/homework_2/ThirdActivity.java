package com.example.homework_2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {

    private TextView textView_results;
    private RecyclerView recyclerView;
    private ArrayList<Beer> beers;
    private ArrayList<Beer> beers_search;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        textView_results = findViewById(R.id.textView_results);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        beers = new ArrayList<>();
        beers_search = new ArrayList<>();

        Intent intent = getIntent();

        ArrayList<String> test = intent.getStringArrayListExtra("name");
        ArrayList<String> test2 = intent.getStringArrayListExtra("description");
        ArrayList<String> test3 = intent.getStringArrayListExtra("image_url");

        Drawable d_1;
        Drawable d_2;

        try {
            InputStream ims = getAssets().open("greenlightsaber.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            d_1 = d;
        }catch (IOException ex) {
            return;
        }
        try {
            InputStream ims = getAssets().open("redlightsaber.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            d_2 = d;
        }catch (IOException ex) {
            return;
        }

        if(test != null) {
            for (int i = 0; i < test.size(); i++) {
                Beer beer = new Beer(test.get(i),
                        test2.get(i),
                        test3.get(i),
                        d_1,
                        d_2);

                beers.add(beer);
                beers_search.add(beer);
            }
        }


        textView_results.setText("We found " + test.size() + " results");
        textView_results.setTextSize(18);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query_1) {
                if(!query_1.isEmpty()){
                    query_1 = query_1.toLowerCase();
                    beers_search.clear();
                    for(int i = 0; i < beers.size(); i ++){
                        if(beers.get(i).getName().toLowerCase().contains(query_1)){
                            beers_search.add(beers.get(i));
                        }
                        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    }
                    textView_results = findViewById(R.id.textView_results);
                    textView_results.setText("We found " + beers_search.size() + " results");
                }else{
                    beers_search.clear();
                    beers_search.addAll(beers);
                    textView_results.findViewById(R.id.textView_results);
                    textView_results.setText("We found " + beers_search.size() + " results");
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                }
                return true;
            }
        });

        BeerAdapter adapter = new BeerAdapter(beers_search);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }


}
