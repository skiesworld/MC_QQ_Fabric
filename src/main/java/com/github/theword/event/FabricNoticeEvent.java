package com.github.theword.event;


public class FabricNoticeEvent extends FabricEvent {

    private FabricServerPlayer player;

    public FabricNoticeEvent(String eventName, String subType, FabricServerPlayer player) {
        super(eventName, "notice", subType);
        this.player = player;
    }
}
