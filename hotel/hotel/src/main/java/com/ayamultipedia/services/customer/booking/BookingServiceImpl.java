package com.ayamultipedia.services.customer.booking;


import com.ayamultipedia.dto.ReservationDto;
import com.ayamultipedia.dto.ReservationResponseDto;
import com.ayamultipedia.entity.Reservation;
import com.ayamultipedia.entity.Room;
import com.ayamultipedia.entity.User;
import com.ayamultipedia.enums.ReservationStatus;
import com.ayamultipedia.repository.ReservationRepository;
import com.ayamultipedia.repository.RoomRepository;
import com.ayamultipedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto reservationDto) {
        Optional<User> optionalUser = userRepository.findById(reservationDto.getUserId());
        Optional<Room> optionalRoom = roomRepository.findById(reservationDto.getRoomId());
        if (optionalRoom.isPresent() && optionalUser.isPresent()) {
            Reservation reservation = new Reservation();

            reservation.setRoom(optionalRoom.get());
            reservation.setUser(optionalUser.get());
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());


            reservation.setReservationStatus(ReservationStatus.PENDING);

            // ON CALCULATE THE PRICE
            Long days = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckOutDate());
            reservation.setPrice(optionalRoom.get().getPrice()* days);
            reservationRepository.save(reservation);
            return true;
        }

        return false;
    }

    // THE METHOD OF GETTING A ROOM BOOKED
    public ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage  = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());
        return reservationResponseDto;
    }
}
