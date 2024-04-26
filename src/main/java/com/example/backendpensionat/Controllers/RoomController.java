package com.example.backendpensionat.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    @GetMapping("/rooms")
    public String allRooms() {
        return "rooms";
    }
}
