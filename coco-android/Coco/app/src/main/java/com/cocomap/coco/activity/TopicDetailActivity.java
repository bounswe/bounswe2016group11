package com.cocomap.coco.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cocomap.coco.R;
import com.cocomap.coco.adapter.MyReceyclerAdapter;
import com.cocomap.coco.base.BaseActivity;
import com.cocomap.coco.pojo.CreatePostRequest;
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

public class TopicDetailActivity extends BaseActivity {
    @Inject
    Retrofit retrofit;

    EditText postEditText;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        MyReceyclerAdapter myAdapter = new MyReceyclerAdapter(this);

        postEditText = (EditText) findViewById(R.id.postEditText);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        getBaseApplication().getNetComponent().inject(this);

        ArrayList<String> posts = new ArrayList<>();

        posts.add("Ahmet");
        posts.add("Sinan");
        posts.add("Emrah");

        myAdapter.setList(posts);




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
        createPostRequest.setTopic(1);


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
