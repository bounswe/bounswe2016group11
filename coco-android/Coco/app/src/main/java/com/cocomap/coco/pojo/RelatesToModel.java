package com.cocomap.coco.pojo;

/**
 * Created by Emrah on 08/12/2016.
 */
public class RelatesToModel {
    int id;
    String label;
    String topic_from;
    String topic_to;

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

    public String getTopic_from() {
        return topic_from;
    }

    public void setTopic_from(String topic_from) {
        this.topic_from = topic_from;
    }

    public String getTopic_to() {
        return topic_to;
    }

    public void setTopic_to(String topic_to) {
        this.topic_to = topic_to;
    }
}
