package com.ayamultipedia.repository;

import com.ayamultipedia.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // the signature of availability of room
    Page<Room> findByAvailable(boolean available, Pageable pageable);
}
