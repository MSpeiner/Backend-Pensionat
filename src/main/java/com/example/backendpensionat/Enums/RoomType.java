package com.example.backendpensionat.Enums;

import com.example.backendpensionat.Models.Room;
import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE( 0),
    DOUBLE( 1),
    SUITE( 2);

    public final int extraBeds;

    RoomType(int extraBeds) {
        this.extraBeds = extraBeds;
    }

    public static RoomType getRoomType(int beds) {
        return switch (beds) {
            case 1 -> RoomType.DOUBLE;
            case 2 -> RoomType.SUITE;
            default -> RoomType.SINGLE;
        };
    }
}