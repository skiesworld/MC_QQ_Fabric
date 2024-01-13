package com.github.theword.event;

public class FabricServerPlayConnectionDisconnectEvent extends BaseNoticeEvent {
    public FabricServerPlayConnectionDisconnectEvent(FabricServerPlayer player) {
        super("FabricServerPlayConnectionDisconnectEvent", "quit", player);
    }
}
