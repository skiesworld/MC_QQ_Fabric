package com.github.theword.event;

public class FabricServerLivingEntityAfterDeathEvent extends BaseMessageEvent {

    public FabricServerLivingEntityAfterDeathEvent(String messageId, String serverName, FabricServerPlayer player, String message) {
        super(messageId, serverName, "FabricServerLivingEntityAfterDeathEvent", "death", player, message);
    }
}
