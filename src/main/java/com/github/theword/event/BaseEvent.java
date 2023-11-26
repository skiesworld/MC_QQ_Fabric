package com.github.theword.event;

import com.google.gson.annotations.SerializedName;

public class BaseEvent {
    @SerializedName("server_name")
    private String serverName;

    @SerializedName("event_name")
    private String eventName;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("sub_type")
    private String subType;

    private final int timestamp = (int) (System.currentTimeMillis() / 1000);

    public BaseEvent(String serverName, String eventName, String postType, String subType) {
        this.serverName = serverName;
        this.eventName = eventName;
        this.postType = postType;
        this.subType = subType;
    }
}
