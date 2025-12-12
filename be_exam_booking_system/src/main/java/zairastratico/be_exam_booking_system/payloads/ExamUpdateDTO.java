package zairastratico.be_exam_booking_system.payloads;

import zairastratico.be_exam_booking_system.entities.enums.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamUpdateDTO(
        String name,

        LocalDate date,

        TimeSlot timeSlot,

        LocalTime time,

        Integer maxNumb,

        Boolean forceVisibility
) {
}
