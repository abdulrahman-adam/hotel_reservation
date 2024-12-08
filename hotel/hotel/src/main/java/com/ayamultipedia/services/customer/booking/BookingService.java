package com.ayamultipedia.services.customer.booking;

import com.ayamultipedia.dto.ReservationDto;
import com.ayamultipedia.dto.ReservationResponseDto;

public interface BookingService {

    // THE SIGNATURE OF THE METHOD POST BOOKING
    boolean postReservation(ReservationDto reservationDto);


    // THE SIGNATURE OF THE METHOD OF GETTING A ROOM BOOKED
    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);
}
