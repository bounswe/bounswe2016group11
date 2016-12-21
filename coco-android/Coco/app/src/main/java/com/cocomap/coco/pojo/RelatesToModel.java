package com.cocomap.coco.pojo;

/**
 * Created by Emrah on 08/12/2016.
 */
public class RelatesToModel {
    int id;
    private String label;
    private TopicModel topic_from;
    private TopicModel topic_to;
    private int positive_reaction_count;
    private int negative_reaction_count;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TopicModel getTopic_from() {
        return topic_from;
    }

    public void setTopic_from(TopicModel topic_from) {
        this.topic_from = topic_from;
    }

    public TopicModel getTopic_to() {
        return topic_to;
    }

    public void setTopic_to(TopicModel topic_to) {
        this.topic_to = topic_to;
    }
}