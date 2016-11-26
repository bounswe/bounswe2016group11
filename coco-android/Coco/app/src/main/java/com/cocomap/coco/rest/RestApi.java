package com.cocomap.coco.rest;

import com.cocomap.coco.pojo.IpResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Emrah on 20/11/2016.
 */
public interface RestApi {

    @GET("/?format=json")
    Call<IpResponse> getIp();
}
