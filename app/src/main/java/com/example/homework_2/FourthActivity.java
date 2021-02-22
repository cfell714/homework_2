package com.example.homework_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FourthActivity extends AppCompatActivity {

    private LinearLayout linearLayout_fourth;
    private TextView textView_titleFourth;
    private TextView textView_descriptionFourth;
    private ScrollView scrollView;
    private TextView textView_abvFourth;
    private TextView textView_firstBrewedFourth;
    private TextView textView_foodPairing;
    private TextView textView_tips;
    private ImageView imageView_fourth;

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        linearLayout_fourth = findViewById(R.id.linearLayout_fourth);
        textView_titleFourth = findViewById(R.id.textView_fourthTitle);
        textView_descriptionFourth = findViewById(R.id.textView_descriptionFourth);
        textView_abvFourth = findViewById(R.id.textView_abvFourth);
        textView_firstBrewedFourth = findViewById(R.id.textView_firstBrewedFourth);
        textView_foodPairing = findViewById(R.id.textView_foodPairing);
        textView_tips = findViewById(R.id.textView_tips);
        imageView_fourth = findViewById(R.id.imageView_fourth);


        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        textView_titleFourth.setText(name);


        String api_url = "https://api.punkapi.com/v2/beers?beer_name=" + name;

        client.addHeader("Accept", "application/json");
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONArray json = new JSONArray(new String(responseBody));

                        JSONObject name = json.getJSONObject(0);

                        String description = name.getString("description");
                        textView_descriptionFourth.setText("Description: " + description);

                        String abv = name.getString("abv");
                        textView_abvFourth.setText("ABV: " + abv);

                        String first_brewed = name.getString("first_brewed");
                        textView_firstBrewedFourth.setText("First Brewed: " + first_brewed);

                        String url = name.getString("image_url");
                        Picasso.get().load(url).into(imageView_fourth);

                        String final_food = "";
                        JSONArray food = name.getJSONArray("food_pairing");
                        int k = food.length();
                        for (int i = 0; i < k; i ++) {
                            String food_pairing = food.getString(i);
                            final_food = final_food + food_pairing;
                            System.out.println("THIS IS FOOD " + food_pairing);
                        }
                        textView_foodPairing.setText("Food Pairing: " + final_food);

                        String tips = name.getString("brewers_tips");
                        textView_tips.setText("Brewer Tips: " + tips);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // when you get a 400-499 status code
                Log.e("api error", new String(responseBody));
                //  toast_stop(statusCode);
            }
        });

    }






}


