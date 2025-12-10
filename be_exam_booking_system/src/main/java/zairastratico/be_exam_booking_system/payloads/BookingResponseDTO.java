package zairastratico.be_exam_booking_system.payloads;

import zairastratico.be_exam_booking_system.entities.enums.DocumentType;

import java.time.LocalDateTime;

public record BookingResponseDTO(
        String name,
        String surname,
        String email,
        String phone,
        DocumentType documentType,
        String documentNumber,
        LocalDateTime createdAt,
        Long examId
) {
}
