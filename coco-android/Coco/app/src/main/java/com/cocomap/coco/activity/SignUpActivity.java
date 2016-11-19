package com.cocomap.coco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cocomap.coco.R;
import com.cocomap.coco.base.BaseActivity;
import com.cocomap.coco.pojo.IpResponse;
import com.cocomap.coco.rest.RestApi;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Sinan on 15.11.2016.
 */
public class SignUpActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;

    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = (EditText) findViewById(R.id.etEmail);

        getBaseApplication().getNetComponent().inject(this);

        Call<IpResponse> ip = retrofit.create(RestApi.class).getIp();

        ip.enqueue(new Callback<IpResponse>() {
            @Override
            public void onResponse(Call<IpResponse> call, Response<IpResponse> response) {
                emailEditText.setText(response.body().getIp());
            }

            @Override
            public void onFailure(Call<IpResponse> call, Throwable t) {
                emailEditText.setText(t.getMessage().toString());
            }
        });


    }

    public void onClickSignUp(View v) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}
