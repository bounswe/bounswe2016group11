package com.cocomap.coco.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.cocomap.coco.R;
import com.cocomap.coco.adapter.SearchRecyclerAdapter;
import com.cocomap.coco.pojo.PostRetrieveResponse;
import com.cocomap.coco.pojo.SearchResponse;
import com.cocomap.coco.pojo.SearchTopicRequest;
import com.cocomap.coco.pojo.TagModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by Sinan on 21.12.2016.
 */
public class SearchTopicActivity extends AppCompatActivity {
    EditText searchEditText;
    RecyclerView recycler;
    private CountDownTimer countDownTimer;

    @Inject
    Retrofit retrofit;

    SearchRecyclerAdapter searchRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_topic);

        searchEditText = (EditText) findViewById(R.id.searchEditText);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        searchRecyclerAdapter = new SearchRecyclerAdapter(this);

        recycler.setAdapter(searchRecyclerAdapter);

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                OkHttpClient client = new OkHttpClient();

                Gson gson = new Gson();

                SearchTopicRequest searchRequest = new SearchTopicRequest();
                searchRequest.setQuery(searchEditText.getText().toString());
                searchRequest.setTags(new ArrayList<Integer>());

                // 2. Java object to JSON, and assign to a String
                String jsonInString = gson.toJson(searchRequest);


                Request request = new Request.Builder()
                        .url("http://ec2-54-186-167-76.us-west-2.compute.amazonaws.com:8000/cocomapapp/searchByTags")
                        .addHeader("Content-Type", "application/json")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonInString))
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        e.getMessage();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String json_string = response.body().string();
                        Log.d("1", json_string);

                        Gson gson = new Gson();


                        final SearchResponse retrieved_result = gson.fromJson(json_string, SearchResponse.class);

                        SearchTopicActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                searchRecyclerAdapter.setList(retrieved_result.getTopics());
                            }
                        });



                    }
                });
            }
        };


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countDownTimer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >2){
                    countDownTimer.start();
                }
            }
        });
    }
}
