package com.github.theword.event;

import com.google.gson.annotations.SerializedName;

import static com.github.theword.ConfigReader.config;

public class BaseEvent {
    @SerializedName("server_name")
    private final String serverName = config().get("server_name").toString();

    @SerializedName("event_name")
    private String eventName;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("sub_type")
    private String subType;

    private final int timestamp = (int) (System.currentTimeMillis() / 1000);

    public BaseEvent(String eventName, String postType, String subType) {
        this.eventName = eventName;
        this.postType = postType;
        this.subType = subType;
    }
}
