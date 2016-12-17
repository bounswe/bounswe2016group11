package com.cocomap.coco.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cocomap.coco.R;
import com.cocomap.coco.pojo.TagModel;
import com.cocomap.coco.pojo.TopicCreateRequest;
import com.cocomap.coco.pojo.UserModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateTopicActivity extends AppCompatActivity {
    EditText topicNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        topicNameEditText = (EditText) findViewById(R.id.topicNameEditText);
    }

    public void publishClick(View view) {
        String topicName = topicNameEditText.getText().toString();

        OkHttpClient client = new OkHttpClient();

        TopicCreateRequest topicCreateRequest = new TopicCreateRequest();
        topicCreateRequest.setName(topicName);
        topicCreateRequest.setUser(1);
        topicCreateRequest.setTags(new ArrayList<TagModel>());
        topicCreateRequest.setPosts(new ArrayList<Integer>());
        topicCreateRequest.setRelates_to(new ArrayList<Integer>());
        topicCreateRequest.setVisits(new ArrayList<Integer>());

        Gson gson = new Gson();

        // 2. Java object to JSON, and assign to a String
        String jsonInString = gson.toJson(topicCreateRequest);

        Request request = new Request.Builder()
                .url("http://ec2-54-186-167-76.us-west-2.compute.amazonaws.com:8000/cocomapapp/topicCreate")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonInString))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requestFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                requestSuccess(response);
            }
        });
    }

    private void requestSuccess(Response response) throws IOException {
        response.body().string();
    }

    private void requestFailure(IOException e) {
        e.getMessage();

    }
}
