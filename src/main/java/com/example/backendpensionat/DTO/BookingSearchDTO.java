package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSearchDTO {
    private CustomerDetailedDTO customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private RoomType roomType;
    private Long roomNumber;
    private int amountOfBeds;
    private Double totalPrice = 0.0;
}
