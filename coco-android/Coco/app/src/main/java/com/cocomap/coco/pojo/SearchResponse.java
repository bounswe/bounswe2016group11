package com.cocomap.coco.pojo;

import java.util.List;

/**
 * Created by Emrah on 21/12/2016.
 */
public class SearchResponse {
    List<PostRetrieveResponse> topics;

    public List<PostRetrieveResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<PostRetrieveResponse> topics) {
        this.topics = topics;
    }
}
