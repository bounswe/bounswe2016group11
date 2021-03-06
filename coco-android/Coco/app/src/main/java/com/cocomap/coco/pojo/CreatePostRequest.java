package com.cocomap.coco.pojo;

/**
 * Created by Emrah on 30/11/2016.
 */

import java.util.List;

public class CreatePostRequest {

    private String content;
    private int user;
    private List<TagModel> tags;
    private int topic;
    private int positive_reaction_count;
    private int negative_reaction_count;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
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
}
