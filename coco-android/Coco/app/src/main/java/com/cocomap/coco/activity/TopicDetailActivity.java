package com.cocomap.coco.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cocomap.coco.R;
import com.cocomap.coco.adapter.MyReceyclerAdapter;
import com.cocomap.coco.base.BaseActivity;
import com.cocomap.coco.pojo.CreatePostRequest;
import com.cocomap.coco.pojo.PostModel;
import com.cocomap.coco.pojo.PostRetrieveResponse;
import com.cocomap.coco.pojo.TagModel;
import com.google.gson.Gson;

import org.json.JSONObject;

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

public class TopicDetailActivity extends BaseActivity {
    @Inject
    Retrofit retrofit;

    EditText postEditText;

    RecyclerView recyclerView;

    String json_string;

    int topic_id;

    ArrayList<PostModel> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        final MyReceyclerAdapter myAdapter = new MyReceyclerAdapter(this);

        postEditText = (EditText) findViewById(R.id.postEditText);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        getBaseApplication().getNetComponent().inject(this);

        posts = new ArrayList<>();



        topic_id = getIntent().getIntExtra("focused_topic_id", -1);

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("http://ec2-54-186-167-76.us-west-2.compute.amazonaws.com:8000/cocomapapp/topicRetrieve/"+topic_id) // TOPIC ID DEĞİŞECEK
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                json_string = response.body().string();
                Log.d("1", json_string);

                Gson gson = new Gson();


                final PostRetrieveResponse retrieved_post = gson.fromJson(json_string, PostRetrieveResponse.class);

                TopicDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.setList(retrieved_post.getPosts());
                        getSupportActionBar().setTitle(retrieved_post.getName());
                    }
                });



            }
        });








    }

    public void onClickPost(View v) throws IOException {

        OkHttpClient client = new OkHttpClient();

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent(postEditText.getText().toString());
        createPostRequest.setUser(1);
        createPostRequest.setNegative_reaction_count(1);
        createPostRequest.setPositive_reaction_count(1);
        ArrayList<TagModel> tags = new ArrayList<>();
        createPostRequest.setTags(tags);
        createPostRequest.setTopic(topic_id);


        Gson gson = new Gson();

        // 2. Java object to JSON, and assign to a String
        String jsonInString = gson.toJson(createPostRequest);

        Request request = new Request.Builder()
                .url("http://ec2-54-186-167-76.us-west-2.compute.amazonaws.com:8000/cocomapapp/postCreate")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonInString))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        postEditText.setText("");
    }
}
