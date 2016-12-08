package com.cocomap.coco.pojo;

import java.util.List;

/**
 * Created by Emrah on 08/12/2016.
 */
public class PostModel {
    int id;
    String content;
    int positive_reaction_count;
    int negative_reaction_count;
    String created_at;
    String updated_at;
    int user;
    int topic;
    List<TagModel> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPositive_reaction_count() {
        return positive_reaction_count;
    }

    public void setPositive_reaction_count(int positive_reaction_count) {
        this.positive_reaction_count = positive_reaction_count;
    }

    public int getNegative_reaction_count() {
        return negative_reaction_count;
    }

    public void setNegative_reaction_count(int negative_reaction_count) {
        this.negative_reaction_count = negative_reaction_count;
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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }
}
