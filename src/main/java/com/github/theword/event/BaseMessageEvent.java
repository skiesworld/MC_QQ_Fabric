package com.github.theword.event;

import com.google.gson.annotations.SerializedName;

public class BaseMessageEvent extends BaseEvent {
    @SerializedName("message_id")
    private String messageId;
    private FabricServerPlayer player;
    private String message;

    public BaseMessageEvent(String messageId, String serverName, String eventName, String subType, FabricServerPlayer player, String message) {
        super(serverName, eventName, "message", subType);
        this.messageId = messageId;
        this.message = message;
        this.player = player;
    }
}
