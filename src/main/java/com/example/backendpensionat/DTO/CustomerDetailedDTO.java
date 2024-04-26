package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Models.Booking;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Data
public class CustomerDetailedDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String Ssn;

        List<BookingDTO> bookings;
}
