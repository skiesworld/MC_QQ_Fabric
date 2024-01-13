package com.github.theword.event;

public class FabricServerMessageEvent extends FabricMessageEvent {
    public FabricServerMessageEvent(String messageId, FabricServerPlayer player, String message) {
        super("FabricServerMessageEvent", "chat", messageId, player, message);
    }
}
