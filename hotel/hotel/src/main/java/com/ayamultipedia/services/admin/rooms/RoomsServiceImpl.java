package com.ayamultipedia.services.admin.rooms;


import com.ayamultipedia.dto.RoomDto;
import com.ayamultipedia.dto.RoomResponseDto;
import com.ayamultipedia.entity.Room;
import com.ayamultipedia.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {

    private final RoomRepository roomRepository;

    public boolean postRoom(RoomDto roomDto) {
        try{
            Room room = new Room();
            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(true);
            roomRepository.save(room);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // THE METHOD OF GETTING THE ROOMS FROM THE DATABASE
    public RoomResponseDto getAllRooms(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDto.setTotalPages(roomPage.getTotalPages());
        roomResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));
        return roomResponseDto;
    }

    // THE METHOD OF GETTING A ROOM BY ID FROM THE DATABASE

    public RoomDto getRoomById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()) {
            return optionalRoom.get().getRoomDto();
        } else {
            throw new EntityNotFoundException("Room not present");
        }

    }

    // THIS METHOD MAKES UPDATE AROOM

    public boolean updateRoom(Long id, RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()) {
            Room existingRoom = optionalRoom.get();
            existingRoom.setName(roomDto.getName());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setType(roomDto.getType());
            //existingRoom.setAvailable(roomDto.isAvailable());
            roomRepository.save(existingRoom);
            return true;
        }
        return false;
    }

    // THIS METHOD MAKES DELETE A ROOM
    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()) {
            roomRepository.deleteById(id);

        } else {
            throw new EntityNotFoundException("Room not present");
        }
    }

}
