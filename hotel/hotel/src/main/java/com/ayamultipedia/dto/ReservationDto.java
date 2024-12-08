package com.ayamultipedia.dto;


import com.ayamultipedia.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long price;
    private ReservationStatus reservationStatus;

    // On MENTION THE FIELDS OF THE ROOM

    private Long roomId;
    private String roomType;
    private String roomName;
    private Long userId;

    private String userName;
}
