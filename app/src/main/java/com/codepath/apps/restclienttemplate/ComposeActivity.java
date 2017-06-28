package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class ComposeActivity extends AppCompatActivity {
    Button buttonTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient();
        setContentView(R.layout.activity_compose);

        buttonTweet = (Button) findViewById(R.id.tvPublish);
        buttonTweet.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    onSubmit(v);
            }
        });


    }
    public int REQUEST_CODE = 20;
    public int RESULT_CODE = 50;
    public void onSubmit(View v) {
        EditText etName = (EditText) findViewById(R.id.tvCompose);
        client.sendTweet(etName.getText().toString(), new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Tweet tweet = null;
                            tweet = Tweet.fromJSON(response);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_CODE, intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                    }
                    }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                log.i("failure", "message" + errorResponse.toString());
            }
                });
        // Prepare data intent




    }
}
