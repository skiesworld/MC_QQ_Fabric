package com.github.theword.event;

import com.google.gson.annotations.SerializedName;

public class FabricServerPlayer {
    private String nickname;
    private String uuid;
    private String ip;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("movement_speed")
    private float movementSpeed;

    @SerializedName("block_x")
    private int blockX;
    @SerializedName("block_y")
    private int blockY;
    @SerializedName("block_z")
    private int blockZ;

    @SerializedName("is_creative")
    private boolean isCreative;
    @SerializedName("is_spectator")
    private boolean isSpectator;
    @SerializedName("is_sneaking")
    private boolean isSneaking;
    @SerializedName("is_sleeping")
    private boolean isSleeping;
    @SerializedName("is_climbing")
    private boolean isClimbing;
    @SerializedName("is_swimming")
    private boolean isSwimming;


    public FabricServerPlayer() {
    }

    public FabricServerPlayer(String nickname, String uuid, String ip, String displayName, float movementSpeed, int blockX, int blockY, int blockZ, boolean isCreative, boolean isSpectator, boolean isSneaking, boolean isSleeping, boolean isClimbing, boolean isSwimming) {
        this.nickname = nickname;
        this.uuid = uuid;
        this.ip = ip;
        this.displayName = displayName;
        this.movementSpeed = movementSpeed;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.isCreative = isCreative;
        this.isSpectator = isSpectator;
        this.isSneaking = isSneaking;
        this.isSleeping = isSleeping;
        this.isClimbing = isClimbing;
        this.isSwimming = isSwimming;
    }

    @Override
    public String toString() {
        return "FabricServerPlayer{" +
                "nickname='" + nickname + '\'' +
                ", uuid='" + uuid + '\'' +
                ", ip='" + ip + '\'' +
                ", displayName='" + displayName + '\'' +
                ", movementSpeed=" + movementSpeed +
                ", blockX=" + blockX +
                ", blockY=" + blockY +
                ", blockZ=" + blockZ +
                ", isCreative=" + isCreative +
                ", isSpectator=" + isSpectator +
                ", isSneaking=" + isSneaking +
                ", isSleeping=" + isSleeping +
                ", isClimbing=" + isClimbing +
                ", isSwimming=" + isSwimming +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getBlockX() {
        return blockX;
    }

    public void setBlockX(int blockX) {
        this.blockX = blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
    }

    public int getBlockZ() {
        return blockZ;
    }

    public void setBlockZ(int blockZ) {
        this.blockZ = blockZ;
    }

    public boolean isCreative() {
        return isCreative;
    }

    public void setCreative(boolean creative) {
        isCreative = creative;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setSpectator(boolean spectator) {
        isSpectator = spectator;
    }

    public boolean isSneaking() {
        return isSneaking;
    }

    public void setSneaking(boolean sneaking) {
        isSneaking = sneaking;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }

    public boolean isClimbing() {
        return isClimbing;
    }

    public void setClimbing(boolean climbing) {
        isClimbing = climbing;
    }

    public boolean isSwimming() {
        return isSwimming;
    }

    public void setSwimming(boolean swimming) {
        isSwimming = swimming;
    }
}
