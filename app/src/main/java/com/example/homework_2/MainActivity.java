package com.example.homework_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button button_main;
    private ImageView imageView_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_main = findViewById(R.id.button_main);
        imageView_background = findViewById(R.id.imageView_background);

        AssetManager assetManager = getAssets();
        try {
            InputStream ims = assetManager.open("starkiller.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            imageView_background.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }

        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(v);
            }
        });
    }

    public void launchActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);

    }

}