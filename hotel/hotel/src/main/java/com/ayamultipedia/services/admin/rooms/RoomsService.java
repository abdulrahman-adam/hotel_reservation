package com.ayamultipedia.services.admin.rooms;


import com.ayamultipedia.dto.RoomDto;
import com.ayamultipedia.dto.RoomResponseDto;

public interface RoomsService {
    boolean postRoom(RoomDto roomDto);

    RoomResponseDto getAllRooms(int pageNumber);


    // ON GETTING A ROOM FROM THE DATABASE BY IT IS ID
    RoomDto getRoomById(Long id);

    // ON UPDATING A ROOM
    boolean updateRoom(Long id, RoomDto roomDto);

    // THE SIGNATURE OF THE METHOD MAKE DELETE ROOM FROM DATABASE
    void deleteRoom(Long id);
}
