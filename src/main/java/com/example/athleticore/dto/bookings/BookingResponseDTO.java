package com.example.athleticore.dto.bookings;

import com.example.athleticore.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponseDTO {
    private Long id;
    private Long sessionId;
    private String sessionName;
    private LocalDateTime sessionDate;

    private Long clientId;
    private String clientName;

    private BookingStatus status;
}

