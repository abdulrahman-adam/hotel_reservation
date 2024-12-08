package com.ayamultipedia.services.admin.reservation;


import com.ayamultipedia.dto.ReservationResponseDto;

public interface ReservationService {
    ReservationResponseDto getAllReservations(int pageNumber);

    boolean changeReservationStatus(Long id, String status);
}
