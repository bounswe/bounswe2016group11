package com.cocomap.coco.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cocomap.coco.adapter.GridViewAdapter;
import com.cocomap.coco.R;
import com.cocomap.coco.pojo.PostRetrieveResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GlobalViewActivity extends AppCompatActivity {

    TextView focus;
    String json;
    GridViewAdapter adapter;
    int focusedTopicId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_view);
        request(3);


        focus = (TextView) findViewById(R.id.focusTopicTextView);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        adapter = new GridViewAdapter(GlobalViewActivity.this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                request(adapter.getRelatedTopics().get(position).getTopic_to());
            }
        });
    }

    private void request(int topicId){

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("http://ec2-54-186-167-76.us-west-2.compute.amazonaws.com:8000/cocomapapp/topicRetrieve/"+topicId)
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

                json = response.body().string();
                Log.d("1", json);

                Gson gson = new Gson();


                final PostRetrieveResponse retrieved_post = gson.fromJson(json, PostRetrieveResponse.class);



                GlobalViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setRelatedTopics(retrieved_post.getRelated_to());
                        focus.setText(retrieved_post.getName());
                        focusedTopicId = retrieved_post.getId();
                    }
                });
            }
        });
    }

    public void onClickCreateTopic(View view) {

        Intent intent = new Intent(this, CreateTopicActivity.class);

        startActivity(intent);

    }

    public void onFocusClick(View view) {
        Intent intent = new Intent(GlobalViewActivity.this, TopicDetailActivity.class);
        intent.putExtra("focused_topic_id", focusedTopicId);
        startActivity(intent);

    }
}
