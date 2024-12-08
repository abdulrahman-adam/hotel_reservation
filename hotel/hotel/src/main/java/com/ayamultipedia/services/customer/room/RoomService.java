package com.ayamultipedia.services.customer.room;

import com.ayamultipedia.dto.RoomResponseDto;

public interface RoomService {

    // THE SIGNATURE OF THE METHOD AVAILABILITY OF THE ROOM
    RoomResponseDto getAvailableRoom(int pageNumber);
}
