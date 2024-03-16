package com.github.theword.models;

public class FabricServerMessageEvent extends FabricMessageEvent {
    public FabricServerMessageEvent(String messageId, FabricServerPlayer player, String message) {
        super("FabricServerMessageEvent", "chat", messageId, player, message);
    }
}
