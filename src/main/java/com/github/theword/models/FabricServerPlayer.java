package com.github.theword.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
