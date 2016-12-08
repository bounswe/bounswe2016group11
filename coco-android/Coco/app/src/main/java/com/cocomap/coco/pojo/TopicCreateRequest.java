package com.cocomap.coco.pojo;

import java.util.List;

/**
 * Created by Emrah on 08/12/2016.
 */
public class TopicCreateRequest {

    String name;
    Integer user;
    List<Integer> relates_to;
    List<Integer> tags;
    List<Integer> posts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<Integer> getRelates_to() {
        return relates_to;
    }

    public void setRelates_to(List<Integer> relates_to) {
        this.relates_to = relates_to;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public List<Integer> getPosts() {
        return posts;
    }

    public void setPosts(List<Integer> posts) {
        this.posts = posts;
    }
}
