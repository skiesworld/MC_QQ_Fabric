package com.github.theword.event;


public class BaseNoticeEvent extends BaseEvent {

    private FabricServerPlayer player;

    public BaseNoticeEvent(String serverName, String eventName, String subType, FabricServerPlayer player) {
        super(serverName, eventName, "notice", subType);
        this.player = player;
    }
}
