package com.github.theword.models;

public class FabricServerCommandMessageEvent extends FabricMessageEvent {
    public FabricServerCommandMessageEvent(String messageId, FabricServerPlayer player, String message) {
        super("FabricServerCommandMessageEvent", "player_command", messageId, player, message);
    }
}
