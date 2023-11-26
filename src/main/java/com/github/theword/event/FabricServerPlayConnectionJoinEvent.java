package com.github.theword.event;

public class FabricServerPlayConnectionJoinEvent extends BaseNoticeEvent {
    public FabricServerPlayConnectionJoinEvent(String serverName, FabricServerPlayer player) {
        super(serverName, "FabricServerPlayConnectionJoinEvent", "join", player);
    }
}
