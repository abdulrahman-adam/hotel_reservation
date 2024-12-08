package com.ayamultipedia.controller.admin;


import com.ayamultipedia.dto.RoomDto;
import com.ayamultipedia.services.admin.rooms.RoomsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RoomsController {

    private final RoomsService roomsService;

    // THIS METHOD MAKE CREATION THE A ROOM
    @PostMapping("/room")
    public ResponseEntity<?>postRoom(@RequestBody RoomDto roomDto) {
        boolean success = roomsService.postRoom(roomDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();

        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<?> getAllRooms(@PathVariable int pageNumber) {
        return ResponseEntity.ok(roomsService.getAllRooms(pageNumber));

    }

    //ON GETTINR A ROOM BY IT IS ID
    @GetMapping("/room/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(roomsService.getRoomById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went wrong");
        }
    }

    // ON UPADATING A ROOM
    @PutMapping("/room/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        boolean success = roomsService.updateRoom(id, roomDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();

        } else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ON DELETING THE ROOM
    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        try{
            roomsService.deleteRoom(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
