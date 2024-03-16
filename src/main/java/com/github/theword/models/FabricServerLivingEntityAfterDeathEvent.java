package com.github.theword.models;

public class FabricServerLivingEntityAfterDeathEvent extends FabricMessageEvent {

    public FabricServerLivingEntityAfterDeathEvent(String messageId, FabricServerPlayer player, String message) {
        super("FabricServerLivingEntityAfterDeathEvent", "death", messageId, player, message);
    }
}
