package com.github.theword.event;

public class FabricServerPlayConnectionDisconnectEvent extends FabricNoticeEvent {
    public FabricServerPlayConnectionDisconnectEvent(FabricServerPlayer player) {
        super("FabricServerPlayConnectionDisconnectEvent", "quit", player);
    }
}
