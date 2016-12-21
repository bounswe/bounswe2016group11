package com.cocomap.coco.pojo;

import java.util.List;

/**
 * Created by Emrah on 21/12/2016.
 */
public class SearchTopicRequest {
    String query;
    List<Integer> tags;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }
}
