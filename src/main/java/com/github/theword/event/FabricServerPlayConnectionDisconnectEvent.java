package com.github.theword.event;

public class FabricServerPlayConnectionDisconnectEvent extends BaseNoticeEvent {
    public FabricServerPlayConnectionDisconnectEvent(String serverName, FabricServerPlayer player) {
        super(serverName, "FabricServerPlayConnectionDisconnectEvent", "quit", player);
    }
}
