package com.cocomap.coco.pojo;

import java.util.List;

/**
 * Created by Emrah on 04/12/2016.
 */
public class TagModel {
    Integer wikidataID;
    String name;
    List<Integer> topics;
    List<Integer> posts;
    String created_at;
    String updated_at;

    public Integer getWikidataID() {
        return wikidataID;
    }

    public void setWikidataID(Integer wikidataID) {
        this.wikidataID = wikidataID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getTopics() {
        return topics;
    }

    public void setTopics(List<Integer> topics) {
        this.topics = topics;
    }

    public List<Integer> getPosts() {
        return posts;
    }

    public void setPosts(List<Integer> posts) {
        this.posts = posts;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
