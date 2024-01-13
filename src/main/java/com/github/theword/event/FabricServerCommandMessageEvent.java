package com.github.theword.event;

public class FabricServerCommandMessageEvent extends BaseMessageEvent {
    public FabricServerCommandMessageEvent(String messageId, FabricServerPlayer player, String message) {
        super("FabricServerCommandMessageEvent", "player_command", messageId, player, message);
    }
}
