package com.ayamultipedia.repository;


import com.ayamultipedia.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // THE SIGNATURE OF THE METHOD GETTING A ROOM BOOKED
    Page<Reservation> findAllByUserId(Pageable pageable,Long userId);
}
