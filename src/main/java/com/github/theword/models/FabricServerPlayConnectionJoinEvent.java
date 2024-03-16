package com.github.theword.models;

public class FabricServerPlayConnectionJoinEvent extends FabricNoticeEvent {
    public FabricServerPlayConnectionJoinEvent(FabricServerPlayer player) {
        super("FabricServerPlayConnectionJoinEvent", "join", player);
    }
}
