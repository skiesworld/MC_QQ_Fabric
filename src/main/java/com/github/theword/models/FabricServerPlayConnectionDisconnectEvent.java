package com.github.theword.models;

public class FabricServerPlayConnectionDisconnectEvent extends FabricNoticeEvent {
    public FabricServerPlayConnectionDisconnectEvent(FabricServerPlayer player) {
        super("FabricServerPlayConnectionDisconnectEvent", "quit", player);
    }
}
