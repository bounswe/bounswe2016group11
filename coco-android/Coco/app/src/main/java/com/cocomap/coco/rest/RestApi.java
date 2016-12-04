package com.cocomap.coco.rest;

import com.cocomap.coco.pojo.CreatePostRequest;
import com.cocomap.coco.pojo.CreatePostResponse;
import com.cocomap.coco.pojo.IpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Emrah on 20/11/2016.
 */
public interface RestApi {

    @Headers({"Content-Type: application/json", "Media-Type: application/json"})
    @POST("/postCreate")
    Call<CreatePostResponse> createPost(@Body CreatePostRequest request);
}
