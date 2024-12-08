package com.ayamultipedia.services.customer.room;


import com.ayamultipedia.dto.RoomResponseDto;
import com.ayamultipedia.entity.Room;
import com.ayamultipedia.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    // THE METHOD OF GETTING THE ROOMS FROM THE DATABASE
    public RoomResponseDto getAvailableRoom(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findByAvailable(true,pageable);
        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDto.setTotalPages(roomPage.getTotalPages());
        roomResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));
        return roomResponseDto;
    }


}
