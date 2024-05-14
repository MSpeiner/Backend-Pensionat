package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class EventType {
    LocalDateTime timeStamp;
    String type;
    String roomNo;
    String cleaningByUser;

    public EventType(LocalDateTime timeStamp, String type, String roomNo, String cleaningByUser) {
        this.timeStamp = timeStamp;
        this.type = type;
        this.roomNo = roomNo;
        this.cleaningByUser = cleaningByUser == null ? "" : "  -  " + cleaningByUser;
    }

    public EventType() {
    }
}
