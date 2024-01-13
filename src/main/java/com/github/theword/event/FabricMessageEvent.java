package com.github.theword.event;

import com.google.gson.annotations.SerializedName;

public class FabricMessageEvent extends FabricEvent {
    @SerializedName("message_id")
    private String messageId;
    private FabricServerPlayer player;
    private String message;

    public FabricMessageEvent(String eventName, String subType, String messageId, FabricServerPlayer player, String message) {
        super(eventName, "message", subType);
        this.messageId = messageId;
        this.message = message;
        this.player = player;
    }
}
