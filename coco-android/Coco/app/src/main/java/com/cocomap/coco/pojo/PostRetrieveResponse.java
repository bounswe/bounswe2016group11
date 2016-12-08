package com.cocomap.coco.pojo;

import java.util.List;

/**
 * Created by Sinan on 08.12.2016.
 */
public class PostRetrieveResponse {

    private int id;
    private String name;
    private UserModel user;
    private List<TagModel> tags;
    private List<RelatesToModel> related_to;
    private List<PostModel> posts;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    public List<RelatesToModel> getRelated_to() {
        return related_to;
    }

    public void setRelated_to(List<RelatesToModel> related_to) {
        this.related_to = related_to;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
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
