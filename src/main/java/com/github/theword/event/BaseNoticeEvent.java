package com.github.theword.event;


public class BaseNoticeEvent extends BaseEvent {

    private FabricServerPlayer player;

    public BaseNoticeEvent(String eventName, String subType, FabricServerPlayer player) {
        super(eventName, "notice", subType);
        this.player = player;
    }
}
