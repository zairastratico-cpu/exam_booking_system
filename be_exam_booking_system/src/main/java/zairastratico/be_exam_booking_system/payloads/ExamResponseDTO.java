package zairastratico.be_exam_booking_system.payloads;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamResponseDTO(
        String name,
        String description,
        LocalDate date,
        LocalTime time,
        Integer maxNumb,
        Integer availableNumb
) {
}
