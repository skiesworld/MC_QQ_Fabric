package com.github.theword.event;

public class FabricServerPlayConnectionJoinEvent extends BaseNoticeEvent {
    public FabricServerPlayConnectionJoinEvent(FabricServerPlayer player) {
        super("FabricServerPlayConnectionJoinEvent", "join", player);
    }
}
