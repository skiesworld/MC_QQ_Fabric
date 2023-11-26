package com.github.theword.event;

public class FabricServerMessageEvent extends BaseMessageEvent {
    public FabricServerMessageEvent(String messageId, String serverName, FabricServerPlayer player, String message) {
        super(messageId, serverName, "FabricServerMessageEvent", "chat", player, message);
    }
}
