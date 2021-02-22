package com.example.homework_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {

    private EditText editText_name;
    private EditText editText_date_from;
    private EditText editText_date_to;
    private Button button_second;
    private Switch switch_point;
    private LinearLayout linearLayout_vertical;
    private LinearLayout linearLayout_horizontal;

    private TextView textView_to;
    private TextView textView_brewed;

    private List<String> list_names;
    private List<String> list_description;
    private List<String> list_url;

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText_name = findViewById(R.id.editText_name);
        editText_date_from = findViewById(R.id.editText_date_from);
        editText_date_to = findViewById(R.id.editText_date_to);
        button_second = findViewById(R.id.button_second);
        switch_point = findViewById(R.id.switch_point);
        linearLayout_horizontal = findViewById(R.id.linearlayout_horizontal);
        linearLayout_vertical = findViewById(R.id.linearlayout_vertical);


        textView_to = findViewById(R.id.textView_to);
        textView_brewed = findViewById(R.id.textView_brewed);


        button_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence dateFrom = editText_date_from.getText().toString();
                CharSequence dateTo = editText_date_to.getText().toString();

                if(TextUtils.isEmpty(dateFrom) && TextUtils.isEmpty(dateTo)) {
                    launchActivity(v);
                }

                if (dateFrom.toString().length() > 7 || dateFrom.toString().length() > 7){
                    notValid(v);
                }

                if(!TextUtils.isEmpty(dateFrom) && !TextUtils.isEmpty(dateTo) && validate(dateFrom) && validate(dateTo)){
                    String lastFourDigits = dateFrom.toString();
                    String lastFourDigits_1 = lastFourDigits.substring(dateFrom.length() - 4);
                    int number_from = Integer.parseInt(lastFourDigits_1);

                    String last_to = dateTo.toString();
                    String last_to_1 = last_to.substring(dateTo.length() - 4);
                    int number_to = Integer.parseInt(last_to_1);

                    String month_from = lastFourDigits.substring( 0, 2 );
                    int month_from_1 = Integer.parseInt(month_from);
                    String month_to = last_to.substring( 0, 2 );
                    int month_to_1  = Integer.parseInt(month_to);

                    if(month_from_1 > month_to_1){
                        System.out.println("check");
                    }else{
                        notValid(v);
                    }

                    if(number_from > number_to){
                        launchActivity(v);
                    }else{
                        notValid(v);
                    }

                } else if(!TextUtils.isEmpty(dateFrom) && TextUtils.isEmpty(dateTo)) {
                    if (validate(dateFrom)) {
                        launchActivity(v);
                    }else{
                        notValid(v);
                    }
                }else if(TextUtils.isEmpty(dateFrom) && !TextUtils.isEmpty(dateTo)){
                    if(validate(dateTo)){
                        launchActivity(v);
                    }else{
                        notValid(v);
                    }
                    }
                }
        });
    }

    private boolean validate(CharSequence date1){
        if (date1.toString().length() == 6){
            date1.toString().matches("[0-1]{1}[1-9]{1}[0-1]{1}[0-9]{3}");
            return true;
        }
        else if (date1.toString().length() == 7){
            date1.toString().matches("[0-1]{1}[1-9]{1} [/.-] [0-1]{1}[0-9]{3}");
            return true;
        }
        return false;
    }

    public void notValid(View view){
        Toast toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
        toast.show();
    }




    public void launchActivity(View v){

        String api_url = "https://api.punkapi.com/v2/beers?";

        CharSequence query = editText_name.getText().toString();
        CharSequence query_dateFrom = editText_date_from.getText().toString();
        CharSequence query_dateTo = editText_date_to.getText().toString();

        if(switch_point.isChecked()){
            api_url = api_url + "abv_gt=3.9";
        }else{
            api_url = api_url + "abv_lt=4.0";
        }

        if(!TextUtils.isEmpty(query)){
            api_url = api_url + "&beer_name=" + query;
        }

        if(!TextUtils.isEmpty(query_dateTo)) {
            api_url = api_url + "&brewed_after" + query_dateTo;
        }

        if(!TextUtils.isEmpty(query_dateFrom)){
            api_url = api_url + "&brewed_before" + query_dateFrom;
        }

        System.out.println("THIS IS THE API " + api_url);

            client.addHeader("Accept", "application/json");
            client.get(api_url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // when you get a 200 status code
                    Log.d("api response", new String(responseBody));

                    try {
                        JSONArray json = new JSONArray(new String(responseBody));
                        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

                        list_names = new ArrayList<String>();
                        list_description = new ArrayList<String>();
                        list_url = new ArrayList<String>();

                        for (int i = 0; i < json.length(); i++) {

                            JSONObject name = json.getJSONObject(i);
                            String name_1 = name.getString("name");
                            list_names.add(name_1);

                            String description = name.getString("description");
                            list_description.add(description);

                            String url = name.getString("image_url");
                            list_url.add(url);

                        }

                        intent.putStringArrayListExtra("name", (ArrayList<String>) list_names);
                        intent.putStringArrayListExtra("description", (ArrayList<String>) list_description);
                        intent.putStringArrayListExtra("image_url", (ArrayList<String>) list_url);


                        startActivity(intent);

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
