package com.github.theword.event;

public class FabricServerCommandMessageEvent extends BaseMessageEvent {
    public FabricServerCommandMessageEvent(String messageId, String serverName, FabricServerPlayer player, String message) {
        super(messageId, serverName, "FabricServerCommandMessageEvent", "player_command", player, message);
    }
}
