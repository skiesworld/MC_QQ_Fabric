package com.github.theword.event;

public class FabricServerPlayConnectionJoinEvent extends FabricNoticeEvent {
    public FabricServerPlayConnectionJoinEvent(FabricServerPlayer player) {
        super("FabricServerPlayConnectionJoinEvent", "join", player);
    }
}
